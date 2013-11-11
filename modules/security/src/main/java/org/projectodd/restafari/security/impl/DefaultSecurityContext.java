package org.projectodd.restafari.security.impl;

import java.security.Principal;
import java.util.Set;

import org.projectodd.restafari.security.spi.AuthToken;
import org.projectodd.restafari.spi.SecurityContext;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class DefaultSecurityContext implements SecurityContext {

    private final Principal principal;
    private Set<String> realmRoles;
    private Set<String> applicationRoles;

    private DefaultSecurityContext(Principal principal, Set<String> realmRoles, Set<String> appRoles) {
        this.principal = principal;
        this.realmRoles = realmRoles;
        this.applicationRoles = appRoles;
    }

    public static SecurityContext createFromAuthToken(final AuthToken authToken) {
        if (authToken.isAnonymous()) {
            return SecurityContext.ANONYMOUS;
        } else {
            Principal principal = () -> authToken.getUsername();
            return new DefaultSecurityContext(principal, authToken.getRealmRoles(), authToken.getApplicationRoles());
        }
    }


    @Override
    public Principal getPrincipal() {
        return principal;
    }

    @Override
    public Set<String> getRealmRoles() {
        return realmRoles;
    }

    @Override
    public Set<String> getApplicationRoles() {
        return applicationRoles;
    }
}
