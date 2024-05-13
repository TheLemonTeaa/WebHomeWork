package edu.hitwh.homework.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.hitwh.homework.mapper.EmpMapper;
import edu.hitwh.homework.mapper.StuMapper;
import edu.hitwh.homework.pojo.PageStu;
import edu.hitwh.homework.pojo.Stu;
import edu.hitwh.homework.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class stuService implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Override
    public PageStu page(Integer page, Integer pageSize, String studentName, String studentID, Short degree, String className){
        PageHelper.startPage(page,pageSize);
        List<Stu> stuList = stuMapper.list(studentName,studentID,degree,className);
        Page<Stu> p = (Page<Stu>) stuList;
        PageStu pageStu = new PageStu(p.getTotal(),p.getResult());
        return pageStu;
    }

    @Override
    public void delete(List<Integer> ids){
        stuMapper.delete(ids);
    }

    @Override
    public void save(Stu stu){
        stu.setCount(0);
        stu.setScore(0);
        stu.setUpdate_time(String.valueOf(LocalDateTime.now()));
        stuMapper.save(stu);
    }

    @Override
    public Stu getById(Integer id){
        return stuMapper.getById(id);
    }

    @Override
    public void update(Stu stu){
        stu.setUpdate_time(String.valueOf(LocalDateTime.now()));
        stuMapper.update(stu);
    }

    @Override
    public List<Stu> getAll(Short gender){
        return stuMapper.getAll(gender);
    }
}
