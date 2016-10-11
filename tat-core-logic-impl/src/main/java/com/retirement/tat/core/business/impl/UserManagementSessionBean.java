package com.retirement.tat.core.business.impl;

import com.retirement.tat.common.Constants;
import com.retirement.tat.common.security.DesEncryptionUtils;
import com.retirement.tat.common.util.DozerSingletonMapper;
import com.retirement.tat.core.business.*;
import com.retirement.tat.core.business.utils.UserBeanUtil;
import com.retirement.tat.core.business.utils.UserGroupBeanUtil;
import com.retirement.tat.core.data.entity.*;
import com.retirement.tat.core.data.session.*;
import com.retirement.tat.core.dto.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * E-Retailer System Platform - Copyright (c) by Ban Vien Co., Ltd. All rights reserved.
 * User: viennh
 * Date: 11/30/15
 * Time: 3:46 PM
 */
@Stateless(name = "UserManagementSessionEJB")
public class UserManagementSessionBean implements UserManagementLocalBean {
    private Log log = LogFactory.getLog(UserManagementSessionBean.class);

    @EJB
    private UsersLocalBean usersLocalBean;

    @EJB
    private UserLineManagerLocalBean userLineManagerLocalBean;

    @EJB
    private UserRoleManagementLocalBean userRoleManagementLocalBean;

    @EJB
    private UserOutletManagementLocalBean userOutletManagementLocalBean;

    @EJB
    private UserOutletLocalBean userOutletLocalBean;

    @EJB
    private UserDemoGraphicManagementLocalBean userDemoGraphicManagementLocalBean;

    @EJB
    private UserDemographicLocalBean userDemographicLocalBean;

    @EJB
    private UserLineManagerManagementLocalBean userLineManagerManagementLocalBean;

    @EJB
    private UserGroupLocalBean userGroupLocalBean;

    @EJB
    private RoleLocalBean roleLocalBean;

    @EJB
    private PermissionLocalBean permissionLocalBean;

    public UserManagementSessionBean() {
    }

    @Override
    public UserDTO addItem(UserDTO pojo) throws DuplicateKeyException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        UsersEntity entity = DozerSingletonMapper.getInstance().map(pojo, UsersEntity.class);
        entity.setPassword(DesEncryptionUtils.getInstance().encrypt(pojo.getPassword()));
        entity.setCreatedDate(now);
        entity = usersLocalBean.save(entity);

