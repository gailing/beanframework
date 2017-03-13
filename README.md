# ***BeanPortal is still UNDER DEVELOPMENT, official release will be publish at branch versions***

# BeanPortal - Java Enterprise Information Platform for Rapid Website Development

## Why BeanPortal

BeanPortal helps Java Developer to quickly setup their new Java project and transform to their desire development environment. BeanPortal contains many modules such as CMS, User Management, Menu, CronJob, Email, Log, Account Policy and System Configuration.

## Who Builded This Platform

I am only the one developer who contribute this project for now. After so many years I adapt many kinds of open source software and it helps me alot in my career, I decided to build this platform because I want to have a little contribution and gracefully return a favour to open source technology world. Feel free to comment any suggestions and report any bugs, I will do my best to keep this poject alive and updated. 

## Goal
My dream & goal is to develop a open source software to help developers in Malaysia to build their extensive software. Beside that, this platform also contain many best pratices and resuable code framework that can share to developers.

## Platform Overview

BeanPortal is based on several excellent open source project, highly integrated package from efficient, high performance, strong security Java EE open source rapid development platform.

BeanPortal itself use Spring Boot that contained Spring Framework as the core container, Spring MVC is Model View Controller, Hibernate data access layer,
Spring Security permissions for authorization layer, Ehcahe to cache frequently used data, Activit workflow engine, and so on.

## Built-in functions

1. User management: the user is a system operator, the main function of the system to complete the user configuration.
2. Management menu: System Configuration menu, operating authority, buttons, logo and other privileges.
3. Role Management: the role of rights assignment menu, set the role for data range by agency division of authority.
4. Dictionary management: fixed some of the more frequently used data system maintenance, such as: whether men and women, class, level and so on.
5. Operation Log: The system normal operation and query logging; logging and exception information system query.
6. The connection pool monitoring: monitor the current state of the system database connection pooling, SQL can be analyzed to identify performance bottlenecks.
7. The workflow engine: to achieve business work order flow, the online process designer.

## Why BeanPortal

1. Use the [Apache License 2.0] (http://www.apache.org/licenses/LICENSE-2.0) protocol, source code is completely open source, no commercial restrictions.
2. Use the current mainstream Java EE development framework, easy to learn, learn low cost.
3. Unlimited database, currently supports MySql, Oracle, scalable SQL Server, PostgreSQL, H2 and so on.
4. Modular design, a clear hierarchy. Built-in basic functions series of enterprise information management.
5. The operating authority precise and meticulous control of all management links for permission verification can be controlled to the button.
6. Data access control precise and meticulous, set permissions for the specified data filter or seven data authority to choose.
7. Provide online features code generation tools to improve development efficiency and quality.
8. The package provides common tools, logging, caching, authentication, dictionary, organizations, etc., commonly used tags (taglib), to get the current organizational structure, data dictionaries.
9. compatible with the most popular browsers (IE7 +, Chrome, Firefox) IE6 also supported, but experience poor results.

## UI

You are freely to add any new html template into application. You can add new theme under resources/static/theme and resources/templates/theme/ folder.
* Available Backend UI: AdminLTE Control Panel Template https://almsaeedstudio.com/
* Available Frontend UI:
* Email Template: https://github.com/wildbit/postmark-templates

## Technology Selection

1, The Backend

* Core framework: Spring Boot 1.4.0.BUILD-SNAPSHOT
* More dependency can be found in pom.xml

4, The platform

* Deployment: Spring Boot Embedded Web Server Deployment Tomcat 
(You can choose embedded Jetty, or you can deploy to external installation of Tomcat, WebSphere, WebLogic, or JBoss. More information can be found at https://spring.io/blog/2014/03/07/deploying-spring-boot-applications)
* Database: MySQL
(Not limited to database, leaving other database platforms support interfaces,It can be easily changed to other databases, such as: SqlServer 2008, MySql 5.5, H2, etc. You also can choose Spring Boot Embedded Database Support. More information can be found at http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-sql.html#boot-features-embedded-database-support)
* Development Environment: Java EE, Eclipse, Maven or Gradle, Git

## Security Considerations

1. Development Language: The system uses Java language development, with excellent versatility, efficiency, platform portability and security.
2. The hierarchical design :( database layer, data access layer, business logic layer, presentation layer) level, clear, low coupling, the layers must pass in order to access the interface and calibration parameters (such as: can not directly manipulate the database in the presentation layer ), ensure data security operation.
3. The two-factor authentication: the user double form submission validation: including server-side validation and client authentication to prevent a malicious user through a browser (such as text fields can not write, hidden variable tampering, upload illegal files, etc.), skip client authentication operation database.
4. Security Code: user form to submit all the data on the server side are securely encoded to prevent illegal users to submit scripts and SQL injection to obtain sensitive data, to ensure data security.
5. Encryption Password: Spring Framework recommended BCryptPasswordEncoder hashing user password encryption, this encryption method is not reversible. Ensure the safety of the ciphertext after the leak.
6. Mandatory access: The system links all managed end user identity verification authority, to prevent users from directly fill url to access.

## Demo

* <Http://demo.BeanPortal.com> Username: admin password: admin

## Quick Experience

1. Run org.beanportal.Application.java as Java Application in eclipse.
2. Visit url at http://localhost:8080 
3. The highest administrator account can be configured at src\main\resources\application.properties, "default.admin.username" and "default.admin.password" property.

## Common problem

1. 
2.
3.

## More Documentation

* <Https://github.com/williamtanws/BeanPortal/doc>

## How to communicate, feedback, participation contribution?

* E-mail: williamtanws@gmail.com
* GitHub: <https://github.com/williamtanws/BeanPortal>
* Official Website: <http://BeanPortal.com> Forums: <http://bbs.BeanPortal.com>
* Donor support BeanPortal (Paypal): [williamtanws@gmail.com] (http://BeanPortal.com/donation.html)

One's personal ability, you can not beat a team, want to support brothers and sisters, it is possible to contribute their part of the code, involved joint to improve it (^ _ ^).


## Copyright Notice

The software uses the [Apache License 2.0] (http://www.apache.org/licenses/LICENSE-2.0) agreement, in strict compliance with the agreement:

1. The need to give the user a code Apache Licence.
2. If you modify the code, a description of the modified files.
3. ** In extending the code (the source code modifications and derived code) requires agreement with the original code, trademarks, patents illustrate statements and other provisions of the original authors need to include. **
4. If the re-release of the product contains a Notice document is required in the Notice file with the Apache Licence. You can add your own in the Notice of license, but not the performance of the Apache Licence constitutes change.
3. Apache Licence is licensed for commercial use friendly. Users can also modify the code when needed to meet the needs and as open source or commercial product launches / Sales
