package com.retirement.tat.core.business.utils;

import com.retirement.tat.core.data.entity.PermissionEntity;
import com.retirement.tat.core.dto.PermissionDTO;

/**
 * Created with IntelliJ IDEA.
 * User: ANHTAI
 * Date: 2/18/16
 * Time: 9:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class PermissionBeanUtil {
    public static PermissionDTO entity2DTO(PermissionEntity entity) {
        PermissionDTO dto = new PermissionDTO(entity.getPermissionId(),entity.getCode(),entity.getName(),entity.getCreatedDate(),entity.getModifiedDate());
        return dto;
    }
}
