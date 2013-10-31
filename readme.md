# OIOSAML on GlassFish

https://pdurbin.pagekite.me/oiosaml_on_glassfish

## What is this?

This repo was created to replicate the "Exception while loading the app : java.lang.IllegalStateException: ContainerBase.addChild: start: org.apache.catalina.LifecycleException: java.lang.RuntimeException: javax.xml.parsers.FactoryConfigurationError: Provider org.apache.xerces.jaxp.DocumentBuilderFactoryImpl not found" errors mentioned at http://irclog.greptilian.com/javaee/2013-10-29#i_35892 and http://irclog.greptilian.com/javaee/2013-10-30#i_36294 in a minimal project.

## Installing the OIOSAML jar into a local Maven repository 

I had to run a manual step like this to install the OIOSAML jar into your local Maven repository:

    murphy:oiosaml_on_glassfish pdurbin$ mvn deploy:deploy-file -DgroupId=dk.itst -DartifactId=oiosaml.java -Dversion=8501 -Dpackaging=jar -Dfile=/tmp//oiosaml.java-8501.jar -DrepositoryId=oiosaml_on_glassfish_jars -Durl=file:///Users/$USER/github/gists/7233390/oiosaml_on_glassfish/local_lib
    [INFO] Scanning for projects...
    [INFO]                                                                         
    [INFO] ------------------------------------------------------------------------
    [INFO] Building oiosaml_on_glassfish 1.0-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    [INFO] 
    [INFO] --- maven-deploy-plugin:2.5:deploy-file (default-cli) @ oiosaml_on_glassfish ---
    Uploading: file:///Users/pdurbin/github/gists/7233390/oiosaml_on_glassfish/local_lib/dk/itst/oiosaml.java/8501/oiosaml.java-8501.jar
    Uploaded: file:///Users/pdurbin/github/gists/7233390/oiosaml_on_glassfish/local_lib/dk/itst/oiosaml.java/8501/oiosaml.java-8501.jar (226 KB at 5793.7 KB/sec)
    Uploading: file:///Users/pdurbin/github/gists/7233390/oiosaml_on_glassfish/local_lib/dk/itst/oiosaml.java/8501/oiosaml.java-8501.pom
    Uploaded: file:///Users/pdurbin/github/gists/7233390/oiosaml_on_glassfish/local_lib/dk/itst/oiosaml.java/8501/oiosaml.java-8501.pom (391 B at 381.8 KB/sec)
    Downloading: file:///Users/pdurbin/github/gists/7233390/oiosaml_on_glassfish/local_lib/dk/itst/oiosaml.java/maven-metadata.xml
    Uploading: file:///Users/pdurbin/github/gists/7233390/oiosaml_on_glassfish/local_lib/dk/itst/oiosaml.java/maven-metadata.xml
    Uploaded: file:///Users/pdurbin/github/gists/7233390/oiosaml_on_glassfish/local_lib/dk/itst/oiosaml.java/maven-metadata.xml (297 B at 145.0 KB/sec)
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 0.638s
    [INFO] Finished at: Wed Oct 30 13:23:16 EDT 2013
    [INFO] Final Memory: 4M/81M
    [INFO] ------------------------------------------------------------------------
    murphy:oiosaml_on_glassfish pdurbin$

## Pagekite

For an Identity Provider (IdP) such as http://testshib.org to reach my laptop that isn't (otherwise) on a public IP address, I'm using https://pagekite.net

These are autodiscovered based on the url you've used for this page. Make sure you're using the official url.
Receive SAML Artifact response  http://pdurbin.pagekite.me/oiosaml_on_glassfish/saml/SAMLAssertionConsumer
Receive SAML POST response      http://pdurbin.pagekite.me/oiosaml_on_glassfish/saml/SAMLAssertionConsumer
Initiate single logout  http://pdurbin.pagekite.me/oiosaml_on_glassfish/saml/SAMLAssertionConsumer
Receive single logout response  http://pdurbin.pagekite.me/oiosaml_on_glassfish/saml/LogoutServiceHTTPRedirectResponse
Receive single logout request   http://pdurbin.pagekite.me/oiosaml_on_glassfish/saml/LogoutServiceHTTPRedirect
Receive SOAP single logout request      http://pdurbin.pagekite.me/oiosaml_on_glassfish/saml/LogoutServiceSOAP
Receive HTTP POST single logout request http://pdurbin.pagekite.me/oiosaml_on_glassfish/saml/LogoutServiceHTTPPost

## OIOSAML assurance level

