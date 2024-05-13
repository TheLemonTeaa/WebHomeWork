package edu.hitwh.homework.mapper;

import edu.hitwh.homework.pojo.Course;
import edu.hitwh.homework.pojo.Plan;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PlanMapper {
    List<Plan> list(String courseName, String teacher);
    void delete(Integer id);
    void save(Plan plan);
    Plan getById(Integer id);
    void update(Plan plan);
}
