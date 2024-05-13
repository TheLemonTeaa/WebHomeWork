package edu.hitwh.homework.mapper;

import edu.hitwh.homework.pojo.Cls;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ClsMapper {
    List<Cls> getAllCls();
    List<Cls> list(String className, LocalDate begin, LocalDate end);
    void delete(Integer id);
    void save(Cls cls);
    Cls getById(Integer id);
    void update(Cls cls);
}
