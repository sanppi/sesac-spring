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

    public String insertStudent(String name, String nickname, Student.LoginType type){
        // 받아온 데이터로 repository 의 save 메소드를 호출
        Student student = Student.builder().name(name).nickname(nickname).type(type).build();
        Student newStudent = studentRepository.save(student);
        // newStudent : save를 한 후 반환되는 Entity 객체

        return newStudent.getId() + newStudent.getName();
    }

    public String searchStudentByName(String name) {
//        Student student = studentRepository.findByName(name); // repository에서 호출
        List<Student> student = studentRepository.findByName(name); // repository에서 호출
//          return "id는 " + student.getId() + "입니다.";
        return "해당되는 이름의 사용자는 " + student.size() + "명입니다.";
    }
    public String searchStudentById(int id) {
//        Student student = studentRepository.findById(id);
        // if ( student != null)
        // Optional<List<T>>
        Student student = studentRepository
                .findById(id)
                .orElseThrow(() ->new RuntimeException("사용자가 없다!!!"));
        // orElse() :  있으면 반환하고 없으면 다른 값 반환
        // orElseThrow() :  있으면 반환하고 없으면 error 처리
        return student.getName();

//        Optional<Student> student = studentRepository.findById(id);
//        if (student.isPresent()) {
//            // ispresent : 객체의 여부를 boolean 으로 반환
//            return student.get().getName();
//            // get : Optional 에 담긴 객체를 반환
//        }
//        return "null";

        // Optional<T> : java 8 부터 등장한 친구
        // -> findById같은 단일값, id검색할 때만 optional사용하고 다른건 list로 하면 됨
        // null 일 수도 있는 객체를 감사는 wrapper 클래스
    }

    // 실습 1.
    // 리더님 해답
//    public int searchCntByNickname(String nickname) {
//        // count라는 method를 활용해라
//        // countByNickname(String nickname) = select count(*)
//        return studentRepository.countByNickname(nickname);
//    }

    public String countStudentByNickname(String nickname) {
        List<Student> student = studentRepository.findByNickname(nickname);
        return "동일한 nickname " + student.size() + "명입니다.";
    }

    // 실습 2.
    public String updateStudent(int id, String name){
        // save(T) : 새로운 entity를 insert or 기존 entity를 update
        // T의 기본값(pk)의 상태에 따라 다르게 동작
        // - pk 값이 존재하는 경우 : pk 와 연결된 entity를 update
        // - pk 값이 없는 경우 : 새로운 entity를 insert

//        리더님 해답 코드!!
//        Student student = studentRepository.findById(id)
//                 .orElseThrow(()-> new NoSuchElementException("id is wrong!"))

            // 내가 수정하고자 하는 객체로 만들고 그걸 사용해야 함. 그래야 내가 위에서 가져온 Student객체가
            // 훼손되지 않고 원하는 동작을 하게 됨
            // save를 할 때는 insert, update -> 새로운 객체를 반드시 만들어줘야 함! (이해 안 되면 이거라도 외우기)
            // 강사님 코드로 하니깐 ............. setName 안해도 되네.....??
            // entity에서 나는 @Setter 추가해서 했는데...........ㅜ ㅜ ㅜ ㅜ ㅜ ㅜ ㅜ ㅜ

//        Student modifiedStudent =
//                Student.builder().id(id).name(name).nickname(student.getNickname())
//                .type(student.getType()).build(); // 이렇게 넣는 이유는 nickname이랑 type은 변경 안할것이니깐
//        studentRepository.save(modifiedStudent);
//        return "Update Success";

        Student student = studentRepository
                .findById(id)
                .orElseThrow(() ->new RuntimeException("사용자가 없다!!!"));

        student.setName(name);

        Student updatedStudent = studentRepository.save(student);

        return updatedStudent.getId() + "의 name을 " + updatedStudent.getName() + "로 변경 완료";
    }
}