        UserDemographicEntity userDemographicEntity = new UserDemographicEntity();
        if (pojo.getUserDemographicDTO() != null) {
            userDemographicEntity.setBirthday(pojo.getUserDemographicDTO().getBirthday());
            userDemographicEntity.setPlaceOfBirth(pojo.getUserDemographicDTO().getPlaceOfBirth());
            userDemographicEntity.setSex(pojo.getUserDemographicDTO().getSex());
            userDemographicEntity.setCity(new CityEntity(pojo.getUserDemographicDTO().getCityOfBirth().getCityId()));
            userDemographicEntity.setUser(entity);
            userDemographicEntity.setCreatedDate(now);
            userDemographicLocalBean.update(userDemographicEntity);
        }
        return DozerSingletonMapper.getInstance().map(entity, UserDTO.class);
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] objs = usersLocalBean.searchByProperties(properties, sortExpression, sortDirection, firstItem, maxPageItems);
        List<UsersEntity> outletEntities = (List<UsersEntity>) objs[1];
        List<UserDTO> outletDTOs = new ArrayList<>();
        for (UsersEntity outletEntity : outletEntities) {
            outletDTOs.add(DozerSingletonMapper.getInstance().map(outletEntity, UserDTO.class));

        }

        Long totals = Long.valueOf(objs[0].toString());
        Object[] results = new Object[2];
        results[0] = totals;
        results[1] = outletDTOs;

        return results;
    }

    @Override
    public void updateUserLine(UserDTO pojo) throws DuplicateKeyException, ObjectNotFoundException {
        if (!userLineManagerLocalBean.checkDuplicate(pojo.getUserId(), pojo.getUserLineManagerDTO().getLineManager().getUserId())) {
            userLineManagerLocalBean.updateStatus(pojo.getUserId(), null, Constants.USER_LINE_INACTIVE);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            UserLineManagerEntity userLineManagerEntity = new UserLineManagerEntity();
            userLineManagerEntity.setLineManager(new UsersEntity(pojo.getUserLineManagerDTO().getLineManager().getUserId()));
            userLineManagerEntity.setStatus(Constants.USER_LINE_ACTIVE);
            userLineManagerEntity.setUser(new UsersEntity(pojo.getUserId()));
            userLineManagerEntity.setCreatedDate(now);
            this.userLineManagerLocalBean.save(userLineManagerEntity);
        } else {
            userLineManagerLocalBean.updateStatus(pojo.getUserId(), null, Constants.USER_LINE_INACTIVE);
            userLineManagerLocalBean.updateStatus(pojo.getUserId(), pojo.getUserLineManagerDTO().getLineManager().getUserId(), Constants.USER_LINE_ACTIVE);
        }
    }

    @Override
    public void updateUserOutlet(Long userId, Map<Long, Long> currentMaps) throws DuplicateKeyException {
        List<UserOutletEntity> userOutletEntitys = this.userOutletLocalBean.findByUserId(userId);
        Map<Long, Long> oldMaps2 = new HashMap<>();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (userOutletEntitys != null && userOutletEntitys.size() > 0) {
            for (UserOutletEntity userOutletEntity : userOutletEntitys) {
                oldMaps2.put(userOutletEntity.getOutlet().getOutletId(), userOutletEntity.getUser().getUserId());
            }
        }
        for (Long key : currentMaps.keySet()) {
            if (oldMaps2.get(key) == null && currentMaps.get(key) != null) {
                UserOutletEntity userOutletEntity = new UserOutletEntity();
                userOutletEntity.setOutlet(new OutletEntity(key));
                userOutletEntity.setUser(new UsersEntity(userId));
                userOutletEntity.setCreatedDate(now);
                this.userOutletLocalBean.save(userOutletEntity);
            }
        }
        for (Long key : oldMaps2.keySet()) {
            if (currentMaps.get(key) == null) {
                this.userOutletLocalBean.deleteByOutletUserId(key, userId);
            }
        }
    }

    @Override
    public void updateUserRoles(Long userId, Map<Long, Long> currentMaps) throws DuplicateKeyException {
        Map<Long, Long> oldMaps = this.userRoleManagementLocalBean.findByUserId(userId);
        for (Long key : currentMaps.keySet()) {
            if (oldMaps.get(key) == null && currentMaps.get(key) != null) {
                UserRoleDTO roleACLDTO = new UserRoleDTO();
                RoleDTO role = new RoleDTO();
                role.setRoleId(key);
                UserDTO user = new UserDTO();
                user.setUserId(userId);
                roleACLDTO.setUser(user);
                roleACLDTO.setRole(role);
                roleACLDTO.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                roleACLDTO.setModifiedDate(new Timestamp(System.currentTimeMillis()));
                this.userRoleManagementLocalBean.addItem(roleACLDTO);
            }
        }
        for (Long key : oldMaps.keySet()) {
            if (currentMaps.get(key) == null) {
                this.userRoleManagementLocalBean.deleteByRoleIdUserId(key, userId);
            }
        }
    }

    @Override
    public UserDTO updateItem(UserDTO pojo) throws ObjectNotFoundException, DuplicateKeyException {
        UsersEntity dbItem = this.usersLocalBean.findById(pojo.getUserId());
        if (dbItem == null) throw new ObjectNotFoundException("Can not find User with ID " + pojo.getUserId());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (!(pojo.getPassword() != null && StringUtils.isNotBlank(pojo.getPassword()))) {
            pojo.setPassword(dbItem.getPassword());
        } else {
            pojo.setPassword(DesEncryptionUtils.getInstance().encrypt(pojo.getPassword()));
        }
        dbItem.setUserName(pojo.getUserName());
        dbItem.setCode(pojo.getCode());
        dbItem.setEmail(pojo.getEmail());
        dbItem.setFirstName(pojo.getFirstName());
        dbItem.setLastName(pojo.getLastName());
        dbItem.setPhoneNumber(pojo.getPhoneNumber());
        dbItem.setUserGroup(new UserGroupEntity(pojo.getUserGroup().getUserGroupId()));
        dbItem.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        dbItem = usersLocalBean.update(dbItem);

        if (pojo.getUserDemographicDTO() != null) {
            UserDemographicEntity userDemographicEntity = new UserDemographicEntity();
            if (pojo.getUserDemographicDTO().getUserDemographicId() != null) {
                userDemographicEntity = this.userDemographicLocalBean.findById(pojo.getUserDemographicDTO().getUserDemographicId());
            }
            userDemographicEntity.setBirthday(pojo.getUserDemographicDTO().getBirthday());
            userDemographicEntity.setPlaceOfBirth(pojo.getUserDemographicDTO().getPlaceOfBirth());
            userDemographicEntity.setSex(pojo.getUserDemographicDTO().getSex());
            userDemographicEntity.setCity(new CityEntity(pojo.getUserDemographicDTO().getCityOfBirth().getCityId()));
            userDemographicEntity.setUser(new UsersEntity(pojo.getUserId()));
            if (pojo.getUserDemographicDTO().getUserDemographicId() != null) {
                userDemographicEntity.setModifiedDate(now);
                this.userDemographicLocalBean.update(userDemographicEntity);
            } else {
                userDemographicEntity.setCreatedDate(now);
                this.userDemographicLocalBean.save(userDemographicEntity);
            }
        }
        return DozerSingletonMapper.getInstance().map(dbItem, UserDTO.class);
    }

    @Override
    public UserDTO findById(Long userId) throws ObjectNotFoundException {
        UsersEntity entity = usersLocalBean.findById(userId);
        UserDTO userDTO = UserBeanUtil.entity2DTO(entity);
        return userDTO;
    }

    @Override
    public Object[] search(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems, String whereClause) {
        Object[] res = usersLocalBean.searchByProperties(properties, sortExpression, sortDirection, firstItem, maxPageItems, whereClause);
        List<UserDTO> dtos = new ArrayList<UserDTO>();
        for (UsersEntity entity : (List<UsersEntity>) res[1]) {
            dtos.add(UserBeanUtil.entity2DTO(entity));
        }
        res[1] = dtos;
        Long totals = Long.valueOf(res[0].toString());
        res[0] = totals;
        return res;
    }

    @Override
    public void deleteItems(String[] checkList) throws RemoveException {
        Integer res = 0;
        if (checkList != null && checkList.length >= 0) {
            Long[] ids = new Long[checkList.length];
            for (int i = 0; i < checkList.length; i++) {
                ids[i] = Long.valueOf(checkList[i]);
            }
            usersLocalBean.delete(ids);
        }
    }

    @Override
    public UserDTO findByUserName(String username) throws ObjectNotFoundException {
        UsersEntity entity = usersLocalBean.findByUserName(username);
        UserDTO userDTO = UserBeanUtil.entity2DTO(entity);
        if (entity.getUserGroup() != null) {
            userDTO.setUserGroup(UserGroupBeanUtil.entity2DTO(entity.getUserGroup()));
        }
        return userDTO;
    }


    @Override
    public List<UserDTO> findByUsernameCodeOrEmail(String userName, String code, String email, Long userId) {
        List<UserDTO> result = new ArrayList<>();
        List<UsersEntity> usersEntities = this.usersLocalBean.findByUsernameCodeOrEmail(userName, code, email, userId);
        for (UsersEntity entity : usersEntities) {
            result.add(UserBeanUtil.entity2DTO(entity));
        }

        return result;
    }

    @Override
    public UserDTO findByCode(String code) {
        return DozerSingletonMapper.getInstance().map(usersLocalBean.findByCode(code), UserDTO.class);
    }

    @Override
    public List<UserDTO> importOutletUser(List<ImportOutletUserDTO> listImport) {
        List<UserDTO> listAfterImport = new ArrayList<>();
        for (ImportOutletUserDTO importDTO : listImport) {
            UserDTO userDTO = importDTO.getUser();
            UsersEntity usersEntity = DozerSingletonMapper.getInstance().map(userDTO, UsersEntity.class);
            try {
                usersEntity = usersLocalBean.save(usersEntity);
                listAfterImport.add(DozerSingletonMapper.getInstance().map(usersEntity, UserDTO.class));
            } catch (DuplicateKeyException e) {
                e.printStackTrace();
            }
        }
        return listAfterImport;
    }

    @Override
    public List<UserLineManagerDTO> importManager(List<UserLineManagerDTO> listManager) {
        List<UserLineManagerDTO> listAfterImport = new ArrayList<>();
        if (listManager.size() > 0) {
            for (UserLineManagerDTO dto : listManager) {
                UserLineManagerEntity entity = DozerSingletonMapper.getInstance().map(dto, UserLineManagerEntity.class);
                try {
                    entity = userLineManagerLocalBean.save(entity);
                    listAfterImport.add(DozerSingletonMapper.getInstance().map(entity, UserLineManagerDTO.class));
                } catch (DuplicateKeyException e) {
                    e.printStackTrace();
                }
            }
        }
        return listAfterImport;
    }

    @Override
    public Boolean checkDuplicatedUniqueData(UserDTO userDTO) {
        return usersLocalBean.checkDuplicatedUniqueData(DozerSingletonMapper.getInstance().map(userDTO, UsersEntity.class));
    }

    @Override
    public List<UserDTO> searchByUserGroup(String code) {
        List<UserDTO> userDTOs = new ArrayList<>();
        List<UsersEntity> usersEntities = usersLocalBean.searchByUserGroup(code);
        for (UsersEntity entity : usersEntities) {
            userDTOs.add(DozerSingletonMapper.getInstance().map(entity, UserDTO.class));
        }
        return userDTOs;
    }

    @Override
    public List<UserDTO> searchByManagerLine(String managerCode) {
        List<UsersEntity> usersEntities = usersLocalBean.searchByMangerLine(managerCode);
        List<UserDTO> userDTOs = new ArrayList<>();
        for (UsersEntity entity : usersEntities) {
            userDTOs.add(DozerSingletonMapper.getInstance().map(entity, UserDTO.class));
        }
        return userDTOs;
    }

    @Override
    public void insertOrUpdateByList(List<UserImportDTO> userImportDTOs) throws DuplicateKeyException {
        List<UsersEntity> user2Insert = new ArrayList<>();
        for (UserImportDTO dto : userImportDTOs) {
            if (dto.getValid()) {
                Timestamp now = new Timestamp(System.currentTimeMillis());
                UsersEntity entity = UserBeanUtil.dto2Entity(dto.getUser());
                if (dto.getUser().getUserId() != null) {
                    entity.setModifiedDate(now);
                    usersLocalBean.update(entity);
                } else {
                    entity.setCreatedDate(now);
                    user2Insert.add(entity);
                }

            }
        }
        if(user2Insert.size() > 0){
            try {
                usersLocalBean.doBulkInsert(user2Insert);
            } catch (Exception e){
                  throw new DuplicateKeyException("Duplicate key exception " + e.getMessage());
            }
        }
    }

    @Override
    public List<UserDTO> findByRole(String roleCode) {
        List<UsersEntity> entities = usersLocalBean.findByRole(roleCode);
        List<UserDTO> dtoList = new ArrayList<>();
        for (UsersEntity entity : entities) {
            dtoList.add(UserBeanUtil.entity2DTO(entity));
        }
        return dtoList;
    }

    @Override
    public Map<String, UserDTO> findByUserNames(List<String> userNames) {
        Map<String, UserDTO> userNameMap = new HashMap<>();
        List<UsersEntity> usersEntities = usersLocalBean.findByUserNames(userNames);
        for (UsersEntity entity : usersEntities) {
            userNameMap.put(entity.getUserName(), UserBeanUtil.entity2DTO(entity));
        }
        return userNameMap;
    }

    @Override
    public Map<String, UserDTO> findByEmails(List<String> emails) {
        Map<String, UserDTO> emailMap = new HashMap<>();
        List<UsersEntity> usersEntities = usersLocalBean.findByEmails(emails);
        for (UsersEntity entity : usersEntities) {
            emailMap.put(entity.getEmail(), UserBeanUtil.entity2DTO(entity));
        }
        return emailMap;
    }

    @Override
    public UserDTO findByEmail(String email) throws ObjectNotFoundException {
        UsersEntity entity = usersLocalBean.findByEmail(email);
        return UserBeanUtil.entity2DTO(entity);
    }

    @Override
    public void validateData(List<UserImportDTO> userImportDTOs, List<String> emails, List<String> userNames) {

        List<UsersEntity> userNameEntities = usersLocalBean.findByUserNames(userNames);
        List<UsersEntity> userEmailEntities = usersLocalBean.findByEmails(emails);
        List<UserGroupEntity> userGroupEntities = userGroupLocalBean.findAll();

        Map<String, UserGroupEntity> userGroupMap = new HashMap<>();
        for (UserGroupEntity entity : userGroupEntities) {
            userGroupMap.put(entity.getCode().toUpperCase(), entity);
        }

        Map<String, UsersEntity> userNameMap = new HashMap<>();
        for (UsersEntity entity : userNameEntities) {
            userNameMap.put(entity.getUserName().toUpperCase(), entity);
        }

        Map<String, UsersEntity> userEmailMap = new HashMap<>();
        for (UsersEntity entity : userEmailEntities) {
            userEmailMap.put(entity.getEmail().toUpperCase(), entity);
        }
        List<UserImportDTO> userImportDTOList = new ArrayList<>(userImportDTOs.size());
        for (UserImportDTO userImport : userImportDTOs) {
            if (!userImport.getValid()) {
                continue;
            }
            UserDTO dto = userImport.getUser();
            Boolean valid = true;
            String errorCode = "";
            if (userGroupMap.get(dto.getUserGroup().getCode().toUpperCase()) == null) {
                valid = false;
                errorCode = "user.group.do.not.exist";
            } else if (userNameMap.get(dto.getUserName().toUpperCase()) != null) {
                UsersEntity userUserName = userNameMap.get(dto.getUserName().toUpperCase());
                UsersEntity userEmail = userEmailMap.get(dto.getEmail().toUpperCase());
                if (userEmail != null && !(userEmail.getEmail().toUpperCase()).equals(userUserName.getEmail().toUpperCase())) {
                    valid = false;
                    errorCode = "errors.email.duplicated";
                } else {
                    updateValue(dto, userUserName);
                    dto = UserBeanUtil.entity2DTO(userUserName);
                }

            }
            if (valid && userEmailMap.get(dto.getEmail().toUpperCase()) != null) {
                UsersEntity userUserName = userNameMap.get(dto.getUserName().toUpperCase());
                UsersEntity userEmail = userEmailMap.get(dto.getEmail().toUpperCase());
                if (userUserName != null && !(userEmail.getUserName().toUpperCase()).equals(userUserName.getUserName().toUpperCase())) {
                    valid = false;
                    errorCode = "errors.username.duplicated";
                } else {
                    updateValue(dto, userUserName);
                    dto = UserBeanUtil.entity2DTO(userUserName);
                }
            }
            if(valid){
                UserGroupEntity userGroupEntity = userGroupMap.get(dto.getUserGroup().getCode());
                dto.getUserGroup().setUserGroupId(userGroupEntity.getUserGroupId());
            }
            userImport.setValid(valid);
            userImport.setErrorCode(errorCode);
            userImport.setUser(dto);
            userImportDTOList.add(userImport);
        }
    }

    @Override
    public UserDTO findByUserNameAndFetchPermissions(String username) throws ObjectNotFoundException{
        UsersEntity entity = usersLocalBean.findByUserName(username);
        UserDTO userDTO = UserBeanUtil.entity2DTO(entity);
        if (entity.getUserGroup() != null) {
            userDTO.setUserGroup(UserGroupBeanUtil.entity2DTO(entity.getUserGroup()));
        }
        List<RoleEntity> roleEntities = roleLocalBean.findRoleOfUser(entity.getUserId());
        List<RoleDTO> roles = new ArrayList<RoleDTO>();
        List<Long> roleIds = new ArrayList<Long>();
        for (RoleEntity roleEntity : roleEntities) {
            roles.add(DozerSingletonMapper.getInstance().map(roleEntity, RoleDTO.class));
            roleIds.add(roleEntity.getRoleId());
        }
        userDTO.setRoles(roles);

        List<PermissionEntity> permissionEntities = permissionLocalBean.findPermissionOfUserId(entity.getUserId(), entity.getUserGroup().getUserGroupId(), roleIds);
        List<PermissionDTO> permissionDTOs = new ArrayList<PermissionDTO>();
        for (PermissionEntity permissionEntity : permissionEntities) {
            permissionDTOs.add(DozerSingletonMapper.getInstance().map(permissionEntity, PermissionDTO.class));
        }
        userDTO.setPermissions(permissionDTOs);
        return userDTO;
    }

    private void updateValue(UserDTO source, UsersEntity destinate) {
        destinate.setUserName(source.getUserName());
        destinate.setEmail(source.getEmail());
        destinate.setPassword(source.getPassword());

        if (StringUtils.isNotBlank(source.getFirstName())) {
            destinate.setFirstName(source.getFirstName());
        }
        if (StringUtils.isNotBlank(source.getLastName())) {
            destinate.setLastName(source.getLastName());
        }
        if (StringUtils.isNotBlank(source.getCode())) {
            destinate.setCode(source.getCode());
        }

    }

    @Override
    public List<UserDTO> findByUserNameQuery(String query) {
        List<UsersEntity> usersEntities = usersLocalBean.findByUserNameQuery(query);
        List<UserDTO> userDTOs = new ArrayList<>(usersEntities.size());
        for(UsersEntity entity : usersEntities){
              userDTOs.add(UserBeanUtil.entity2DTO(entity));
        }
        return userDTOs;
    }
}
