# OIOSAML on GlassFish

https://pdurbin.pagekite.me/oiosaml_on_glassfish

## What is this?

This gist was created to replicate the "Exception while loading the app : java.lang.IllegalStateException: ContainerBase.addChild: start: org.apache.catalina.LifecycleException: java.lang.RuntimeException: javax.xml.parsers.FactoryConfigurationError: Provider org.apache.xerces.jaxp.DocumentBuilderFactoryImpl not found" errors mentioned at http://irclog.greptilian.com/javaee/2013-10-29#i_35892 and http://irclog.greptilian.com/javaee/2013-10-30#i_36294 in a minimal project.

Unfortunately, I can't replicate the same behavior. I don't get that error when saving and deploying. I don't have to restart glassfish to redeploy.

## Installing the OIOSAML jarinto a local Maven repository 

You'll have to run a manual step like this to install the OIOSAML jar into your local Maven repository:

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
