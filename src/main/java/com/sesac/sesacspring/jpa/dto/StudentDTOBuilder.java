// StudentDTO 구조 어떻게 되는지 보여주기 위해 만든 파일.

package com.sesac.sesacspring.jpa.dto;

public class StudentDTOBuilder {
    private final String name;
    private final String nickname;

    public static class Builder {
        private String name;
        private String nickname;

        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder nickname(String nickname){
            this.nickname = nickname;
            return this;
        }
        public StudentDTOBuilder build() { return new StudentDTOBuilder(name, nickname); }
    }
    public StudentDTOBuilder(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;
    }
    public static Builder builder(){ return new Builder(); } // Builder 라는 클래스를 리턴함
}

