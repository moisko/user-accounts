# user-accounts
TesttaskforSeniorJavaWebDevelopers-061014-1301-44

* URL - http://boratino.com/user-accounts/

* Github repository - https://github.com/moisko/user-accounts

* Jenkins build - http://www.boratino.com/jenkins/
	-> project - user-accounts
	-> username/password - westernacher/westernacher

Environment:
------------
* Java 7

* Services Framework - Apache CXF

* Application Server - Tomcat 7

* Database - MySQL

* Sortable Grid - jquery datatables

* CSS Framework - Bootstrap

Setup:
------
* Connector/j driver should be installed in $CATALINA_HOME/lib dir

* create account_schema db schema before deploying and starting the application

* In server.xml, add the following context configuration and replace with the correct values for username and password:
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
* get latest development version from Jenkins CI server

* deploy produced war

* make a GET request to http://localhost:<port>/user-accounts/accounts to check if the server returns empty array

# REST endpoints
See accounts.controller.Accounts

