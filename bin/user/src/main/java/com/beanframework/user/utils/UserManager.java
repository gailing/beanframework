package com.beanframework.user.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import com.beanframework.user.MaxSessionReachedException;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserSession;

@Component
public class UserManager {

	public static final String MODEL = "userManager";

	public boolean isLoggedIn() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			return false;
		}

		if (auth.getPrincipal() instanceof User) {
			return true;
		} else {
			return false;
		}
	}

	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return null;
		}

		return (User) auth.getPrincipal();
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getAuthorities();
	}

	@Autowired
	private SessionRegistry sessionRegistry;

	public List<UserSession> getAllUserSession() {
		final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
		List<UserSession> userSessions = new ArrayList<UserSession>();

		for (final Object principal : allPrincipals) {
			if (principal instanceof User) {
				final User user = (User) principal;
				List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(user, false);
				userSessions.add(new UserSession(user, sessionInformations));
			}
		}
		return userSessions;
	}

	public List<User> getAllLoggedInUser() {
		final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
		List<User> userList = new ArrayList<User>();

		for (final Object principal : allPrincipals) {
			if (principal instanceof User) {
				final User user = (User) principal;

				userList.add(user);
			}
		}
		return userList;
	}

	public void expireAllSessionsByUsername(String username) {
		List<User> userList = getAllLoggedInUser();

		for (User user : userList) {
			if (user.getUsername().equals(username)) {
				List<SessionInformation> session = sessionRegistry.getAllSessions(user, false);
				for (SessionInformation sessionInformation : session) {
					sessionInformation.expireNow(); // expire the session
				}
			}
		}
	}

	public void expireOneOldSessionByUsername(String username) {
		List<User> userList = getAllLoggedInUser();

		for (User user : userList) {
			if (user.getUsername().equals(username)) {
				List<SessionInformation> session = sessionRegistry.getAllSessions(user, false);

				Date oldestDate = null;
				String sessionIdToExpire = null;
				for (SessionInformation sessionInformation : session) {
					if (oldestDate == null) {
						oldestDate = sessionInformation.getLastRequest();
						sessionIdToExpire = sessionInformation.getSessionId();
					} else if (oldestDate.compareTo(sessionInformation.getLastRequest()) < 0) {
						oldestDate = sessionInformation.getLastRequest();
						sessionIdToExpire = sessionInformation.getSessionId();
					}
				}

				if (sessionIdToExpire != null) {
					for (SessionInformation sessionInformation : session) {
						if (sessionInformation.getSessionId().equals(sessionIdToExpire)) {
							sessionInformation.expireNow(); // expire the
															// session
						}
					}
				}
			}
		}
	}

	public static Date least(Date a, Date b) {
		return a == null ? b : (b == null ? a : (a.before(b) ? a : b));
	}

	public boolean isLoggedInByUsername(String username) {
		List<User> userList = getAllLoggedInUser();

		for (User user : userList) {
			if (user.getUsername().equals(username)) {
				List<SessionInformation> session = sessionRegistry.getAllSessions(user, false);
				if (session != null && !session.isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	public int getTotalSessionsByUsername(String username) {
		List<User> userList = getAllLoggedInUser();

		int total = 0;
		for (User user : userList) {
			if (user.getUsername().equals(username)) {
				List<SessionInformation> session = sessionRegistry.getAllSessions(user, false);
				if (session != null && !session.isEmpty()) {
					total++;
				}
			}
		}
		return total;
	}

	@Value(UserConstants.MAX_SESSION_USER)
	private int maxSessionUser;

	@Value(UserConstants.MAX_SESSION_PREVENTS_LOGIN)
	private boolean maxSessionPreventsLogin;

	public void validateMaxUserSession(User user) {
		int totalSessions = getTotalSessionsByUsername(user.getUsername());

		if (maxSessionUser != -1 && totalSessions >= maxSessionUser) {
			if (maxSessionPreventsLogin) {
				throw new MaxSessionReachedException("You already reached the maximum allowed number of sessions.");
			} else {
				expireOneOldSessionByUsername(user.getUsername());
			}
		}
	}
}
