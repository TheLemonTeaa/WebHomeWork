package edu.hitwh.homework.controller;

import edu.hitwh.homework.pojo.Dept;
import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depts")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping
    public Result list() {
        List<Dept> depts = deptService.list();
        return Result.success(depts);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        deptService.deleteById(id);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Dept dept) {
        deptService.saveDept(dept);
        return Result.success();
    }

    @RequestMapping("/{id}")
    public Result selectById(@PathVariable Integer id) {
        Dept dept = deptService.selectById(id);
        return Result.success(dept);
    }

    @PutMapping
    public Result update(@RequestBody Dept dept) {
        deptService.updateDept(dept);
        return Result.success();
    }
}
