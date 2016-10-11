package com.retirement.tat.core.business.utils;

import com.retirement.tat.core.data.entity.UserGroupEntity;
import com.retirement.tat.core.dto.UserGroupDTO;

public class UserGroupBeanUtil {

    public static UserGroupDTO entity2DTO(UserGroupEntity entity) {
        UserGroupDTO dto = new UserGroupDTO(entity.getUserGroupId(), entity.getName(), entity.getCode(),
                entity.getCreatedDate(), entity.getModifiedDate());
        return dto;
    }
    public static UserGroupEntity dto2Entity(UserGroupDTO dto){
        UserGroupEntity entity = new UserGroupEntity();
        entity.setUserGroupId(dto.getUserGroupId());
        entity.setCode(dto.getCode());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setName(dto.getName());
        entity.setModifiedDate(dto.getModifiedDate());
        return entity;
    }
}
