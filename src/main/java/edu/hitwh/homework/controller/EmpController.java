package edu.hitwh.homework.controller;

import edu.hitwh.homework.pojo.Emp;
import edu.hitwh.homework.pojo.PageEmp;
import edu.hitwh.homework.pojo.Result;

import edu.hitwh.homework.service.EmpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1")Integer page,
                       @RequestParam(defaultValue = "10")Integer pageSize,
                       String name, Short gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end) {
        PageEmp pageEmp = empService.page(page, pageSize, name, gender, begin, end);
        return Result.success(pageEmp);
    }
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        empService.delete(ids);
        return Result.success();
    }
    @PostMapping
    public Result save(@RequestBody Emp emp) {
        empService.save(emp);
        return Result.success();
    }
    @GetMapping ("/{id}")
    public Result getById(@PathVariable Integer id) {
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        empService.update(emp);
        return Result.success();
    }
    @GetMapping("/job")
    public Result getAll(Short job) {
        List<Emp> emp = empService.getAllEmp(job);
        return Result.success(emp);
    }
}
