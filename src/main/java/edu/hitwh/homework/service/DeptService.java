package edu.hitwh.homework.service;

import edu.hitwh.homework.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> list();
    void deleteById(Integer id);
    void saveDept(Dept dept);
    Dept selectById(Integer id);
    void updateDept(Dept dept);
}
