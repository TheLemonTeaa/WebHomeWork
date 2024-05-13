package edu.hitwh.homework.controller;

import edu.hitwh.homework.mapper.StuMapper;
import edu.hitwh.homework.pojo.PageCls;
import edu.hitwh.homework.pojo.PageStu;
import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.pojo.Stu;
import edu.hitwh.homework.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stu")
public class StuController {
    @Autowired
    private StuService stuService;
    @Autowired
    private StuMapper stuMapper;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1")Integer page,
                       @RequestParam(defaultValue = "10")Integer pageSize,
                       String studentName, String studentID, Short degree, String className){
        PageStu pageStu = stuService.page(page, pageSize, studentName, studentID, degree, className);
        return Result.success(pageStu);
    }
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        stuService.delete(ids);
        return Result.success();
    }
    @PostMapping
    public Result save(@RequestBody Stu stu){
        stuService.save(stu);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Stu stu = stuService.getById(id);
        return Result.success(stu);
    }
    @PutMapping
    public Result update(@RequestBody Stu stu){
        stuService.update(stu);
        return Result.success();
    }
    @GetMapping("/gender")
    public Result getAll(Short gender){
        List<Stu> stu = stuService.getAll(gender);
        return Result.success(stu);
    }
}
