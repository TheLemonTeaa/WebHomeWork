package edu.hitwh.homework.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Emp {
    private Integer id;
    private String username;
    private String name;
    private String password;
    private String entrydate;
    private Short gender;
    private String image;
    private Short job;
    private Integer dept_id;
    private LocalDateTime create_time;
    private String update_time;
}