I made this change (and undeployed and redeployed)...

    murphy:.oiosaml-oiosaml_on_glassfish pdurbin$ diff oiosaml-sp.properties.orig oiosaml-sp.properties
    10c10
    < oiosaml-sp.assurancelevel=2
    ---
    > oiosaml-sp.assurancelevel=0
    murphy:.oiosaml-oiosaml_on_glassfish pdurbin$ 

... to avoid this error:

HTTP Status 500 -

type Exception report

message

description The server encountered an internal error () that prevented it from fulfilling this request.

exception

java.lang.RuntimeException: Assurance level too low: 0, required: 2

note The full stack traces of the exception and its root causes are available in the GlassFish Server Open Source Edition 3.1.2.2 logs.
GlassFish Server Open Source Edition 3.1.2.2

## Expected output

At http://pdurbin.pagekite.me/oiosaml_on_glassfish/faces/saml.xhtml (after authenticating to the TestShib IdP) I'm getting the output I expect. OIOSAML is working:

    urn:oid:1.3.6.1.4.1.5923.1.1.1.10/[<?xml version="1.0" encoding="UTF-8"?><saml2:NameID xmlns:saml2="urn:oasis:names:tc:SAML:2.0:assertion" Format="urn:oasis:names:tc:SAML:2.0:nameid-format:persistent" NameQualifier="https://idp.testshib.org/idp/shibboleth" SPNameQualifier="pdurbin.pagekite.me">yNZHVwbZQwxl5Kuuultm1+n/jzM=</saml2:NameID>, null]

    urn:oid:1.3.6.1.4.1.5923.1.1.1.1/[Member]

    urn:oid:0.9.2342.19200300.100.1.1/[myself]

    urn:oid:2.5.4.3/[Me Myself And I]

    urn:oid:1.3.6.1.4.1.5923.1.1.1.6/[myself@testshib.org]

    urn:oid:2.5.4.20/[555-5555]

    urn:oid:1.3.6.1.4.1.5923.1.1.1.9/[Member@testshib.org]

    urn:oid:2.5.4.42/[Me Myself]

    urn:oid:1.3.6.1.4.1.5923.1.1.1.7/[urn:mace:dir:entitlement:common-lib-terms]

    urn:oid:2.5.4.4/[And I]

