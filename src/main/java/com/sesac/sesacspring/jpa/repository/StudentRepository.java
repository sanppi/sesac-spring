package com.sesac.sesacspring.jpa.repository;

import com.sesac.sesacspring.jpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// JpaRepository<대상으로 지정할 엔티티, 해당 엔티티의 pk 타입 >
public interface StudentRepository extends JpaRepository<Student, Integer> {
    // 2 가지 방법이 있음.

    // 1. jpa 의 기본 규칙을 따르는 방법
    // findBy컬럼명
//    Student findByName(String name); // 반환되는 엔티티는 student, 들어오는 값은 name
    List<Student> findByName(String name); // primary key, unique key

    List<Student> findByNameAndNickname(String name, String nickname); // 이름과 닉네임이 모두 일치하는 친구를 검색
    List<Student> findByNameOrNickname(String name, String nickname); // 이름이 일치하거나 닉네임이 일치하는 경우
    // findByAgeGreaterThanEqual(int age) //  age 가 값보다 크거나 같은 경우

    // 2. 직접 쿼리를 작성헤서 연결
    // @Query("select s from Student s where s.name = :a")
    @Query(nativeQuery = true, value = "select * from Student where name = :a")
    List<Student> findTest(String a);

    // 실습 1.
    List<Student> findByNickname(String nickname);
    // int로 했을 때, 함수명 countByNickname 으로 했을 때
    // int countByNickname(String nickname);

    // 실습 2.

}