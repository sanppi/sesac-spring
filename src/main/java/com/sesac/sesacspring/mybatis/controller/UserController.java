package com.sesac.sesacspring.mybatis.controller;

import com.sesac.sesacspring.mybatis.dto.UserDTO;
import com.sesac.sesacspring.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Mybatis 수업 controller
@RestController
@RequestMapping("/user") // Method 에 상관없이 매핑해 줌
public class UserController {
    // C, R
    // 1. Table 생성 완료 (user)
    // 2. domain 생성 완료 (domain/user)
    // 3. mapper 만들기
    // 4. service 만들기
    // 5. controller 만들기
    @Autowired
    UserService userService;

    @GetMapping("/all") // /user 이 붙게 됨. url => /user/all 됨.
    public List<UserDTO> getUser() {
        List<UserDTO> result = userService.retrieveAll();
        return result;
    } // []

    @GetMapping("/user") // url => /user/user
    public String getUserInsert(
            @RequestParam String name,
            @RequestParam String nickname) {
        userService.createUser(name, nickname); // name, nickname 순서
        return "Success";
    }

    @GetMapping("/update")
    public String getUserUpdate(
            @RequestParam int id,
            @RequestParam String nickname) {
        userService.updateUser(id, nickname);
        return "Update Success";
    }

    @GetMapping("/delete")
    public String getUserDelete(
            @RequestParam int id) {
        userService.deleteUser(id);
        return "Delete Success";
    }
}
