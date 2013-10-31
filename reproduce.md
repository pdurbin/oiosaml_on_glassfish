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
