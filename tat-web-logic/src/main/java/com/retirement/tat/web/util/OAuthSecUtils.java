package com.retirement.tat.web.util;

import com.retirement.tat.common.Constants;
import com.retirement.tat.core.business.UserManagementLocalBean;
import com.retirement.tat.core.dto.UserDTO;
import com.retirement.tat.core.dto.mobile.UserPrincipalDTO;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Hieu Le on 6/28/2016.
 */
public class OAuthSecUtils {
    private static final Logger logger = Logger.getLogger(OAuthSecUtils.class);
    private static ThreadLocal<UserPrincipalDTO> principal = new ThreadLocal<>();

    public static List<String> buildUserAuthorities(UserDTO userDTO) {
        Set<String> authorities = new HashSet<>();
        authorities.add(Constants.LOGON);

        if (userDTO.getUserGroup() != null) {
            authorities.add(userDTO.getUserGroup().getCode());
        }
        // TODO get more authorities if needed
        return new ArrayList<>(authorities);
    }

    public static UserPrincipalDTO buildUserPrincipal(Long userId, UserManagementLocalBean userManagementLocalBean) {
        UserPrincipalDTO res = null;
        try {
            final UserDTO userDTO = userManagementLocalBean.findById(userId);
            res = new UserPrincipalDTO(userDTO.getUserId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                    userDTO.getPhoneNumber(), userDTO.getCode());
            if (userDTO.getUserGroup() != null) {
                res.setGroup(userDTO.getUserGroup().getCode());
            }
            res.setAuthorities(buildUserAuthorities(userDTO));

        } catch (Exception e) {
            logger.warn("Could NOT authenticate for user " + userId, e);
        }

        return res;
    }

    public static UserPrincipalDTO getPrincipal() {
        return principal.get();
    }

    public static void setPrincipal(UserPrincipalDTO principal) {
        OAuthSecUtils.principal.set(principal);
    }
}
