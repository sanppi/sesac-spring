package com.sesac.sesacspring.jpaboard.service;

import com.sesac.sesacspring.jpaboard.domain.Board;
import com.sesac.sesacspring.jpaboard.dto.BoardDTO;
import com.sesac.sesacspring.jpaboard.entity.BoardEntity;
import com.sesac.sesacspring.jpaboard.mapper.BoardJpaMapper;
import com.sesac.sesacspring.jpaboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BoardJpaService {
//    @Autowired
//    BoardJpaMapper boardMapper;
    @Autowired
    BoardRepository boardRepository; // 레포 불러와서 사용할 수 있게

    public List<BoardDTO> getBoardAll(){
//        List<Board> result = boardMapper.getBoardAll();
        List<BoardEntity> jpaResult = boardRepository.findAll();
        List<BoardDTO> boards = new ArrayList<>(); // dto를 수정할 필요없음 mybatis나 jpa나 똑같으니

        // for문 자체는 변경된 게 없고, BoardEntity 으로만 바뀐 것.
        for ( BoardEntity boardEntity : jpaResult) {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setBoardID(boardEntity.getId());
            boardDTO.setTitle(boardEntity.getTitle());
            boardDTO.setContent(boardEntity.getContent());
            boardDTO.setWriter(boardEntity.getWriter());
            boardDTO.setRegistered(
                    new SimpleDateFormat("yyyy-MM-dd").format(boardEntity.getRegistered())
            ); // 요 패턴으로 포매팅해서 string 으로 반환받는 것.

            // 1)  dto 의 값을 timestamp 로 변경
            // 2) timestamp -> string 파싱하거나 -> 이거 사용해보겠다!
            // java에는 (SimplaeDateFormat)이 존재 = 내가 원하는 형태로 데이터 포맷할 수 있음.
            boardDTO.setNo(100 + boardEntity.getId());
            boards.add(boardDTO);
        }
//        for ( Board board : result ){
//            BoardDTO boardDTO = new BoardDTO();
//            boardDTO.setBoardID(board.getId());
//            boardDTO.setTitle(board.getTitle());
//            boardDTO.setContent(board.getContent());
//            boardDTO.setWriter(board.getWriter());
//            boardDTO.setRegistered(board.getRegistered());
//            boardDTO.setNo(100 + board.getId());
//            boards.add(boardDTO);
//        }
        return boards;
    }
    public boolean insertBoard(BoardDTO boardDTO) {
        // repository로 바꿀 거면 save 써야 함
        // save()
        BoardEntity boardEntity = BoardEntity.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter()).build();
        boardRepository.save(boardEntity);

//        Board board = new Board();
//        board.setTitle(boardDTO.getTitle());
//        board.setContent(boardDTO.getContent());
//        board.setWriter(boardDTO.getWriter());
//
//        boardMapper.insertBoard(board);
        return true;
    }

    public void patchBoard(BoardDTO boardDTO) {
        // save()
        BoardEntity boardEntity = boardRepository.findById(boardDTO.getBoardID())
                .orElseThrow(()->new NoSuchElementException("board patch : id is wrong"));

        BoardEntity modified = BoardEntity.builder()
                .id(boardEntity.getId())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .registered(boardEntity.getRegistered())
                .build();
        boardRepository.save(modified);
        // board.getBoardID // title, content, writer
//        Board board = new Board();
//        board.setId(boardDTO.getBoardID()); // update의 where
//        board.setTitle(boardDTO.getTitle());
//        board.setContent(boardDTO.getContent());
//        board.setWriter(boardDTO.getWriter());
//        boardMapper.patchBoard(board);
    }
    public void deleteBoard(int id){
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("board patch : id is wrong"));

        boardRepository.delete(boardEntity);
        // boardRepository.deleteById(id); -> 귀찮으면 그냥 이렇게 삭제해도 됨.

//        boardMapper.deleteBoard(id);
    }

    public int searchBoard(String word) {
        List<BoardEntity> result = boardRepository.searchByWord(word);
//        List<Board> result = boardMapper.searchBoard(word);
        return result.size();
    }
}
