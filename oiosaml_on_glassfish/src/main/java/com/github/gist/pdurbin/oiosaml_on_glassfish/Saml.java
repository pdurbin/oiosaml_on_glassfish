package com.github.gist.pdurbin.oiosaml_on_glassfish;

import dk.itst.oiosaml.sp.UserAssertion;
import dk.itst.oiosaml.sp.UserAttribute;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@ViewScoped
@Named("saml")
public class Saml {

    private static final Logger logger = Logger.getLogger(Saml.class.getCanonicalName());
    private final String assertName = "dk.itst.oiosaml.userassertion";
    // an IdP is a Shibboleth Identity Provider
    private Map<String, List<String>> attributesFromIdp = new HashMap<String, List<String>>();

    public Map<String, List<String>> getAttributesFromIdp() {
        return attributesFromIdp;
    }
    
    public Saml() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) context.getSession(true);
        UserAssertion userAssert = (UserAssertion) session.getAttribute(assertName);
        Collection<UserAttribute> allAttributesCollection = userAssert.getAllAttributes();
        for (UserAttribute userAttribute : allAttributesCollection) {
            logger.info(userAttribute.getName() + "/" + userAttribute.getValues());
            /*
             urn:oid:1.3.6.1.4.1.5923.1.1.1.10/[<?xml version="1.0" encoding="UTF-8"?><saml2:NameID xmlns:saml2="urn:oasis:names:tc:SAML:2.0:assertion" Format="urn:oasis:names:tc:SAML:2.0:nameid-format:persistent" NameQualifier="https://idp.testshib.org/idp/shibboleth" SPNameQualifier="pdurbin.pagekite.me">yNZHVwbZQwxl5Kuuultm1+n/jzM=</saml2:NameID>, null]|#]
             urn:oid:1.3.6.1.4.1.5923.1.1.1.1/[Member]|#]
             urn:oid:0.9.2342.19200300.100.1.1/[myself]|#]
             urn:oid:2.5.4.3/[Me Myself And I]|#]
             urn:oid:1.3.6.1.4.1.5923.1.1.1.6/[myself@testshib.org]|#]
             urn:oid:2.5.4.20/[555-5555]|#]
             urn:oid:1.3.6.1.4.1.5923.1.1.1.9/[Member@testshib.org]|#]
             urn:oid:1.3.6.1.4.1.5923.1.1.1.7/[urn:mace:dir:entitlement:common-lib-terms]|#]
             urn:oid:2.5.4.42/[Me Myself]|#]
             urn:oid:2.5.4.4/[And I]|#]
             */
            attributesFromIdp.put(userAttribute.getName(), userAttribute.getValues());
        }
    }
}
