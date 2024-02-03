package com.sesac.sesacspring.mapper;

import com.sesac.sesacspring.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

// Mapper 는 interface 로 정의가 되어야 함.
@Mapper
public interface UserMapper {
    // sql 문을 정의 or xml 파일을 읽거나 -> Mapper 는 이 둘 중 하나 작업을 하는 것

    // xml 파일을 읽어서 실행하겠다.
    List<User> retrieveAll();

    // sql 문 정의
    @Insert("insert into user(name, nickname) values(#{name}, #{nickname})") // db 에 값 넣어주고 있음.
    void createUser(
            String name, String nickname); // name, nickname 전달받기 위해서.

//    @Insert("insert into user(name, nickname) values(#{name}, #{nickname})") // db 에 값 넣어주고 있음.
//    void createUser(User user); // 이렇게 작성해도 service의 User 때문에 알아서 만들 수 있게 해 줌.


    @Update("UPDATE user SET nickname = #{nickname} WHERE id = #{id}")
    void updateUser(
            int id, String nickname);

    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteUser(int id);

}
