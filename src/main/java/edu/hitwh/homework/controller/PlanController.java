package edu.hitwh.homework.controller;

import edu.hitwh.homework.pojo.PageCourse;
import edu.hitwh.homework.pojo.PagePlan;
import edu.hitwh.homework.pojo.Plan;
import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private PlanService planService;
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1")Integer page,
                       @RequestParam(defaultValue = "10")Integer pageSize,
                       String courseName, String teacher) {
        PagePlan pagePlan = planService.page(page, pageSize, courseName, teacher);
        return Result.success(pagePlan);
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        planService.delete(id);
        return Result.success();
    }
    @PostMapping
    public Result save(@RequestBody Plan plan) {
        planService.save(plan);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Plan plan = planService.getById(id);
        return Result.success(plan);
    }
    @PutMapping
    public Result update(@RequestBody Plan plan) {
        planService.update(plan);
        return Result.success();
    }
}
