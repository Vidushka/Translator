package com.hsenid.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Vidushka on 05/05/17.
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        final String targetUrl = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * Redirect to relevant url based on authentication.(based on user role)
     *
     * @param authentication Authentication object returned from authentication process.
     * @return String url of authenticated user.
     */
    protected String determineTargetUrl(final Authentication authentication) {
        String targetUrl = "";
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            switch (grantedAuthority.getAuthority()) {
                case "ROLE_USER":
                    targetUrl = "/user";
                    break;
                case "ROLE_ADMIN":
                    targetUrl = "/admin";
                    break;
            }
        }
        return targetUrl;
    }
}
