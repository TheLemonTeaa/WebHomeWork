package edu.hitwh.homework.mapper;

import edu.hitwh.homework.pojo.Emp;

import org.apache.ibatis.annotations.Mapper;


import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {
    List<Emp> getAllEmp(Short job);
    List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);
    void delete(List<Integer> ids);
    void save(Emp emp);
    Emp getById(Integer id);
    void update(Emp emp);
    Emp getByUsernameAndPassword(Emp emp);
}
