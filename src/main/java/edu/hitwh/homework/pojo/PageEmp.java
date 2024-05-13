package edu.hitwh.homework.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PageEmp {
    private long total;
    private List<Emp> result;

    public PageEmp(long total, List<Emp> result) {
        this.total = total;
        this.result = result;
    }
}
