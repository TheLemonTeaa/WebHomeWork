package edu.hitwh.homework.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.hitwh.homework.mapper.PlanMapper;
import edu.hitwh.homework.pojo.Plan;
import edu.hitwh.homework.pojo.PagePlan;
import edu.hitwh.homework.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class planService implements PlanService {
    @Autowired
    private PlanMapper planMapper;
    @Override
    public PagePlan page(Integer page, Integer pageSize, String courseName, String teacher){
        PageHelper.startPage(page,pageSize);
        List<Plan> planList = planMapper.list(courseName, teacher);
        Page<Plan> p = (Page<Plan>) planList;
        PagePlan pagePlan = new PagePlan(p.getTotal(), p.getResult());
        return pagePlan;
    }
    @Override
    public void delete(Integer id) {
        planMapper.delete(id);
    }
    @Override
    public void save(Plan plan) {
        planMapper.save(plan);
    }
    @Override
    public void update(Plan plan) {
        planMapper.update(plan);
    }
    @Override
    public Plan getById(Integer id) {
        return planMapper.getById(id);
    }
}
