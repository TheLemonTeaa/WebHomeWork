package edu.hitwh.homework.controller;

import edu.hitwh.homework.pojo.Cls;
import edu.hitwh.homework.pojo.Course;
import edu.hitwh.homework.pojo.PageCourse;
import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1")Integer page,
                       @RequestParam(defaultValue = "10")Integer pageSize,
                       String courseName,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end) {
        PageCourse pageCourse = courseService.page(page, pageSize, courseName, begin, end);
        return Result.success(pageCourse);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        courseService.delete(id);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Course course) {
        courseService.save(course);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Course course = courseService.getById(id);
        return Result.success(course);
    }

    @PutMapping
    public Result update(@RequestBody Course course) {
        courseService.update(course);
        return Result.success();
    }

    @GetMapping("/all")
    public Result getAll() {
        List<Course> course = courseService.getAll();
        return Result.success(course);
    }

}