## Example error output from server.log

    [#|2013-10-31T09:02:50.935-0400|INFO|glassfish3.1.2|javax.enterprise.resource.webcontainer.jsf.config|_ThreadID=28;_ThreadName=Thread-4;|Initializing Mojarra 2.1.6 (SNAPSHOT 20111206) for context '/oiosaml_on_glassfish'|#]

    [#|2013-10-31T09:02:51.071-0400|SEVERE|glassfish3.1.2|javax.enterprise.resource.webcontainer.jsf.config|_ThreadID=28;_ThreadName=Thread-4;|Critical error during deployment: 
    com.sun.faces.config.ConfigurationException: java.util.concurrent.ExecutionException: com.sun.faces.config.ConfigurationException: Unable to parse document 'bundle://184.0:1/com/sun/faces/jsf-ri-runtime.xml': DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.
            at com.sun.faces.config.ConfigManager.getConfigDocuments(ConfigManager.java:672)
            at com.sun.faces.config.ConfigManager.initialize(ConfigManager.java:322)
            at com.sun.faces.config.ConfigureListener.contextInitialized(ConfigureListener.java:225)
            at org.apache.catalina.core.StandardContext.contextListenerStart(StandardContext.java:4750)
            at com.sun.enterprise.web.WebModule.contextListenerStart(WebModule.java:550)
            at org.apache.catalina.core.StandardContext.start(StandardContext.java:5366)
            at com.sun.enterprise.web.WebModule.start(WebModule.java:498)
            at org.apache.catalina.core.ContainerBase.addChildInternal(ContainerBase.java:917)
            at org.apache.catalina.core.ContainerBase.addChild(ContainerBase.java:901)
            at org.apache.catalina.core.StandardHost.addChild(StandardHost.java:733)
            at com.sun.enterprise.web.WebContainer.loadWebModule(WebContainer.java:2019)
            at com.sun.enterprise.web.WebContainer.loadWebModule(WebContainer.java:1669)
            at com.sun.enterprise.web.WebApplication.start(WebApplication.java:109)
            at org.glassfish.internal.data.EngineRef.start(EngineRef.java:130)
            at org.glassfish.internal.data.ModuleInfo.start(ModuleInfo.java:269)
            at org.glassfish.internal.data.ApplicationInfo.start(ApplicationInfo.java:301)
            at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:461)
            at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:240)
            at org.glassfish.deployment.admin.DeployCommand.execute(DeployCommand.java:389)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$1.execute(CommandRunnerImpl.java:348)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:363)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:1085)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.access$1200(CommandRunnerImpl.java:95)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1291)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1259)
            at com.sun.enterprise.v3.admin.AdminAdapter.doCommand(AdminAdapter.java:461)
            at com.sun.enterprise.v3.admin.AdminAdapter.service(AdminAdapter.java:212)
            at com.sun.grizzly.tcp.http11.GrizzlyAdapter.service(GrizzlyAdapter.java:179)
            at com.sun.enterprise.v3.server.HK2Dispatcher.dispath(HK2Dispatcher.java:117)
            at com.sun.enterprise.v3.services.impl.ContainerMapper$Hk2DispatcherCallable.call(ContainerMapper.java:354)
            at com.sun.enterprise.v3.services.impl.ContainerMapper.service(ContainerMapper.java:195)
            at com.sun.grizzly.http.ProcessorTask.invokeAdapter(ProcessorTask.java:860)
            at com.sun.grizzly.http.ProcessorTask.doProcess(ProcessorTask.java:757)
            at com.sun.grizzly.http.ProcessorTask.process(ProcessorTask.java:1056)
            at com.sun.grizzly.http.DefaultProtocolFilter.execute(DefaultProtocolFilter.java:229)
            at com.sun.grizzly.DefaultProtocolChain.executeProtocolFilter(DefaultProtocolChain.java:137)
            at com.sun.grizzly.DefaultProtocolChain.execute(DefaultProtocolChain.java:104)
            at com.sun.grizzly.DefaultProtocolChain.execute(DefaultProtocolChain.java:90)
            at com.sun.grizzly.http.HttpProtocolChain.execute(HttpProtocolChain.java:79)
            at com.sun.grizzly.ProtocolChainContextTask.doCall(ProtocolChainContextTask.java:54)
            at com.sun.grizzly.SelectionKeyContextTask.call(SelectionKeyContextTask.java:59)
            at com.sun.grizzly.ContextTask.run(ContextTask.java:71)
            at com.sun.grizzly.util.AbstractThreadPool$Worker.doWork(AbstractThreadPool.java:532)
            at com.sun.grizzly.util.AbstractThreadPool$Worker.run(AbstractThreadPool.java:513)
            at java.lang.Thread.run(Thread.java:695)
    Caused by: java.util.concurrent.ExecutionException: com.sun.faces.config.ConfigurationException: Unable to parse document 'bundle://184.0:1/com/sun/faces/jsf-ri-runtime.xml': DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.
            at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:222)
            at java.util.concurrent.FutureTask.get(FutureTask.java:83)
            at com.sun.faces.config.ConfigManager.getConfigDocuments(ConfigManager.java:670)
            ... 44 more
    Caused by: com.sun.faces.config.ConfigurationException: Unable to parse document 'bundle://184.0:1/com/sun/faces/jsf-ri-runtime.xml': DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.
            at com.sun.faces.config.ConfigManager$ParseTask.call(ConfigManager.java:920)
            at com.sun.faces.config.ConfigManager$ParseTask.call(ConfigManager.java:865)
            at java.util.concurrent.FutureTask$Sync.innerRun(FutureTask.java:303)
            at java.util.concurrent.FutureTask.run(FutureTask.java:138)
            at com.sun.faces.config.ConfigManager.getConfigDocuments(ConfigManager.java:656)
            ... 44 more
    Caused by: org.apache.xerces.impl.dv.DVFactoryException: DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.
            at org.apache.xerces.impl.dv.DTDDVFactory.getInstance(Unknown Source)
            at org.apache.xerces.impl.dv.DTDDVFactory.getInstance(Unknown Source)
            at org.apache.xerces.impl.xs.opti.SchemaParsingConfig.<init>(Unknown Source)
            at org.apache.xerces.impl.xs.opti.SchemaParsingConfig.<init>(Unknown Source)
            at org.apache.xerces.impl.xs.traversers.XSDHandler.<init>(Unknown Source)
            at org.apache.xerces.impl.xs.traversers.XSDHandler.<init>(Unknown Source)
            at org.apache.xerces.impl.xs.XMLSchemaLoader.<init>(Unknown Source)
            at org.apache.xerces.impl.xs.XMLSchemaLoader.<init>(Unknown Source)
            at org.apache.xerces.impl.xs.XMLSchemaValidator.<init>(Unknown Source)
            at org.apache.xerces.jaxp.validation.XMLSchemaValidatorComponentManager.<init>(Unknown Source)
            at org.apache.xerces.jaxp.validation.ValidatorHandlerImpl.<init>(Unknown Source)
            at org.apache.xerces.jaxp.validation.AbstractXMLSchema.newValidatorHandler(Unknown Source)
            at org.apache.xerces.jaxp.DocumentBuilderImpl.<init>(Unknown Source)
            at org.apache.xerces.jaxp.DocumentBuilderFactoryImpl.newDocumentBuilder(Unknown Source)
            at com.sun.faces.config.ConfigManager$ParseTask.getBuilderForSchema(ConfigManager.java:1130)
            at com.sun.faces.config.ConfigManager$ParseTask.getDocument(ConfigManager.java:999)
            at com.sun.faces.config.ConfigManager$ParseTask.call(ConfigManager.java:911)
            ... 48 more
    |#]

    [#|2013-10-31T09:02:51.072-0400|SEVERE|glassfish3.1.2|org.apache.catalina.core.StandardContext|_ThreadID=28;_ThreadName=Thread-4;|PWC1306: Startup of context /oiosaml_on_glassfish failed due to previous errors|#]

    [#|2013-10-31T09:02:51.074-0400|SEVERE|glassfish3.1.2|org.apache.catalina.core.StandardContext|_ThreadID=28;_ThreadName=Thread-4;|PWC1305: Exception during cleanup after start failed
    org.apache.catalina.LifecycleException: PWC2769: Manager has not yet been started
            at org.apache.catalina.session.StandardManager.stop(StandardManager.java:873)
            at org.apache.catalina.core.StandardContext.stop(StandardContext.java:5571)
            at com.sun.enterprise.web.WebModule.stop(WebModule.java:527)
            at org.apache.catalina.core.StandardContext.start(StandardContext.java:5384)
            at com.sun.enterprise.web.WebModule.start(WebModule.java:498)
            at org.apache.catalina.core.ContainerBase.addChildInternal(ContainerBase.java:917)
            at org.apache.catalina.core.ContainerBase.addChild(ContainerBase.java:901)
            at org.apache.catalina.core.StandardHost.addChild(StandardHost.java:733)
            at com.sun.enterprise.web.WebContainer.loadWebModule(WebContainer.java:2019)
            at com.sun.enterprise.web.WebContainer.loadWebModule(WebContainer.java:1669)
            at com.sun.enterprise.web.WebApplication.start(WebApplication.java:109)
            at org.glassfish.internal.data.EngineRef.start(EngineRef.java:130)
            at org.glassfish.internal.data.ModuleInfo.start(ModuleInfo.java:269)
            at org.glassfish.internal.data.ApplicationInfo.start(ApplicationInfo.java:301)
            at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:461)
            at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:240)
            at org.glassfish.deployment.admin.DeployCommand.execute(DeployCommand.java:389)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$1.execute(CommandRunnerImpl.java:348)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:363)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:1085)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.access$1200(CommandRunnerImpl.java:95)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1291)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1259)
            at com.sun.enterprise.v3.admin.AdminAdapter.doCommand(AdminAdapter.java:461)
            at com.sun.enterprise.v3.admin.AdminAdapter.service(AdminAdapter.java:212)
            at com.sun.grizzly.tcp.http11.GrizzlyAdapter.service(GrizzlyAdapter.java:179)
            at com.sun.enterprise.v3.server.HK2Dispatcher.dispath(HK2Dispatcher.java:117)
            at com.sun.enterprise.v3.services.impl.ContainerMapper$Hk2DispatcherCallable.call(ContainerMapper.java:354)
            at com.sun.enterprise.v3.services.impl.ContainerMapper.service(ContainerMapper.java:195)
            at com.sun.grizzly.http.ProcessorTask.invokeAdapter(ProcessorTask.java:860)
            at com.sun.grizzly.http.ProcessorTask.doProcess(ProcessorTask.java:757)
            at com.sun.grizzly.http.ProcessorTask.process(ProcessorTask.java:1056)
            at com.sun.grizzly.http.DefaultProtocolFilter.execute(DefaultProtocolFilter.java:229)
            at com.sun.grizzly.DefaultProtocolChain.executeProtocolFilter(DefaultProtocolChain.java:137)
            at com.sun.grizzly.DefaultProtocolChain.execute(DefaultProtocolChain.java:104)
            at com.sun.grizzly.DefaultProtocolChain.execute(DefaultProtocolChain.java:90)
            at com.sun.grizzly.http.HttpProtocolChain.execute(HttpProtocolChain.java:79)
            at com.sun.grizzly.ProtocolChainContextTask.doCall(ProtocolChainContextTask.java:54)
            at com.sun.grizzly.SelectionKeyContextTask.call(SelectionKeyContextTask.java:59)
            at com.sun.grizzly.ContextTask.run(ContextTask.java:71)
            at com.sun.grizzly.util.AbstractThreadPool$Worker.doWork(AbstractThreadPool.java:532)
            at com.sun.grizzly.util.AbstractThreadPool$Worker.run(AbstractThreadPool.java:513)
            at java.lang.Thread.run(Thread.java:695)
    |#]

    [#|2013-10-31T09:02:51.074-0400|SEVERE|glassfish3.1.2|org.apache.catalina.core.ContainerBase|_ThreadID=28;_ThreadName=Thread-4;|ContainerBase.addChild: start: 
    org.apache.catalina.LifecycleException: java.lang.RuntimeException: com.sun.faces.config.ConfigurationException: java.util.concurrent.ExecutionException: com.sun.faces.config.ConfigurationException: Unable to parse document 'bundle://184.0:1/com/sun/faces/jsf-ri-runtime.xml': DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.
            at org.apache.catalina.core.StandardContext.start(StandardContext.java:5389)
            at com.sun.enterprise.web.WebModule.start(WebModule.java:498)
            at org.apache.catalina.core.ContainerBase.addChildInternal(ContainerBase.java:917)
            at org.apache.catalina.core.ContainerBase.addChild(ContainerBase.java:901)
            at org.apache.catalina.core.StandardHost.addChild(StandardHost.java:733)
            at com.sun.enterprise.web.WebContainer.loadWebModule(WebContainer.java:2019)
            at com.sun.enterprise.web.WebContainer.loadWebModule(WebContainer.java:1669)
            at com.sun.enterprise.web.WebApplication.start(WebApplication.java:109)
            at org.glassfish.internal.data.EngineRef.start(EngineRef.java:130)
            at org.glassfish.internal.data.ModuleInfo.start(ModuleInfo.java:269)
            at org.glassfish.internal.data.ApplicationInfo.start(ApplicationInfo.java:301)
            at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:461)
            at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:240)
            at org.glassfish.deployment.admin.DeployCommand.execute(DeployCommand.java:389)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$1.execute(CommandRunnerImpl.java:348)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:363)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:1085)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.access$1200(CommandRunnerImpl.java:95)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1291)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1259)
            at com.sun.enterprise.v3.admin.AdminAdapter.doCommand(AdminAdapter.java:461)
            at com.sun.enterprise.v3.admin.AdminAdapter.service(AdminAdapter.java:212)
            at com.sun.grizzly.tcp.http11.GrizzlyAdapter.service(GrizzlyAdapter.java:179)
            at com.sun.enterprise.v3.server.HK2Dispatcher.dispath(HK2Dispatcher.java:117)
            at com.sun.enterprise.v3.services.impl.ContainerMapper$Hk2DispatcherCallable.call(ContainerMapper.java:354)
            at com.sun.enterprise.v3.services.impl.ContainerMapper.service(ContainerMapper.java:195)
            at com.sun.grizzly.http.ProcessorTask.invokeAdapter(ProcessorTask.java:860)
            at com.sun.grizzly.http.ProcessorTask.doProcess(ProcessorTask.java:757)
            at com.sun.grizzly.http.ProcessorTask.process(ProcessorTask.java:1056)
            at com.sun.grizzly.http.DefaultProtocolFilter.execute(DefaultProtocolFilter.java:229)
            at com.sun.grizzly.DefaultProtocolChain.executeProtocolFilter(DefaultProtocolChain.java:137)
            at com.sun.grizzly.DefaultProtocolChain.execute(DefaultProtocolChain.java:104)
            at com.sun.grizzly.DefaultProtocolChain.execute(DefaultProtocolChain.java:90)
            at com.sun.grizzly.http.HttpProtocolChain.execute(HttpProtocolChain.java:79)
            at com.sun.grizzly.ProtocolChainContextTask.doCall(ProtocolChainContextTask.java:54)
            at com.sun.grizzly.SelectionKeyContextTask.call(SelectionKeyContextTask.java:59)
            at com.sun.grizzly.ContextTask.run(ContextTask.java:71)
            at com.sun.grizzly.util.AbstractThreadPool$Worker.doWork(AbstractThreadPool.java:532)
            at com.sun.grizzly.util.AbstractThreadPool$Worker.run(AbstractThreadPool.java:513)
            at java.lang.Thread.run(Thread.java:695)
    Caused by: java.lang.RuntimeException: com.sun.faces.config.ConfigurationException: java.util.concurrent.ExecutionException: com.sun.faces.config.ConfigurationException: Unable to parse document 'bundle://184.0:1/com/sun/faces/jsf-ri-runtime.xml': DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.
            at com.sun.faces.config.ConfigureListener.contextInitialized(ConfigureListener.java:292)
            at org.apache.catalina.core.StandardContext.contextListenerStart(StandardContext.java:4750)
            at com.sun.enterprise.web.WebModule.contextListenerStart(WebModule.java:550)
            at org.apache.catalina.core.StandardContext.start(StandardContext.java:5366)
            ... 39 more
    Caused by: com.sun.faces.config.ConfigurationException: java.util.concurrent.ExecutionException: com.sun.faces.config.ConfigurationException: Unable to parse document 'bundle://184.0:1/com/sun/faces/jsf-ri-runtime.xml': DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.
            at com.sun.faces.config.ConfigManager.getConfigDocuments(ConfigManager.java:672)
            at com.sun.faces.config.ConfigManager.initialize(ConfigManager.java:322)
            at com.sun.faces.config.ConfigureListener.contextInitialized(ConfigureListener.java:225)
            ... 42 more
    Caused by: java.util.concurrent.ExecutionException: com.sun.faces.config.ConfigurationException: Unable to parse document 'bundle://184.0:1/com/sun/faces/jsf-ri-runtime.xml': DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.
            at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:222)
            at java.util.concurrent.FutureTask.get(FutureTask.java:83)
            at com.sun.faces.config.ConfigManager.getConfigDocuments(ConfigManager.java:670)
            ... 44 more
    Caused by: com.sun.faces.config.ConfigurationException: Unable to parse document 'bundle://184.0:1/com/sun/faces/jsf-ri-runtime.xml': DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.
            at com.sun.faces.config.ConfigManager$ParseTask.call(ConfigManager.java:920)
            at com.sun.faces.config.ConfigManager$ParseTask.call(ConfigManager.java:865)
            at java.util.concurrent.FutureTask$Sync.innerRun(FutureTask.java:303)
            at java.util.concurrent.FutureTask.run(FutureTask.java:138)
            at com.sun.faces.config.ConfigManager.getConfigDocuments(ConfigManager.java:656)
            ... 44 more
    Caused by: org.apache.xerces.impl.dv.DVFactoryException: DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.
            at org.apache.xerces.impl.dv.DTDDVFactory.getInstance(Unknown Source)
            at org.apache.xerces.impl.dv.DTDDVFactory.getInstance(Unknown Source)
            at org.apache.xerces.impl.xs.opti.SchemaParsingConfig.<init>(Unknown Source)
            at org.apache.xerces.impl.xs.opti.SchemaParsingConfig.<init>(Unknown Source)
            at org.apache.xerces.impl.xs.traversers.XSDHandler.<init>(Unknown Source)
            at org.apache.xerces.impl.xs.traversers.XSDHandler.<init>(Unknown Source)
            at org.apache.xerces.impl.xs.XMLSchemaLoader.<init>(Unknown Source)
            at org.apache.xerces.impl.xs.XMLSchemaLoader.<init>(Unknown Source)
            at org.apache.xerces.impl.xs.XMLSchemaValidator.<init>(Unknown Source)
            at org.apache.xerces.jaxp.validation.XMLSchemaValidatorComponentManager.<init>(Unknown Source)
            at org.apache.xerces.jaxp.validation.ValidatorHandlerImpl.<init>(Unknown Source)
            at org.apache.xerces.jaxp.validation.AbstractXMLSchema.newValidatorHandler(Unknown Source)
            at org.apache.xerces.jaxp.DocumentBuilderImpl.<init>(Unknown Source)
            at org.apache.xerces.jaxp.DocumentBuilderFactoryImpl.newDocumentBuilder(Unknown Source)
            at com.sun.faces.config.ConfigManager$ParseTask.getBuilderForSchema(ConfigManager.java:1130)
            at com.sun.faces.config.ConfigManager$ParseTask.getDocument(ConfigManager.java:999)
            at com.sun.faces.config.ConfigManager$ParseTask.call(ConfigManager.java:911)
            ... 48 more
    |#]

    [#|2013-10-31T09:02:51.075-0400|WARNING|glassfish3.1.2|javax.enterprise.system.container.web.com.sun.enterprise.web|_ThreadID=28;_ThreadName=Thread-4;|java.lang.IllegalStateException: ContainerBase.addChild: start: org.apache.catalina.LifecycleException: java.lang.RuntimeException: com.sun.faces.config.ConfigurationException: java.util.concurrent.ExecutionException: com.sun.faces.config.ConfigurationException: Unable to parse document 'bundle://184.0:1/com/sun/faces/jsf-ri-runtime.xml': DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.
    java.lang.IllegalStateException: ContainerBase.addChild: start: org.apache.catalina.LifecycleException: java.lang.RuntimeException: com.sun.faces.config.ConfigurationException: java.util.concurrent.ExecutionException: com.sun.faces.config.ConfigurationException: Unable to parse document 'bundle://184.0:1/com/sun/faces/jsf-ri-runtime.xml': DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.
            at org.apache.catalina.core.ContainerBase.addChildInternal(ContainerBase.java:921)
            at org.apache.catalina.core.ContainerBase.addChild(ContainerBase.java:901)
            at org.apache.catalina.core.StandardHost.addChild(StandardHost.java:733)
            at com.sun.enterprise.web.WebContainer.loadWebModule(WebContainer.java:2019)
            at com.sun.enterprise.web.WebContainer.loadWebModule(WebContainer.java:1669)
            at com.sun.enterprise.web.WebApplication.start(WebApplication.java:109)
            at org.glassfish.internal.data.EngineRef.start(EngineRef.java:130)
            at org.glassfish.internal.data.ModuleInfo.start(ModuleInfo.java:269)
            at org.glassfish.internal.data.ApplicationInfo.start(ApplicationInfo.java:301)
            at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:461)
            at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:240)
            at org.glassfish.deployment.admin.DeployCommand.execute(DeployCommand.java:389)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$1.execute(CommandRunnerImpl.java:348)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:363)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:1085)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.access$1200(CommandRunnerImpl.java:95)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1291)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1259)
            at com.sun.enterprise.v3.admin.AdminAdapter.doCommand(AdminAdapter.java:461)
            at com.sun.enterprise.v3.admin.AdminAdapter.service(AdminAdapter.java:212)
            at com.sun.grizzly.tcp.http11.GrizzlyAdapter.service(GrizzlyAdapter.java:179)
            at com.sun.enterprise.v3.server.HK2Dispatcher.dispath(HK2Dispatcher.java:117)
            at com.sun.enterprise.v3.services.impl.ContainerMapper$Hk2DispatcherCallable.call(ContainerMapper.java:354)
            at com.sun.enterprise.v3.services.impl.ContainerMapper.service(ContainerMapper.java:195)
            at com.sun.grizzly.http.ProcessorTask.invokeAdapter(ProcessorTask.java:860)
            at com.sun.grizzly.http.ProcessorTask.doProcess(ProcessorTask.java:757)
            at com.sun.grizzly.http.ProcessorTask.process(ProcessorTask.java:1056)
            at com.sun.grizzly.http.DefaultProtocolFilter.execute(DefaultProtocolFilter.java:229)
            at com.sun.grizzly.DefaultProtocolChain.executeProtocolFilter(DefaultProtocolChain.java:137)
            at com.sun.grizzly.DefaultProtocolChain.execute(DefaultProtocolChain.java:104)
            at com.sun.grizzly.DefaultProtocolChain.execute(DefaultProtocolChain.java:90)
            at com.sun.grizzly.http.HttpProtocolChain.execute(HttpProtocolChain.java:79)
            at com.sun.grizzly.ProtocolChainContextTask.doCall(ProtocolChainContextTask.java:54)
            at com.sun.grizzly.SelectionKeyContextTask.call(SelectionKeyContextTask.java:59)
            at com.sun.grizzly.ContextTask.run(ContextTask.java:71)
            at com.sun.grizzly.util.AbstractThreadPool$Worker.doWork(AbstractThreadPool.java:532)
            at com.sun.grizzly.util.AbstractThreadPool$Worker.run(AbstractThreadPool.java:513)
            at java.lang.Thread.run(Thread.java:695)
    |#]

    [#|2013-10-31T09:02:51.076-0400|SEVERE|glassfish3.1.2|javax.enterprise.system.tools.admin.org.glassfish.deployment.admin|_ThreadID=28;_ThreadName=Thread-4;|Exception while invoking class com.sun.enterprise.web.WebApplication start method
    java.lang.Exception: java.lang.IllegalStateException: ContainerBase.addChild: start: org.apache.catalina.LifecycleException: java.lang.RuntimeException: com.sun.faces.config.ConfigurationException: java.util.concurrent.ExecutionException: com.sun.faces.config.ConfigurationException: Unable to parse document 'bundle://184.0:1/com/sun/faces/jsf-ri-runtime.xml': DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.
            at com.sun.enterprise.web.WebApplication.start(WebApplication.java:138)
            at org.glassfish.internal.data.EngineRef.start(EngineRef.java:130)
            at org.glassfish.internal.data.ModuleInfo.start(ModuleInfo.java:269)
            at org.glassfish.internal.data.ApplicationInfo.start(ApplicationInfo.java:301)
            at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:461)
            at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:240)
            at org.glassfish.deployment.admin.DeployCommand.execute(DeployCommand.java:389)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$1.execute(CommandRunnerImpl.java:348)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:363)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:1085)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl.access$1200(CommandRunnerImpl.java:95)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1291)
            at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1259)
            at com.sun.enterprise.v3.admin.AdminAdapter.doCommand(AdminAdapter.java:461)
            at com.sun.enterprise.v3.admin.AdminAdapter.service(AdminAdapter.java:212)
            at com.sun.grizzly.tcp.http11.GrizzlyAdapter.service(GrizzlyAdapter.java:179)
            at com.sun.enterprise.v3.server.HK2Dispatcher.dispath(HK2Dispatcher.java:117)
            at com.sun.enterprise.v3.services.impl.ContainerMapper$Hk2DispatcherCallable.call(ContainerMapper.java:354)
            at com.sun.enterprise.v3.services.impl.ContainerMapper.service(ContainerMapper.java:195)
            at com.sun.grizzly.http.ProcessorTask.invokeAdapter(ProcessorTask.java:860)
            at com.sun.grizzly.http.ProcessorTask.doProcess(ProcessorTask.java:757)
            at com.sun.grizzly.http.ProcessorTask.process(ProcessorTask.java:1056)
            at com.sun.grizzly.http.DefaultProtocolFilter.execute(DefaultProtocolFilter.java:229)
            at com.sun.grizzly.DefaultProtocolChain.executeProtocolFilter(DefaultProtocolChain.java:137)
            at com.sun.grizzly.DefaultProtocolChain.execute(DefaultProtocolChain.java:104)
            at com.sun.grizzly.DefaultProtocolChain.execute(DefaultProtocolChain.java:90)
            at com.sun.grizzly.http.HttpProtocolChain.execute(HttpProtocolChain.java:79)
            at com.sun.grizzly.ProtocolChainContextTask.doCall(ProtocolChainContextTask.java:54)
            at com.sun.grizzly.SelectionKeyContextTask.call(SelectionKeyContextTask.java:59)
            at com.sun.grizzly.ContextTask.run(ContextTask.java:71)
            at com.sun.grizzly.util.AbstractThreadPool$Worker.doWork(AbstractThreadPool.java:532)
            at com.sun.grizzly.util.AbstractThreadPool$Worker.run(AbstractThreadPool.java:513)
            at java.lang.Thread.run(Thread.java:695)
    |#]

    [#|2013-10-31T09:02:51.076-0400|SEVERE|glassfish3.1.2|javax.enterprise.system.core.com.sun.enterprise.v3.server|_ThreadID=28;_ThreadName=Thread-4;|Exception while loading the app|#]

    [#|2013-10-31T09:02:51.215-0400|SEVERE|glassfish3.1.2|javax.enterprise.system.tools.admin.org.glassfish.deployment.admin|_ThreadID=28;_ThreadName=Thread-4;|Exception while loading the app : java.lang.IllegalStateException: ContainerBase.addChild: start: org.apache.catalina.LifecycleException: java.lang.RuntimeException: com.sun.faces.config.ConfigurationException: java.util.concurrent.ExecutionException: com.sun.faces.config.ConfigurationException: Unable to parse document 'bundle://184.0:1/com/sun/faces/jsf-ri-runtime.xml': DTD factory class org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl does not extend from DTDDVFactory.|#]

## java.endorsed.dirs solution?

"OpenSAML relies heavily on JAXP 1.3 for low-level XML parsing and creation. Some JREs, most notably Sun's, ship with horribly broken JAXP implementations. As such you may (in the Sun JRE case, you must) endorse a different JAXP provider. At the time of this writing, the only known JAXP provider to work is the Apache Xerces & Xalan projects." -- https://wiki.shibboleth.net/confluence/display/OpenSAML/OSTwoUsrManJavaInstall

To set up the java.endorsed.dirs stuff they use maven-dependency-plugin to endorse xml-apis, xercesImpl, xalan, etc.: http://svn.shibboleth.net/view/java-parent-projects/java-parent-project-v3/trunk/pom.xml?view=markup
