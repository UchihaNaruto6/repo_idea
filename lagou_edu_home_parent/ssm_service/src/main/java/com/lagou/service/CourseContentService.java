package com.lagou.service;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;

import java.util.List;

public interface CourseContentService {
    /**
     * 根据id查询课程内容
     * @param courseId
     * @return
     */
    public List<CourseSection> findSectionAndLessonByCourseId(int courseId);

    /**
     * 根据课程id查询课程信息，回显课程id
     * @param courseId
     * @return
     */
    public Course findCourseByCourseId(int courseId);

    /**
     * 添加章节信息
     * @param section
     */
    public void saveSection(CourseSection section);

    /**
     * 修改章节信息
     * @param section
     */
    public void updateSection(CourseSection section);

    /**
     * 修改章节状态
     * @param id
     * @param status
     */
    public void updateSectionStatus(int id,int status);

    /**
     * 添加课时信息
     * @param lesson
     */
    public void saveLesson(CourseLesson lesson);



}
