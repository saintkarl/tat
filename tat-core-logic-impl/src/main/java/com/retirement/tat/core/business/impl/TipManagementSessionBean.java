package com.retirement.tat.core.business.impl;

import com.retirement.tat.common.util.DozerSingletonMapper;
import com.retirement.tat.core.business.TipManagementLocalBean;
import com.retirement.tat.core.data.entity.TipEntity;
import com.retirement.tat.core.data.session.TipLocalBean;
import com.retirement.tat.core.dto.TipDTO;

import javax.ejb.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Stateless(name = "TipManagementSessionEJB")
public class TipManagementSessionBean implements TipManagementLocalBean {
    @EJB
    private TipLocalBean tipLocalBean;

    public TipManagementSessionBean() {
    }

    @Override
    public TipDTO saveOrUpdate(TipDTO dto) throws ObjectNotFoundException, DuplicateKeyException {
        TipEntity entity = null;
        if (dto.getTipId() != null && dto.getTipId() > 0) {
            entity = tipLocalBean.findById(dto.getTipId());
            entity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
            tipLocalBean.update(entity);
        } else {
            entity = new TipEntity();
            entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            entity = tipLocalBean.save(entity);
            dto.setTipId(entity.getTipId());
        }
        return dto;
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems, String whereClause) {
        Object[] objs = tipLocalBean.searchByProperties(properties, sortExpression, sortDirection, firstItem, maxPageItems, whereClause);
        List<TipEntity> roleEntries = (List<TipEntity>)objs[1];
        List<TipDTO> roleDTOs = new ArrayList<>();
        for(TipEntity TipEntity : roleEntries){
            roleDTOs.add(DozerSingletonMapper.getInstance().map(TipEntity, TipDTO.class));
        }

        Long totals = Long.valueOf(objs[0].toString());
        Object[] results = new Object[2];
        results[0] = totals;
        results[1] = roleDTOs;

        return results;
    }

    @Override
    public Boolean deleteByIds(String[] checkList) {
        Long[] ids = new Long[checkList.length];
        int index = 0;
        for(String id : checkList){
            ids[index] = Long.valueOf(id);
            index++;
        }
        try{
            tipLocalBean.delete(ids);
        }catch (RemoveException e){
            return false;
        }
        return true;
    }

    @Override
    public TipDTO findById(Long roleId) throws ObjectNotFoundException {
        TipEntity entity = tipLocalBean.findById(roleId);
        return DozerSingletonMapper.getInstance().map(entity, TipDTO.class);
    }

    @Override
    public Boolean checkDuplicatedName(String name) {
        return tipLocalBean.checkDuplicatedName(name);
    }

    @Override
    public TipDTO findByTipName(String name) throws ObjectNotFoundException {
        TipEntity entity = tipLocalBean.findEqualUnique("name", name);
        if (entity == null) {
            throw new ObjectNotFoundException("Not found role " + name);
        }
        return DozerSingletonMapper.getInstance().map(entity, TipDTO.class);
    }
    @Override
    public  List<TipDTO> findAll(){

        List<TipDTO>  roleDTOs = new ArrayList<TipDTO>();
        List<TipEntity> entity = tipLocalBean.findAll();
        for(int i = 0; i < entity.size(); i++){
            roleDTOs.add(DozerSingletonMapper.getInstance().map(entity.get(i), TipDTO.class));

        }
        return roleDTOs;


    }

    @Override
    public Integer deleteItems(String[] checkList,Map<Long,Long> mapPermissions) {
        Integer res = 0;
        if(checkList != null && checkList.length >= 0){
            for(String id : checkList){
                tipLocalBean.deleteById(Long.parseLong(id));
                res++;
            }
        }
        return  res;
    }

    @Override
    public TipDTO addItem(TipDTO pojo, Map<Long, Long> mapPermissions) throws DuplicateKeyException {
        TipEntity entity = DozerSingletonMapper.getInstance().map(pojo, TipEntity.class);
        entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        entity = tipLocalBean.save(entity);
        return DozerSingletonMapper.getInstance().map(entity, TipDTO.class);
    }

    @Override
    public TipDTO updateItem(TipDTO pojo, Map<Long, Long> mapPermissions) throws ObjectNotFoundException, DuplicateKeyException {
        TipEntity dbItem = this.tipLocalBean.findById(pojo.getTipId());
        if(dbItem == null) throw new ObjectNotFoundException("Can not find Tip with ID " + pojo.getTipId());
        dbItem.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        dbItem = tipLocalBean.update(dbItem);
        return DozerSingletonMapper.getInstance().map(dbItem, TipDTO.class);
    }

    @Override
    public TipDTO findByCode(String code) throws ObjectNotFoundException{
        TipEntity entity = this.tipLocalBean.findByCode(code);
        return DozerSingletonMapper.getInstance().map(entity, TipDTO.class);

    }

    @Override
    public TipDTO findByName(String name) throws ObjectNotFoundException{
            TipEntity entity  = this.tipLocalBean.findByName(name);
        return DozerSingletonMapper.getInstance().map(entity, TipDTO.class);
    }

    public List<TipDTO> findByCodeOrName(String code, String name){
        List<TipEntity> entities = this.tipLocalBean.findByCodeOrName(code, name);
        List<TipDTO> dtos = new ArrayList<>();
        for(int i=0; i< entities.size(); i++){
            
            dtos.add(DozerSingletonMapper.getInstance().map(entities.get(i), TipDTO.class));
        }
        return dtos;
    }
}
