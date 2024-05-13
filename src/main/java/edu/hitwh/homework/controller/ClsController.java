package edu.hitwh.homework.controller;

import edu.hitwh.homework.pojo.Cls;
import edu.hitwh.homework.pojo.PageCls;
import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.service.ClsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/class")
public class ClsController {
    @Autowired
    private ClsService clsService;
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1")Integer page,
                       @RequestParam(defaultValue = "10")Integer pageSize,
                       String className,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end) {
        PageCls pageCls = clsService.page(page, pageSize, className, begin, end);
        return Result.success(pageCls);
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        clsService.delete(id);
        return Result.success();
    }
    @PostMapping
    public Result save(@RequestBody Cls cls) {
        clsService.save(cls);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Cls cls = clsService.getById(id);
        return Result.success(cls);
    }
    @PutMapping
    public Result update(@RequestBody Cls cls) {
        clsService.update(cls);
        return Result.success();
    }
    @GetMapping("/all")
    public Result getAll() {
        List<Cls> cls = clsService.getAllCls();
        return Result.success(cls);
    }
}
