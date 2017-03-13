package com.beanframework.console.web.api;

import java.lang.management.ManagementFactory;

import javax.annotation.PostConstruct;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;

import com.beanframework.console.web.ConsoleWebConstants;
import com.beanframework.platform.core.base.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ConsoleWebConstants.PATH_API_ROOT)
public class MonitorConsoleResource extends BaseResource {
	
	@Autowired
	private GaugeService gaugeService;
	
	@PostConstruct
	public void startMeasuring() {
	    new Thread() {
	        @Override
	        public void run() {
	            try {
					gaugeService.submit("process.cpu.load", getProcessCpuLoad());
					Thread.sleep(2000);   //measure every 2sec.
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
	        }
	    }.start();
	}
	
	public static double getProcessCpuLoad() throws Exception {
	    MBeanServer mbs    = ManagementFactory.getPlatformMBeanServer();
	    ObjectName name    = ObjectName.getInstance("java.lang:type=OperatingSystem");
	    AttributeList list = mbs.getAttributes(name, new String[]{ "ProcessCpuLoad" });

	    if (list.isEmpty())     return Double.NaN;

	    Attribute att = (Attribute)list.get(0);
	    Double value  = (Double)att.getValue();

	    // usually takes a couple of seconds before we get real values
	    if (value == -1.0)      return Double.NaN;
	    // returns a percentage value with 1 decimal point precision
	    return ((int)(value * 1000) / 10.0);
	}

	@RequestMapping(ConsoleWebConstants.PATH_API_MONITOR)
	public Double cpu(Model model, HttpServletRequest request) throws Exception {
		return getProcessCpuLoad();
	}
}
