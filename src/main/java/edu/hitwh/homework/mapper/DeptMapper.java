package edu.hitwh.homework.mapper;

import edu.hitwh.homework.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    @Select("select * from dept")
    List<Dept> list();
    @Delete("delete from dept where id = #{id}")
    void delete(Integer id);
    @Insert("insert into dept (name,create_time,update_time) values (#{name},now(),now())")
    void save(String name);
    @Select("select * from dept where id = #{id}")
    Dept selectById(Integer id);
    @Update("update dept set name = #{name},update_time = #{update_time} where id = #{id}")
    void update(Dept dept);
}
