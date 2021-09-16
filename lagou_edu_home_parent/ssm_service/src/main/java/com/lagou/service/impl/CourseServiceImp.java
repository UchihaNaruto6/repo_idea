package com.lagou.service.impl;

import com.lagou.dao.CourseMapper;
import com.lagou.domain.Course;
import com.lagou.domain.CourseVo;
import com.lagou.domain.Teacher;
import com.lagou.service.CourseService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImp implements CourseService {

    @Autowired
    private CourseMapper courseMapper;


    @Override
    public List<Course> findCourseByCondition(CourseVo courseVo) {
        List<Course> courses = courseMapper.findCourseByCondition(courseVo);
        return courses;
    }

    @Override
    public void saveCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {
        //封装课程信息
        Course course = new Course();
        //使用BeanUtils工具类将courseVo中的属性值封装到course中
        BeanUtils.copyProperties(course,courseVo);
        //补全课程信息（创建时间和更新时间）
        Date date=new Date();
        course.setCreateTime(date);
        course.setUpdateTime(date);
        //调用dao层保存方法
        courseMapper.saveCourse(course);
        //获取新插入课程的id
        int id = course.getId();
        //封装讲师信息
        Teacher teacher=new Teacher();
        BeanUtils.copyProperties(teacher,courseVo);
        //补全讲师信息
        teacher.setCreateTime(date);
        teacher.setUpdateTime(date);
        teacher.setIsDel(0);
        teacher.setCourseId(id);
        //保存讲师信息
        courseMapper.saveTeacher(teacher);
    }

    @Override
    public CourseVo findCourseById(Integer id) {
        return courseMapper.findCourseById(id);
    }

    @Override
    public void updateCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {
        //封装课程信息
        Course course=new Course();
        BeanUtils.copyProperties(course,courseVo);
        Date date=new Date();
        course.setUpdateTime(date);
        //修改课程信息
        courseMapper.updateCourse(course);
        //封装讲师信息
        Teacher teacher=new Teacher();
        BeanUtils.copyProperties(teacher,courseVo);
        teacher.setUpdateTime(date);
        teacher.setCourseId(course.getId());
        //更新讲师信息
        courseMapper.updateTeacher(teacher);
    }

    @Override
    public void updateCourseStatus(int id, int status) {
        //封装数据
        Course course=new Course();
        course.setStatus(status);
        course.setId(id);
        course.setUpdateTime(new Date());
        //调用dao
        courseMapper.updateCourseStatus(course);
    }
}
