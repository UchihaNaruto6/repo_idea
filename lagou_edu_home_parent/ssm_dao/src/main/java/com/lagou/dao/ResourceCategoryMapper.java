package com.lagou.dao;

import com.lagou.domain.Resource;
import com.lagou.domain.ResourceCategory;

import java.util.List;

public interface ResourceCategoryMapper {
    /**
     * 查询所有资源分类信息
     */
    public List<ResourceCategory> findAllResourceCategory();
    /**
     * 添加资源分类
     */
    public void saveResourceCategory(ResourceCategory resourceCategory);
    /**
     * 修改资源分类
     */
    public void updateResourceCategory(ResourceCategory resourceCategory);
    /**
     * 删除资源分类
     */
    public void deleteResourceCategpry(Integer id);
    /**
     * 根据资源分类id查询资源信息
     */
    public ResourceCategory findResourceByResourceCategoryId(Integer category_id);
}
