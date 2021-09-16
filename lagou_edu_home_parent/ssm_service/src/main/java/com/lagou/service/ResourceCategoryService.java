package com.lagou.service;

import com.lagou.domain.Resource;
import com.lagou.domain.ResourceCategory;
import com.lagou.domain.ResourceVo;

import java.util.List;

public interface ResourceCategoryService {
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
}
