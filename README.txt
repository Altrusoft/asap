Altrusoftse Alfplay Repo Extension
=======================================

Author: Altrusoft


OVERVIEW
--------

repo
  config
    alfresco
      extension
        altrusoft-alfplay-context.xml ---  Registers model & actions
      templates
         webscripts ---  Place your custom webscripts here
  lib  ---  Libs used by ant tasks
  build.xml  ---  Dist & dev build targets, i.e. hotcopy-tomcat-zip & dist-jar
  build.properties  ---  NOTE! Add the path to your Alfresco installation

<classpath>
	<classpathentry kind="src" path="source/java"/>
	<classpathentry kind="lib" path="lib/ml-ant-http-1.1.1.jar"/>
	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/alfresco-core-4.2.c.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/alfresco-data-model-4.2.c.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/alfresco-deployment-4.2.c.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/alfresco-jlan-embed-4.2.c.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/alfresco-jndi-client-4.2.c.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/alfresco-mbeans-4.2.c.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/alfresco-remote-api-4.2.c.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/alfresco-repository-4.2.c.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/alfresco-web-client-4.2.c.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/alfresco-web-framework-commons-4.2.c.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/org.springframework.aop-3.0.5.RELEASE.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/org.springframework.asm-3.0.5.RELEASE.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/org.springframework.beans-3.0.5.RELEASE.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/org.springframework.context.support-3.0.5.RELEASE.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/org.springframework.context-3.0.5.RELEASE.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/org.springframework.core-3.0.5.RELEASE.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/org.springframework.expression-3.0.5.RELEASE.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/org.springframework.jdbc-3.0.5.RELEASE.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/org.springframework.orm-3.0.5.RELEASE.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/org.springframework.test-3.0.0.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/org.springframework.test-3.0.5.RELEASE.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/org.springframework.transaction-3.0.5.RELEASE.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/org.springframework.web.servlet-3.0.5.RELEASE.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/org.springframework.web-3.0.5.RELEASE.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-beanutils-1.7.0.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-codec-1.5.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-collections-3.2.1.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-compress-1.4.1.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-csv-20110211.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-dbcp-1.4-patched.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-digester-1.6.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-discovery-0.2.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-el.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-fileupload-1.2.2.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-httpclient-3.1.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-io-1.4.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-jxpath-1.2.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-lang-2.6.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-logging-1.1.1.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-net-2.2.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-pool-1.5.5.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/commons/commons-validator-1.4.0.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/spring-surf/spring-cmis-framework-1.2.0-SNAPSHOT.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/spring-surf/spring-webscripts-1.2.0-SNAPSHOT.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/spring-surf/spring-webscripts-1.2.0-SNAPSHOT-tests.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/spring-surf/spring-webscripts-api-1.2.0-SNAPSHOT.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/jackson-core-asl-1.8.3.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/jackson-mapper-asl-1.8.3.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/json.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/json-simple-1.1.1.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/spring-surf/spring-surf-1.2.0-SNAPSHOT.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/spring-surf/spring-surf-api-1.2.0-SNAPSHOT.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/spring-surf/spring-surf-core-1.2.0-SNAPSHOT.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/spring-surf/spring-surf-core-configservice-1.2.0-SNAPSHOT.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/activiti/activiti-engine-5.10-14112012.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/activiti/activiti-spring-5.10-14112012.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/activiti/groovy-1.7.5.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/activiti/livetribe-jsr223-2.0.6.jar"/>
	<classpathentry kind="lib" path="/home/share/home/hans/Utveckling/ws-alfplay/alfresco/lib/server/dependencies/j2ee/servlet.jar"/>
	<classpathentry kind="output" path="bin"/>
</classpath>
