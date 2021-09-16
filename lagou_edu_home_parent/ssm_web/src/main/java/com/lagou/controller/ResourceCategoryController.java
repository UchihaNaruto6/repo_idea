package com.lagou.controller;


import com.lagou.domain.ResourceCategory;
import com.lagou.domain.ResponseResult;
import com.lagou.service.ResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ResourceCategory")
public class ResourceCategoryController {
    @Autowired
    private ResourceCategoryService resourceCategoryService;

    /**
     * 查询所有资源分类信息
     * @return
     */
    @RequestMapping("/findAllResourceCategory")
    public ResponseResult findAllResourceCategory(){
        //调用service层
        List<ResourceCategory> allResourceCategory = resourceCategoryService.findAllResourceCategory();
        return new ResponseResult(true,200,"响应成功",allResourceCategory);
    }
    /**
     * 添加或者修改资源分类信息
     */
    @RequestMapping("/saveOrUpdateResourceCategory")
    public ResponseResult saveOrUpdateResourceCategory(@RequestBody ResourceCategory resourceCategory){
        if(resourceCategory.getId()==null){
            //表示新增操作
            resourceCategoryService.saveResourceCategory(resourceCategory);
            return new ResponseResult(true,200,"响应成功",null);
        }else {
            //表示修改操作
            System.out.println(resourceCategory );
            resourceCategoryService.updateResourceCategory(resourceCategory);
            return new ResponseResult(true,200,"响应成功",null);
        }
    }
    /**
     * 删除资源分类信息
     */
    @RequestMapping("/deleteResourceCategory")
    public ResponseResult deleteResourceCategory(@RequestParam int id){
        resourceCategoryService.deleteResourceCategpry(id);
        return new ResponseResult(true,200,"删除成功",null);
    }
}
