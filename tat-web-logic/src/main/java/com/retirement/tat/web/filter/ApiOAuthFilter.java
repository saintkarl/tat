package com.retirement.tat.web.filter;

import com.retirement.tat.common.Constants;
import com.retirement.tat.common.util.CacheUtil;
import com.retirement.tat.core.business.UserManagementLocalBean;
import com.retirement.tat.core.dto.mobile.UserPrincipalDTO;
import com.retirement.tat.web.context.AppContext;
import com.retirement.tat.web.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * OAuth security based-on JWT
 * User: Hieu Le
 * Date: 6/23/16
 * Time: 9:08 AM
 */
public class ApiOAuthFilter extends OncePerRequestFilter {
    private static final String SEC_FILE_LOCATION_INIT_PARAM_NAME = "oauthSecConfig";
    private static final String NO_FILTER_ROLE = "ANONYMOUS";

    private List<OAuthSecInterceptUrl> interceptUrls;
    private List<String> anonymousPatterns;


    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        interceptUrls = new ArrayList<>();
        anonymousPatterns = new ArrayList<>();
        readSecurityConfig();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        boolean isAllowAccess = false;
        for (String pattern : anonymousPatterns) {
            if (RequestUtil.isMatchWildcard(pattern, requestUri)) {
                isAllowAccess = true;
                break;
            }
        }

        UserPrincipalDTO principal = null;
        if (!isAllowAccess) {
            principal = getPrincipal(request);
            if (principal != null) {
                isAllowAccess = true;
                for (OAuthSecInterceptUrl oAuthSecInterceptUrl : interceptUrls) {
                    Boolean[] isAllowAccessUri = isAllowAccessUri(oAuthSecInterceptUrl, principal.getAuthorities(), requestUri);
                    if (isAllowAccessUri[0] && isAllowAccessUri[1]) {
                        break;
                    } else if (!isAllowAccessUri[0] && isAllowAccessUri[1]) {
                        isAllowAccess = false;
                        break;
                    }
                }
            }
        }

        if (!isAllowAccess) {
            response.sendError(403);
        } else {
            OAuthSecUtils.setPrincipal(principal);
            filterChain.doFilter(request, response);
        }

    }

    private String getUserRoleFromToken(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(jwt)) {
            Map<String, Object> claims = JWTUtils.verify(jwt);
            if (claims != null) {
                Object obj = claims.get(Constants.MOBILE_JWT_CLAIM_USER_ROLE);
                if (obj != null) {
                    return obj.toString();
                }
            }
        }
        return null;
    }

    private UserPrincipalDTO getPrincipal(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(jwt)) {
            Map<String, Object> claims = JWTUtils.verify(jwt);
            if (claims != null) {
                Object obj = claims.get(Constants.MOBILE_JWT_CLAIM_USER_ID);
                if (obj != null) {
                    UserPrincipalDTO principalDTO = (UserPrincipalDTO)CacheUtil.getInstance().getValue(Constants.MOBILE_JWT_CACHE_PREFIX + obj);
                    if (principalDTO == null) {
                        principalDTO = OAuthSecUtils.buildUserPrincipal(Long.valueOf(obj.toString()), AppContext.getApplicationContext().getBean(UserManagementLocalBean.class));
                        CacheUtil.getInstance().putValue(Constants.MOBILE_JWT_CACHE_PREFIX + obj, principalDTO, Constants.MOBILE_PRINCIPAL_CACHE_EXPIRE_TIME_SECS);
                    }
                    if (principalDTO != null) {
                        return principalDTO;
                    }

                }
            }
        }
        return null;
    }

    private Boolean[] isAllowAccessUri(OAuthSecInterceptUrl oAuthSecInterceptUrl, List<String> authorities, String requestUri) {
        boolean isAllow = true, isMatchPattern = false;
        if (RequestUtil.isMatchWildcard(oAuthSecInterceptUrl.getPattern(), requestUri)) {
            boolean temp = false;
            isMatchPattern = true;
            for (String role : oAuthSecInterceptUrl.getRoles()) {
                if (authorities.contains(role)) {
                    temp = true;
                    break;
                }
            }
            if (!temp) {
                isAllow = false;
            }
        }
        return new Boolean[]{isAllow, isMatchPattern};
    }

    private void readSecurityConfig() throws ServletException {
        try {
            String configFileLocation = getServletContext().getInitParameter(SEC_FILE_LOCATION_INIT_PARAM_NAME);
            if (StringUtils.isNotBlank(configFileLocation)) {
                File file = new File(getServletContext().getRealPath(configFileLocation));
                if (file.isFile()) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(OAuthSecXml.class);

                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    OAuthSecXml oAuthSecXml = (OAuthSecXml) jaxbUnmarshaller.unmarshal(file);

                    buildInterceptUrls(oAuthSecXml);
                } else {
                    throw new ServletException("NOT found OAuth Security config file");
                }
            } else {
                throw new ServletException("NOT found OAuth Security config");
            }

        } catch (ServletException e) {
            throw e;
        } catch (Exception e) {
            throw new ServletException("Could NOT read OAuth Security config file", e);
        }

    }

    private void buildInterceptUrls(OAuthSecXml oAuthSecXml) throws ServletException {
        Set<String> patternSet = new HashSet<>();
        try {
            for (OAuthSecXml.InterceptUrlXml interceptUrlXml : oAuthSecXml.getInterceptUrls()) {
                if (StringUtils.isNotBlank(interceptUrlXml.getPattern()) && StringUtils.isNotBlank(interceptUrlXml.getAccess())) {
                    if (!patternSet.contains(interceptUrlXml.getPattern())) {
                        Object[] objArr = parseAccess(interceptUrlXml.getAccess());
                        if ((Boolean)objArr[1]) {
                            anonymousPatterns.add(interceptUrlXml.getPattern());
                        } else {
                            OAuthSecInterceptUrl oAuthSecInterceptUrl = new OAuthSecInterceptUrl();
                            oAuthSecInterceptUrl.setPattern(interceptUrlXml.getPattern());
                            oAuthSecInterceptUrl.setRoles((List<String>)objArr[0]);
                            interceptUrls.add(oAuthSecInterceptUrl);
                        }

                        patternSet.add(interceptUrlXml.getPattern());
                    }
                } else {
                    throw new ServletException("Empty 'pattern/access' property in OAuth Security config file");
                }
            }
        } catch (ServletException e) {
            throw e;
        } catch (Exception e) {
            throw new ServletException("Could NOT parse OAuth Security config file", e);
        }
    }

    private Object[] parseAccess(String access) throws ServletException {
        boolean isAno = false;
        String[] arr = StringUtils.deleteWhitespace(access).split(",");
        List<String> roles = new ArrayList<>(arr.length);
        for (String s : arr) {
            if (StringUtils.isNotBlank(s)) {
                if (NO_FILTER_ROLE.equals(s)) {
                    isAno = true;
                    break;
                } else {
                    roles.add(s);
                }
            } else {
                throw new ServletException("Empty 'access' property in OAuth Security config file");
            }
        }
        return new Object[] {roles, Boolean.valueOf(isAno)};
    }
}
