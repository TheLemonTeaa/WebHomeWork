package edu.hitwh.homework.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PagePlan {
    private long total;
    private List<Plan> result;

    public PagePlan(long total, List<Plan> result) {
        this.total = total;
        this.result = result;
    }
}
