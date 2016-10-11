package com.retirement.tat.core.data.session;

import com.retirement.tat.core.data.entity.UserDemographicEntity;

import javax.ejb.Local;

@Local
public interface UserDemographicLocalBean extends GenericSessionBean<UserDemographicEntity, Long> {
    UserDemographicEntity findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
