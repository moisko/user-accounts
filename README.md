# user-accounts
TesttaskforSeniorJavaWebDevelopers-061014-1301-44

* Github repository - https://github.com/moisko/user-accounts

* Jenkins build - http://www.boratino.com/jenkins/job/user-accounts/ws/target/

* Application Server - Tomcat 7

* Database - MySQL

* System environment and used libraries - check out the pom.xml for more information

* Connector/j driver should be installed in $CATALINA_HOME/lib dir

* create account_schema db schema before deploying and starting the application

* mvn clean install

* In server.xml add the following context configuration for user-accounts application:
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
