package com.sesac.sesacspring.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
// @Controller : 해당 클래스가 Controller 의 역할을 하는 클래스라는 것을
// Spring Container 에게 알려준다.
public class HelloController {

    @GetMapping("/hi")
    // URL 을 매핑시켜주는 친구
    // 클라이언트가 /hi라는 경로로 GET method로
    // 접근한다면 아래 메소드를 실행시켜라

    public String getHi(Model model) {
        // Model : Controller 안의 메서드가 파라미터로 받을 수 있는 객체 중 하나
        // Model 안에 정보를 담아서 view 로 전달
        // IoC : 개발자가 직접 model 을 생성 X , 우리는 받아서 사용할 뿐.

        model.addAttribute("name", "홍길동");
        // model.addAttribute("key", "값");
        model.addAttribute("name2", "<strong>코딩온</strong>");
        model.addAttribute("age","22");

        String[] x = {"a", "b", "c", "d", "e"};
        model.addAttribute("item1", x);
        char[] alphabetArray = new char[26];
        char alphabet = 'A';

        for (int i = 0; i < 26; i++) {
            alphabetArray[i] = alphabet;
            alphabet++;
        }
        model.addAttribute("item2", alphabetArray);

        return "hi"; // 템플릿 파일의 이름
        // res.render("hi") -> hi 라는 템플릿 파일을 불러오라는 것. 글자가 아니라.
        // res.render("hi", {name:'홍길동'}) -> X
    }

    @GetMapping("/people")
    public String getPeople(Model model) {
        ArrayList<Person> people = new ArrayList<Person>();
        people.add(new Person("kim", 10));
        people.add(new Person("lee", 20));
        people.add(new Person("hong", 30));
        people.add(new Person("park", 40));
        model.addAttribute("people", people);

        Person p = new Person("h", 10);
        System.out.println(p.getName());
        return "person";
    }

    @Getter
    @Setter
    public class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

}
