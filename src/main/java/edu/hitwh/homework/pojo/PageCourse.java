package edu.hitwh.homework.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PageCourse {
    private long total;
    private List<Course> result;

    public PageCourse(long total, List<Course> result) {
        this.total = total;
        this.result = result;
    }
}
