# user-accounts

* URL - http://borisborisov.bg/user-accounts

* Code coverage - 79.7%

Environment
-----------
* Java 8

* Services Framework - Apache CXF

* Application Server - Tomcat 8

* Database - MySQL

* Persistency - Eclipselink

* Sortable Grid - jQuery DataTables

* CSS Framework - Bootstrap 3

Setup
-----
* Connector/j mysql driver should be installed in <i>$CATALINA_HOME/lib</i> dir

* create <i>account_schema</i> db schema before deploying and starting the application

* create a user and grant permissions to <i>account_schema</i>

* Edit <i>server.xml</i>, add the following context configuration and replace with the correct values for username and password:
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

* copy the war file into webapps dir

# REST endpoints
See <b>accounts.controller.Accounts</b>

