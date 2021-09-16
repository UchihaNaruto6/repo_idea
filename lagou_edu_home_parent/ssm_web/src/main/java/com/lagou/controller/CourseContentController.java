package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/courseContent")
public class CourseContentController {
    @Autowired
    private CourseContentService courseContentService;

    /**
     * 查询课程内容
     * @param courseId
     * @return
     */
    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(@RequestParam int courseId){
        List<CourseSection> sections = courseContentService.findSectionAndLessonByCourseId(courseId);
        //封装响应数据
        ResponseResult result = new ResponseResult(true,200,"响应成功",sections);
        return result;
    }

    /**
     * 回显章节对应的课程信息
     * @param courseId
     * @return
     */
    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(@RequestParam int courseId){
        Course course = courseContentService.findCourseByCourseId(courseId);
        ResponseResult responseResult = new ResponseResult(true, 200, "响应成功", course);
        return responseResult;
    }

    /**
     * 添加或修改章节信息
     * @param section
     * @return
     */
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection section){
        if(section.getId()==null){
            //表示新增操作
            courseContentService.saveSection(section);
            return new ResponseResult(true,200,"响应成功",null);
        }else {
            //表示修改操作
            courseContentService.updateSection(section);
            return new ResponseResult(true,200,"响应成功",null);
        }
    }

    /**
     * 修改章节状态
     * @param id
     * @param status
     * @return
     */
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(@RequestParam int id,@RequestParam int status){
        courseContentService.updateSectionStatus(id,status);
        //封装结果响应前端
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Status",status);
        return new ResponseResult(true,200,"修改成功",map);
    }

    /**
     * 新增章节信息
     * @param lesson
     * @return
     */
    @RequestMapping("/saveLesson")
    public ResponseResult saveLesson(@RequestBody CourseLesson lesson){
        courseContentService.saveLesson(lesson);
        return new ResponseResult(true,200,"响应成功",null);
    }
}
