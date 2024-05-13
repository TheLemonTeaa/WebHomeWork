package edu.hitwh.homework.controller;

import edu.hitwh.homework.pojo.Emp;
import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.service.EmpService;
import edu.hitwh.homework.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private EmpService empService;
    @PostMapping("/login")
    public Result login(@RequestBody Emp emp) {
        Emp e = empService.login(emp);
        if (e != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("username", e.getUsername());
            claims.put("password", e.getPassword());
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }
        return Result.error("用户名或密码错误!");
    }
    @GetMapping("/updatePassword")
    public Result updatePassword(String token) {
        Claims claims = JwtUtils.parseJWT(token);
        return Result.success(claims);
    }
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Emp emp) {
        empService.update(emp);
        return Result.success();
    }
}
