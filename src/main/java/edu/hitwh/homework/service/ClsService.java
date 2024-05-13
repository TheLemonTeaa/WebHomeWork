package edu.hitwh.homework.service;

import edu.hitwh.homework.pojo.Cls;
import edu.hitwh.homework.pojo.PageCls;

import java.time.LocalDate;
import java.util.List;

public interface ClsService {
    List<Cls> getAllCls();
    PageCls page(Integer page, Integer pageSize, String className, LocalDate begin, LocalDate end);
    void delete(Integer id);
    void save(Cls cls);
    Cls getById(Integer id);
    void update(Cls cls);
}
