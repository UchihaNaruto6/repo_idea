package com.lagou.controller;


import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVo;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {
    @Autowired
    private PromotionAdService promotionAdService;
    /*
    分页查询所有广告信息
    */
    @RequestMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllAdByPage(PromotionAdVo adVo) {
        PageInfo allAdByPage = promotionAdService.findAllAdByPage(adVo);
        ResponseResult responseResult = new ResponseResult(true, 200, "响应成功", allAdByPage);
        return responseResult;
    }

    /**
     * 图片上传
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/PromotionAdUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        //1.判断接受到的上传文件是否为空
        if(file.isEmpty()){
            //直接抛出异常
            throw new RuntimeException();
        }
        //2.获取项目的部署路径,用于保存到路径下的upload文件中
        /*//D:\Program Files (x86)\apache-tomcat-8.5.55-windows-x64\apache-tomcat-8.5.55\webapps\ssm_web\*/
        String realPath = request.getServletContext().getRealPath("/");
        System.out.println(realPath);
        /*//D:\Program Files (x86)\apache-tomcat-8.5.55-windows-x64\apache-tomcat-8.5.55\webapps\*/
        String substring = realPath.substring(0, realPath.indexOf("ssm_web"));
        System.out.println(substring);
        //3.获取原文件名
        //lagou.jqp
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);

        //4.生成新文件名
        //时间戳123231321.jpg
        String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println(newFileName);

        //5.文件上传
        String uploadPath =substring+"upload\\";
        File filePath = new File(uploadPath, newFileName);
        System.out.println(filePath);
        //如果目录不存在，就创建目录
        if(!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();
            System.out.println("目录创建成功！"+filePath);
        }
        //图片上传了
        file.transferTo(filePath);

        //6.将文件名和文件路径返回，进行响应
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName",newFileName);
        map.put("filePath","http://localhost:8080/upload/"+newFileName);

        ResponseResult responseResult = new ResponseResult(true, 200, "上传成功", map);
        return responseResult;
    }


    /**
     * 广告上下线
     */
    @RequestMapping("/updatePromotionAdStatus")
    public  ResponseResult updatePromotionAdStatus(@RequestParam int id, @RequestParam int status){
        promotionAdService.updatePromotionAdStatus(id,status);
        Map<String, Integer> map = new HashMap<>();
        map.put("status", status);
        ResponseResult result = new ResponseResult(true, 200, "响应成功", map);
        return result;
    }

    /**
     * 新增或更新广告
     * @param promotionAd
     * @return
     */
    @RequestMapping("/saveOrUpdatePromotionAd")
    public ResponseResult saveOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd) {
        if(promotionAd.getId()==null){
            //表示新增
            Date date = new Date();
            promotionAd.setCreateTime(date);
            promotionAd.setUpdateTime(date);
            promotionAdService.savePromotionAd(promotionAd);
            ResponseResult result = new ResponseResult(true, 200, "响应成功", null);
            return result;
        }else {
            Date date = new Date();
            promotionAd.setUpdateTime(date);
            promotionAdService.updatePromotionAd(promotionAd);
            ResponseResult result = new ResponseResult(true, 200, "响应成功", null);
            return result;
        }
    }
    /**
     * 根据id回显 广告数据
     * */
    @RequestMapping("/findPromotionAdById")
    public ResponseResult findPromotionAdById(@RequestParam int id){
            PromotionAd promotionAd = promotionAdService.findPromotionAdById(id);
            ResponseResult result = new ResponseResult(true,200,"响应成功",promotionAd);
            return result;
    }
}
