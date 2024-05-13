package edu.hitwh.homework.mapper;

import edu.hitwh.homework.pojo.Stu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.ComponentScan;


import java.util.List;

@Mapper
public interface StuMapper {
    List<Stu> list(String studentName, String studentID, Short degree, String className);
    void delete(List<Integer> ids);
    void save(Stu stu);
    Stu getById(Integer id);
    void update(Stu stu);
    List<Stu> getAll(Short gender);
}
