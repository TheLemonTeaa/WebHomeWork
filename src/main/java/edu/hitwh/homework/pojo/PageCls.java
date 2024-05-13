package edu.hitwh.homework.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PageCls {
    private long total;
    private List<Cls> result;

    public PageCls(long total, List<Cls> result) {
        this.total = total;
        this.result = result;
    }
}
