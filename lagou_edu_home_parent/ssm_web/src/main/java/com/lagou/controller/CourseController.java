package com.lagou.controller;


import com.lagou.domain.Course;
import com.lagou.domain.CourseVo;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController //@Controller + @Responsebody 组合注解
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    /**
     * 多条件查询课程列表信息
     * @param courseVo
     * @return
     */
    @RequestMapping("/findCourseByCondition")
    public ResponseResult findCourseByCondition(@RequestBody CourseVo courseVo){
        System.out.println(courseVo);
        //调用Service
        List<Course> courses = courseService.findCourseByCondition(courseVo);
        ResponseResult responseResult = new ResponseResult(true, 200, "响应成功", courses);
        return responseResult;
    }

    /**
     * 图片上传
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws IOException {
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
     * 添加或修改课程信息
     * @param courseVo
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {
        if(courseVo.getId()==null){
            //表示新增操作
            //调用Service
            courseService.saveCourseOrTeacher(courseVo);
            ResponseResult responseResult = new ResponseResult(true,200,"新增成功",null);
            return responseResult;
        }else {
            //表示更新操作
            courseService.updateCourseOrTeacher(courseVo);
            ResponseResult responseResult = new ResponseResult(true,200,"更新成功",null);
            return responseResult;
        }
    }

    /**
     * 根据id回显课程信息
     * @param id
     * @return
     */
    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(@RequestParam int id) {
        try {
            CourseVo courseVo = courseService.findCourseById(id);
            ResponseResult result = new ResponseResult(true,200,"响应成功",courseVo);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新课程状态
     * @param id
     * @param status
     * @return
     */
    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(@RequestParam int id,@RequestParam int status){
        courseService.updateCourseStatus(id,status);
        //保存修改后的状态，并返回
        Map<String, Integer> map = new HashMap<>();
        map.put("status",status);
        ResponseResult responseResult=new ResponseResult(true,200,"响应成功",map);
        return responseResult;
    }
}

