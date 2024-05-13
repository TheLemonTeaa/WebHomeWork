package edu.hitwh.homework.service;

import edu.hitwh.homework.pojo.PageStu;
import edu.hitwh.homework.pojo.Stu;

import java.util.List;

public interface StuService {
    PageStu page(Integer page, Integer pageSize, String studentName, String studentID, Short degree, String className);
    void delete(List<Integer> ids);
    void save(Stu stu);
    Stu getById(Integer id);
    void update(Stu stu);
    List<Stu> getAll(Short gender);
}
