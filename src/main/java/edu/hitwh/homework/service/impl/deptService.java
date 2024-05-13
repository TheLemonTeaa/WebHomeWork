package edu.hitwh.homework.service.impl;

import edu.hitwh.homework.mapper.DeptMapper;
import edu.hitwh.homework.pojo.Dept;
import edu.hitwh.homework.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class deptService implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    @Override
    public void deleteById(Integer id) {
        deptMapper.delete(id);
    }

    @Override
    public void saveDept(Dept dept) {
        deptMapper.save(dept.getName());
    }

    @Override
    public Dept selectById(Integer id) {
        return deptMapper.selectById(id);
    }

    @Override
    public void updateDept(Dept dept) {
        dept.setUpdate_time(LocalDateTime.now().toString());
        deptMapper.update(dept);
    }
}
