package com.retirement.tat.web.util;

import com.retirement.tat.core.dto.CatGroupDTO;
import com.retirement.tat.core.dto.CatGroupTreeDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nkhang
 * Date: 9/8/15
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductGroupUtil {

    public static List<CatGroupTreeDTO> buildCatGroupTreeStructure(List<CatGroupDTO> catGroupDTOs){
        Map<Long, List<CatGroupDTO>> mapIndex = new HashMap<>();
        for(CatGroupDTO dto : catGroupDTOs){
            CatGroupDTO parent = dto.getParent();
            if(parent != null){
                if(mapIndex.get(parent.getCatGroupId()) != null){
                    mapIndex.get(parent.getCatGroupId()).add(dto);
                }else{
                    List<CatGroupDTO> child = new ArrayList<>();
                    child.add(dto);
                    mapIndex.put(parent.getCatGroupId(), child);
                }
            }
        }
        List<CatGroupDTO> dtos = new ArrayList<>();


        List<CatGroupTreeDTO> listResult = new ArrayList<>();
        List<CatGroupDTO> catGroups = new ArrayList<>();
        for(CatGroupDTO dto : catGroupDTOs){
            CatGroupDTO parent = dto.getParent();
            if(parent == null || parent.getCatGroupId() == null){
                int level = 0;
                CatGroupTreeDTO treeDTO = new CatGroupTreeDTO();
                treeDTO.setNode(dto);
                treeDTO.setLevel(level);
                treeDTO.setChildNodes(catGroupTreeStructure(dto, mapIndex,  level+1));
                listResult.add(treeDTO);
            }
        }
        return listResult;
    }



    private static List<CatGroupTreeDTO> catGroupTreeStructure(CatGroupDTO root, Map<Long, List<CatGroupDTO>> mapIndex, int level){
        List<CatGroupDTO> child = mapIndex.get(root.getCatGroupId());
        List<CatGroupTreeDTO> trees = new ArrayList<>();
        if(child != null && child.size() > 0){
            for(CatGroupDTO children : child){
                CatGroupTreeDTO treeDTO = new CatGroupTreeDTO();
                treeDTO.setNode(children);
                treeDTO.setLevel(level);
                if(mapIndex.get(children.getCatGroupId()) != null){
                    treeDTO.setChildNodes(catGroupTreeStructure(children, mapIndex, level+1));
                }
                trees.add(treeDTO);
            }
        }
        return trees;
    }
}
