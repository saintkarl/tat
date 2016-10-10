/**
 * 
 */
package com.retirement.tat.web.security;

import com.retirement.tat.common.Constants;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Nguyen Hai Vien
 *
 */
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private transient final Logger log = Logger.getLogger(getClass());
    private String userLandingUrl = "/rlwebsite/index.html";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        String userGroup = authentication.getAuthorities().toArray()[0].toString();
        if(userGroup.equalsIgnoreCase(Constants.USER_ROLES)){
            clearAuthenticationAttributes(request);
            getRedirectStrategy().sendRedirect(request, response, userLandingUrl);
        }else if(isAlwaysUseDefaultTargetUrl() || StringUtils.hasText(request.getParameter(getTargetUrlParameter()))){
        	clearAuthenticationAttributes(request);
            String targetUrl = getDefaultTargetUrl();
            logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
    }

    public String getUserLandingUrl() {
        return userLandingUrl;
    }

    public void setUserLandingUrl(String userLandingUrl) {
        this.userLandingUrl = userLandingUrl;
    }
}
