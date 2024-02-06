package com.sesac.sesacspring.mybatis.controller;

import com.sesac.sesacspring.mybatis.dto.BoardDTO;
import com.sesac.sesacspring.mybatis.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board/mybatis")
public class BoardController {
    // 5 개의 method
    // 1. 전체 조회 ->  board.html 렌더링
    // 2. 작성 ( create ) : axios ( 동적폼전송, post ) = @RequestBody 로 데이터를 받을 것.
    // 3. 수정 ( update ) : axios ( 동적폼전송, patch )
    // 4. 삭제 ( delete ) : axios ( 동적폼전송, delete )
    // 5. 검색(조회) : axios ( 동적폼전송, )

    @Autowired
    BoardService boardService;

    // 1. 전체 조회
    @GetMapping("") // /board/mybatis
    public String getBoard(Model model) {
        List<BoardDTO> result = boardService.getBoardAll();
        model.addAttribute("list", result);
        return "board";
    }

    @PostMapping("") // /board/mybatis
    @ResponseBody // 응답을 주기 위함
    public boolean insertBoard(@RequestBody BoardDTO boardDTO) {
        // 2. 게시글 작성
        boardService.insertBoard(boardDTO);
        return true;
    }

    @PatchMapping("") // /board/mybatis
    @ResponseBody
    // public void라 했는데 ResponseBody 를 안 붙인다면 ?
    // 이거 없으면 원래 템플릿 파일을 보여주는데,
    // void 라면 현재 template 을 그대로 다시 보여준다.
    public void patchBoard(@RequestBody BoardDTO boardDTO) {
        boardService.patchBoard(boardDTO);

    }

    @DeleteMapping("")
    @ResponseBody
    public void deleteBoard(@RequestParam int id) {
        boardService.deleteBoard(id);
        // 어떤 id 값 삭제할건지도 보내주겠다. 어디로..? 그리고 이걸 서비스에서 만들어줘야 함.
        // 서비스 거쳐서 매퍼로..
    }

    // 컨트롤러 다음 서비스가서
    @GetMapping("/search") // /board/mybatis/search
    @ResponseBody // int를 리턴해야 하기 때문에 ResponseBody.
    // html확인하면 ? 뒤로 word라는 값으로 받아지고 있음
    // -> 서비스에 서치 보드를 실행하고 그 결과를 정수로 리턴해주고 있음.
    public int searchBoard(@RequestParam String word) {
        return boardService.searchBoard(word);
    }


}
