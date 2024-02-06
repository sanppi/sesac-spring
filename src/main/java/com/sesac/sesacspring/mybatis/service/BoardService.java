package com.sesac.sesacspring.mybatis.service;

import com.sesac.sesacspring.mybatis.domain.Board;
import com.sesac.sesacspring.mybatis.dto.BoardDTO;
import com.sesac.sesacspring.mybatis.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardMapper boardMapper;

    public List<BoardDTO> getBoardAll(){
       List<Board> result =  boardMapper.getBoardAll();
       List<BoardDTO> boards =  new ArrayList<>();

       for (Board board : result) {
           BoardDTO boardDTO = new BoardDTO();
           boardDTO.setBoardID(board.getId());
           boardDTO.setTitle(board.getTitle());
           boardDTO.setContent(board.getContent());
           boardDTO.setWriter(board.getWriter());
           boardDTO.setRegistered(board.getRegistered());
           boardDTO.setNo(100 + board.getId());
           boards.add(boardDTO);
       }
       return boards;
    }
    public boolean insertBoard(BoardDTO boardDTO) {
        Board board = new Board(); // domain 보드에 담는다
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setWriter(boardDTO.getWriter());

        boardMapper.insertBoard(board);
        return true;
    }

    // 컨트롤러(public void patchBoard 부분) 작성하고 여기로 넘어옴. 이거 하고는 xml 로 갔음.
    public void patchBoard(BoardDTO boardDTO) {
        // board.getBoardID // title, content, writer
        Board board = new Board(); // domain 보드에 담는다
        board.setId(boardDTO.getBoardID()); // update 의 where 에 들어갈 친구
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setWriter(boardDTO.getWriter());
        boardMapper.patchBoard(board);
    }

    public void deleteBoard(int id) {
        boardMapper.deleteBoard(id);
    }

    public int searchBoard(String word) { // 컨트롤러에서 보내는 word를 받고 있음.
        List<Board> result = boardMapper.searchBoard(word);
        return result.size();
    }
}


