package edu.hitwh.homework.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Dept {
    private Integer id;
    private String name;
    private LocalDateTime create_time;
    private String update_time;
}
