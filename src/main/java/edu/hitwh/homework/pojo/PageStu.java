package edu.hitwh.homework.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PageStu {
    private long total;
    private List<Stu> result;

    public PageStu(long total, List<Stu> result) {
        this.total = total;
        this.result = result;
    }
}
