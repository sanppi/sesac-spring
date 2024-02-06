package com.sesac.sesacspring.jpa.service;

import com.sesac.sesacspring.jpa.dto.StudentDTO;
import com.sesac.sesacspring.jpa.entity.Student;
import com.sesac.sesacspring.jpa.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    // StudentDTO 의 리스트를 리턴하는 getStudentAll 만들기
    public List<StudentDTO> getStudentAll() {
        // JpaRepository<Student, Integer> 여기서의 Student 가 아래의 Student
        List<Student> result = studentRepository.findAll(); // sql 문을 만들어주기 때문에 jpa를 쓰면 편하다.
        List<StudentDTO> students = new ArrayList<>();

        for ( Student student : result ) {
            // Builder :  객체를 만들 때 순서에 의해 생기는 문제, 명시적이지 못한 문제를 해결하기 위해 나온 방법
            // 생성자 주입, setter 를 통한 주입 -> 우리가 아는 것
            // setter : 필드 갯수만큼 매번 메소드를 추가해야 한다는 단점
            // 생성자 주입 : 여러 개의 필드가 있을 때 순서를 지켜줘야 함.
            StudentDTO studentDTO = StudentDTO.builder() // new Builder 만들고, 메소드를 세팅해서 닉네임, 이름 세팅해서
                    .nickname(student.getNickname())
                    .name(student.getName()) // name 이라는 필드를 지정하고 " " 이 안의 값을 넣음.
                    .build();
            students.add(studentDTO);
//                StudentDTO studentDTO = new StudentDTO();
//                studentDTO.setName("이름")... -> 이거랑 똑같은 것
        }
        return students;
    }

}
