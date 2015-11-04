# user-accounts
TesttaskforSeniorJavaWebDevelopers-061014-1301-44

* URL - http://www.boratino.com/user-accounts

* Github repository - https://github.com/moisko/user-accounts

* Jenkins build - http://www.boratino.com/jenkins/job/user-accounts/

Environment:
------------
* Java 7

* Services Framework - Apache CXF

* Application Server - Tomcat 7

* Database - MySQL

Setup:
------
* Connector/j driver should be installed in $CATALINA_HOME/lib dir

* create account_schema db schema before deploying and starting the application

* In server.xml, add the following context configuration:
```xml
<Context docBase="user-accounts" path="/user-accounts" reloadable="true" source="org.eclipse.jst.jee.server:user-accounts">
	<Resource driverClassName="com.mysql.jdbc.Driver"
		factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
		initialSize="10"
		maxActive="100"
		maxIdle="50"
		minEvictableIdleTimeMillis="55000"
		minIdle="10"
		name="jdbc/accountsDB"
		password="<password>"
		removeAbandoned="true"
		removeAbandonedTimeout="55"
		testOnBorrow="true"
		timeBetweenEvictionRunsMillis="34000"
		type="org.apache.tomcat.jdbc.pool.DataSource"
		url="jdbc:mysql://localhost:3306/account_schema"
		username="<username>"
		validationInterval="34"
		validationQuery="select 1" />
</Context>
```
* get latest development version from Jenkins CI server - see comments above

# REST endpoints
See javadoc in Accounts

