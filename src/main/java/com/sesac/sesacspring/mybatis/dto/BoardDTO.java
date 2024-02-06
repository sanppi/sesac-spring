// DTO 객체: 데이터 전송을 위한 객체로, 클라이언트와 서버 간의 통신에 사용
package com.sesac.sesacspring.mybatis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
    private int boardID;
    private String title, content, writer, registered;
    private int no;
}
