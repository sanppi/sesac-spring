// BoardMapper.xml 과 연결되어 사용됨.
// 이 인터페이스에 정의된 메소드들은 xml 파일에서 해당하는 sql문을 찾아 실행하게 된다.

package com.sesac.sesacspring.mybatis.mapper;

import com.sesac.sesacspring.mybatis.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> getBoardAll(); // 전체 조회 ( select * from board; ) -> sql 로 작성했다면 이렇게
    void insertBoard(Board board); // 삽입 (insert)
    void patchBoard(Board board); // 수정 (update)
    void deleteBoard(int id); // 삭제 (delete)

    List<Board> searchBoard(String word); // list 를 반환하는 searchBoard
}
// sql 을 작성하거나, xml 파일을 작성하거나 선택.
// 1) mapper 에는 메소드가 있고, xml 에는 없는 경우 -> 실행했을 때 오류
// 2) xml 에는 있고, mapper에는 없는 경우 -> 실행 자체가 안 된다.

