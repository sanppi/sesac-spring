package com.sesac.sesacspring.jpa.controller;

import com.sesac.sesacspring.jpa.dto.StudentDTO;
import com.sesac.sesacspring.jpa.entity.Student;
import com.sesac.sesacspring.jpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

//컨트롤러가 실행하는 건 서비스임.
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;
//    @GetMapping("/count")
//    public int getCountAll(){}

    // 1. 전체 검색 ( select * from student)
    @GetMapping("/all")
    public List<StudentDTO> getAll(){
        // student의 목록을 전부 가져와서 보여주는 api
        List<StudentDTO> result = studentService.getStudentAll();
        return result;
    }
    // 2. 삽입 ( insert into ~~~ )
    @GetMapping("/insert") // /student/insert?name=이름
    public String insertStudent(@RequestParam String name,
                                @RequestParam String nickname,
                                @RequestParam Student.LoginType type) {
    return studentService.insertStudent(name, nickname, type);
    } //  이름, 닉네임, LoginType

    // 3. 조건에 따른 검색 ( select * from student where name='' )
    @GetMapping("/search/name") //search/name?name=이름
    public String searchStudentByName(@RequestParam String name) {
        return studentService.searchStudentByName(name);
    }

    // 4. 조건에 따른 검색 (2) select * from student where id='' )
    @GetMapping("/search/id")
    public String searchStudentByID(@RequestParam int id) {
        return studentService.searchStudentById(id);
    }

    // 실습 1.
    @GetMapping("/count/nickname") // /student/count/nickname?nickname=닉네임
    public String countStudentByNickname(@RequestParam String nickname) {
        return studentService.countStudentByNickname(nickname);
    }

    // 실습 2.
    @GetMapping ("/update")
    public String updateStudent(@RequestParam int id,
                                @RequestParam String name) {
        // id: 내가 변경할 데이터의 primary key
        // name: 내가 변경하고자 하는 name 컬럼의 값
        // 컨트롤러에는 실제 로직이 적히지 않는다.  -> 서비스에 로직 적힐 것.
        return studentService.updateStudent(id, name);
    }




//    @GetMapping("/search")
//    public ? getSearch(@RequestBody int id){}
}
