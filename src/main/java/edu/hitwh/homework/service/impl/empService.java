package edu.hitwh.homework.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.hitwh.homework.mapper.EmpMapper;
import edu.hitwh.homework.pojo.Emp;
import edu.hitwh.homework.pojo.PageEmp;
import edu.hitwh.homework.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class empService implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageEmp page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end){
        PageHelper.startPage(page,pageSize);
        List<Emp> empList = empMapper.list(name, gender, begin, end);
        Page<Emp> p = (Page<Emp>) empList;
        PageEmp pageEmp = new PageEmp(p.getTotal(), p.getResult());
        return pageEmp;
    }

    @Override
    public void delete(List<Integer> ids) {
        empMapper.delete(ids);
    }

    @Override
    public void save(Emp emp){
        emp.setCreate_time(LocalDateTime.now());
        emp.setUpdate_time(String.valueOf(LocalDateTime.now()));
        empMapper.save(emp);
    }

    @Override
    public Emp getById(Integer id) {
        return empMapper.getById(id);
    }

    @Override
    public void update(Emp emp) {
        emp.setUpdate_time(String.valueOf(LocalDateTime.now()));
        empMapper.update(emp);
    }

    @Override
    public List<Emp> getAllEmp(Short job){
        return empMapper.getAllEmp(job);
    }

    @Override
    public Emp login(Emp emp) {
        Emp e = empMapper.getByUsernameAndPassword(emp);
        return e;
    }
}
