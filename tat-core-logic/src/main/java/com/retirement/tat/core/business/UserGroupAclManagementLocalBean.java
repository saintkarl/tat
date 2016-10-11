package com.retirement.tat.core.business;

import com.retirement.tat.core.dto.PermissionDTO;
import com.retirement.tat.core.dto.UserGroupAclDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ANHTAI
 * Date: 2/18/16
 * Time: 9:18 AM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface UserGroupAclManagementLocalBean {
    UserGroupAclDTO addItem(UserGroupAclDTO pojo) throws DuplicateKeyException;

    UserGroupAclDTO updateItem(UserGroupAclDTO pojo) throws ObjectNotFoundException, DuplicateKeyException;

    Map<Long, Long> findByUserGroupId(Long userGroupId);

    void deleteByPermissionIdUserGroupId(Long permissionId, Long userGroupId);

    void deleteByUserGroupId(Long userGroupId);

    List<PermissionDTO> getPermissionByUserId(Long userGroupId);
}
