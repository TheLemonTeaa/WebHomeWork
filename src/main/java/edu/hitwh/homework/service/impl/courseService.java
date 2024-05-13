package edu.hitwh.homework.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.hitwh.homework.mapper.CourseMapper;
import edu.hitwh.homework.pojo.Cls;
import edu.hitwh.homework.pojo.Course;
import edu.hitwh.homework.pojo.PageCls;
import edu.hitwh.homework.pojo.PageCourse;
import edu.hitwh.homework.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class courseService implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public PageCourse page(Integer page, Integer pageSize, String courseName, LocalDate begin, LocalDate end){
        PageHelper.startPage(page,pageSize);
        List<Course> courseList = courseMapper.list(courseName, begin, end);
        Page<Course> p = (Page<Course>) courseList;
        PageCourse pageCourse = new PageCourse(p.getTotal(), p.getResult());
        return pageCourse;
    }
    @Override
    public void delete(Integer id) {
        courseMapper.delete(id);
    }
    @Override
    public void save(Course course) {
        courseMapper.save(course);
    }
    @Override
    public Course getById(Integer id) {
        return courseMapper.getById(id);
    }
    @Override
    public void update(Course course) {
        courseMapper.update(course);
    }
    @Override
    public List<Course> getAll() {
        return courseMapper.getAll();
    }
}
