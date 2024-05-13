package edu.hitwh.homework.service;

import edu.hitwh.homework.pojo.Course;
import edu.hitwh.homework.pojo.PageCourse;

import java.time.LocalDate;
import java.util.List;

public interface CourseService {
    PageCourse page(Integer page, Integer pageSize, String courseName, LocalDate begin, LocalDate end);
    void delete(Integer id);
    void save(Course course);
    Course getById(Integer id);
    void update(Course course);
    List<Course> getAll();
}
