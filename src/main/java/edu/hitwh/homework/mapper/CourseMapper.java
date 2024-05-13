package edu.hitwh.homework.mapper;

import edu.hitwh.homework.pojo.Course;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CourseMapper {
    List<Course> list(String courseName, LocalDate begin, LocalDate end);
    void delete(Integer id);
    void save(Course course);
    Course getById(Integer id);
    void update(Course course);
    List<Course> getAll();
}
