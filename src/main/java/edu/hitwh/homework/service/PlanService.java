package edu.hitwh.homework.service;

import edu.hitwh.homework.pojo.PagePlan;
import edu.hitwh.homework.pojo.Plan;

public interface PlanService {
    PagePlan page(Integer page, Integer pageSize, String courseName, String teacher);
    void delete(Integer id);
    void save(Plan plan);
    Plan getById(Integer id);
    void update(Plan plan);
}
