package edu.hitwh.homework.service;


import edu.hitwh.homework.pojo.Emp;
import edu.hitwh.homework.pojo.PageEmp;

import java.time.LocalDate;
import java.util.List;


public interface EmpService {
    PageEmp page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end);
    void delete(List<Integer> ids);
    void save(Emp emp);
    Emp getById(Integer id);
    void update(Emp emp);
    List<Emp> getAllEmp(Short job);
    Emp login(Emp emp);
}
