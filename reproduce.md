# How to reproduce "org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory" using Vagrant

    murphy:pdurbin pdurbin$ git clone https://github.com/pdurbin/oiosaml_on_glassfish.git
    (snip)
    murphy:pdurbin pdurbin$ cd oiosaml_on_glassfish
    murphy:oiosaml_on_glassfish pdurbin$ vagrant up
    (snip)
    murphy:oiosaml_on_glassfish pdurbin$ vagrant ssh
    Last login: Thu Oct 31 17:54:47 2013 from 10.0.2.2
    Welcome to your Vagrant-built virtual machine.
    [vagrant@logus ~]$ cd /downloads
    [vagrant@logus downloads]$ unzip glassfish-3.1.2.2.zip
    (snip)
    [vagrant@logus downloads]$ /downloads/glassfish3/glassfish/bin/asadmin start-domain
    (snip)
    [vagrant@logus downloads]$ cd ~/oiosaml_on_glassfish
    [vagrant@logus oiosaml_on_glassfish]$ mvn package
    (snip)
    [vagrant@logus oiosaml_on_glassfish]$ /downloads/glassfish3/glassfish/bin/asadmin deploy /home/vagrant/oiosaml_on_glassfish/target/oiosaml_on_glassfish-1.0-SNAPSHOT.war
    Application deployed with name oiosaml_on_glassfish-1.0-SNAPSHOT.
    Command deploy executed successfully.
    [vagrant@logus oiosaml_on_glassfish]$ /downloads/glassfish3/glassfish/bin/asadmin redeploy --name oiosaml_on_glassfish-1.0-SNAPSHOT /home/vagrant/oiosaml_on_glassfish/target/oiosaml_on_glassfish-1.0-SNAPSHOT.war
    remote failure: Error occurred during deployment: Exception while loading the app : java.lang.IllegalStateException: ContainerBase.addChild: start: org.apache.catalina.LifecycleException: java.lang.RuntimeException: com.sun.faces.config.ConfigurationException: java.util.concurrent.ExecutionException: com.sun.faces.config.ConfigurationException: Unable to parse document 'bundle://53.0:1/com/sun/faces/jsf-ri-runtime.xml': DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.. Please see server.log for more details.
    Command redeploy failed.
    [vagrant@logus oiosaml_on_glassfish]$ 

The home page of the app triggers http://xml.apache.org/xalan-j/faq.html#environmentcheck and puts the output in /tmp

    [vagrant@logus oiosaml_on_glassfish]$ curl -s http://localhost:8080/oiosaml_on_glassfish-1.0-SNAPSHOT/ > /dev/null
    [vagrant@logus oiosaml_on_glassfish]$ cat /tmp/oiosaml_on_glassfish.xalan_envcheck 
    #---- BEGIN writeEnvironmentReport($Revision: 1.7 $): Useful stuff found: ----
    version.DOM.draftlevel=2.0fd
    java.class.path=/downloads/glassfish3/glassfish/modules/glassfish.jar:/downloads/glassfish3/glassfish/lib/monitor/flashlight-agent.jar
    version.JAXP=1.1 or higher
    java.ext.dirs=/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/ext:/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/jre/lib/ext:/downloads/glassfish3/glassfish/domains/domain1/lib/ext
    version.xerces2=Xerces-J 2.6.2
    version.xerces1=not-present
    version.xalan2_2=Xalan Java 2.6.0
    version.xalan1=not-present
    version.ant=Apache Ant(TM) version 1.8.2 compiled on December 20 2010
    java.version=1.6.0_24
    version.DOM=2.0
    version.crimson=not-present
    sun.boot.class.path=/downloads/glassfish3/glassfish/modules/endorsed/javax.annotation.jar:/downloads/glassfish3/glassfish/modules/endorsed/jaxb-api-osgi.jar:/downloads/glassfish3/glassfish/modules/endorsed/webservices-api-osgi.jar:/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/resources.jar:/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/rt.jar:/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/sunrsasign.jar:/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/jsse.jar:/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/jce.jar:/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/charsets.jar:/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/netx.jar:/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/plugin.jar:/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/rhino.jar:/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/modules/jdk.boot.jar:/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/classes
    version.SAX=2.0
    version.xalan2x=not-present
    #----- END writeEnvironmentReport: Useful properties found: -----
    # YAHOO! Your environment seems to be OK.
    [vagrant@logus oiosaml_on_glassfish]$ 
