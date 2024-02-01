package com.sesac.sesacspring.controller;

import com.sesac.sesacspring.dto.UserDTO;
import com.sesac.sesacspring.vo.Prac;
import com.sesac.sesacspring.vo.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// @RestController // @controller + @responsebody
@Controller
public class MainController {
    @GetMapping("/")
    public String getMain() { return "request"; }

    // ===GET===
    // 매개변수를 넘겨받는 방법
    // 1. /test?id=123 -> ? 뒤에 key 값으로 vaule 로 -> @RequestParam
    // 2. /test/123 -> url 안에 직접 값을 담아서 보내는 것 -> @PathVariable

    @GetMapping("/get/response1")
    // ?key=value
    // ?name=
    // @RequestParam 은 기본값으로 required=true
    public String getResponse1(
            @RequestParam(value = "name") String i,
            Model model) {
        // @RequestParam 어노테이션
        // - ?name=123&id=11&age=abc ( o )
        // - ?id=11@hashtag=abc ( x ) -> required true 라 name 값 무조건 있어야.
        // - string query(?뒤의 값)에서 key("name")에 대한
        // - value 를 변수("i")에 매핑
        // - required=true 기본값 -> 요청 url에서 설정한 key가 필수로 있어야 해요.
        model.addAttribute("name", i);
        return "response";
    }
    @GetMapping("/get/response2")
    // required=false 옵션 ( @RequestParam(value="", required=false) )
    // - query string 에서 특정 key 를 옵셔널하게 받아야 하는 경우
    // ex) 검색할 때 (검색어(필수) 해시태그(선택)
    // @RequestParam(value="search") String search,
    // @RequestParam(value="hashtag", required=false) String hashtag

    public String getResponse2(
        @RequestParam(value="name", required=false) String name,
        Model model
    ) {
        model.addAttribute("name", name);
        return "response";
    }

    @GetMapping("/get/response3/{param1}/{param2}")
    // url 안에 넣을 때 @PathVariable -> url 경로에 있는 것을 사용한다?
    public String getResponse3(
            @PathVariable String param1,
            @PathVariable(value = "param2") String age,
            Model model) {
        /*
        * @PathVariable 어노테이션
        * - /test/{id} 형식의 URL 경로로 데이터를 넘겨줄 때 받는 방법
        * - 기본적으로 경로 변수의 값을 필수로 받아야 하기 때문 ( 보내지 않으면 404 error)
        * */
        model.addAttribute("name", param1);
        model.addAttribute("age", age);
        return "response";
    }

    // pathVariable 을 보낼 때 선택적으로 처리해야 한다면
    @GetMapping({"/get/response4/{param1}", "/get/response4/{param1}/{param2}"})
    public String getResponse4(
            @PathVariable String param1,
            @PathVariable(required = false, value = "param2") String age,
            Model model) {
        // 중요 optional 한 parameter 은 맨 뒤에 오도록 설정 -> param2를 url에서 뒤에 오게 해야 한다.
        model.addAttribute("name", param1);
        model.addAttribute("age", age);
        return "response";
    }

    // post 방식 - @RequestParam
    @PostMapping("/post/response1")
    public String postResponse1(
            @RequestParam(value= "name") String a,
            @RequestParam(value= "age") String b,
            Model model
    ){
        model.addAttribute("name", a);
        model.addAttribute("age", b);
        return "response";
    }

    @PostMapping("/post/response2")
    public String postResponse2(
            @RequestParam(value= "name", required = false) String a,
            @RequestParam(value= "age", required = false) String b,
            Model model
    ){
        model.addAttribute("name", a);
        model.addAttribute("age", b);
        return "response";
    }

    // @ResponseBody : 응답 결과를 client 한테 보내고 싶을 떄 사용함.
    // 응답 시 객체를 json 형태로 리턴한다. ( 직렬화 )
    // express res.send 유사

    @PostMapping("/post/response3")
    @ResponseBody
    public String postResponse3(
            @RequestParam(value= "name", required = false) String a,
            @RequestParam(value= "age", required = false) String b,
            Model model
    ){
        model.addAttribute("name", a);
        model.addAttribute("age", b);
        return a + "-" + b;
    }

    // ==== 실습 1, 2 ====
    @GetMapping("/introduce/{param1}")
    public String getIntroduce1(
            @PathVariable String param1,
            Model model
    ) {
        model.addAttribute("name", param1);
        return "practice";
    }

    @GetMapping("/introduce")
    public String getIntroduce2(
            @RequestParam(value="name", required=false) String name,
            @RequestParam(value="age", required=false) String age,
            Model model
    ) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "response";
    }


    @PostMapping("/info")
    public String infoResponse(
            @RequestParam(value= "name", required = false) String a,
            @RequestParam(value= "gender", required = false) String b,
            @RequestParam(value= "year", required = false) String c,
            @RequestParam(value= "month", required = false) String d,
            @RequestParam(value= "day", required = false) String e,
            @RequestParam(value= "interest", required = false) String f,
            Model model
    ){
        model.addAttribute("name", a);
        model.addAttribute("gender", b);
        model.addAttribute("year", c);
        model.addAttribute("month", d);
        model.addAttribute("day", e);
        model.addAttribute("interest", f);
        return "practice2";
    }

    // ==== DTO ====
    @GetMapping("/dto/response1")
    @ResponseBody
    public String dtoResponse1(@ModelAttribute UserDTO userDTO){
        // DTO : getter 와 setter 가 있는 객체
        // Get 방식에서 DTO 객체로 담아서 값이 받아지는 구나. 를 알 수 있음.
        // ModelAttribute : HTML 폼 데이터를 컨트롤러로 전달할 때 객체에 매핑하는 어노테이션. 생략해도 알아서 붙여줌
        // 매핑 = setter 함수 실행
        // ?name=홍길동&age=10 -> setName("홍길동") setAge("10")
        return userDTO.getName() + " " + userDTO.getAge();
    }

    // RequestBody : 요청의 본문에 있는 데이터(body)를 받는 친구
    @GetMapping("/dto/response11")
    @ResponseBody
    public String dtoResponse11(@RequestBody UserDTO userDTO) {
        return userDTO.getName() + " " + userDTO.getAge();
    }

    // 일반 폼 전송 -> www-x-form-urlencoded (데이터 형식) => 쿼리 매개변수
    // 일반 폼 전송 -> RequestBody 값을 x
    // RequestBody 는 요청의 본문에 있는 데이터(body)를 처리할 수 있기 때문에
    // json, xml 일 때만 실행이 가능
    // application/json

    // 일반 폼전송 - DTO (getter, setter 모두 있는 친구)
    // 1) 어노테이션 없이 DTO 로 받을 경우 -> 0
    // 2) @ModelAttribute DTO 받을 경우 -> 0
    // 3) @RequestBody DTO 받을 경우 -> 오류

    // 일반 폼 전송은 www-x-form-urlencoded 형식이기 때문에
    // get 이든 post 든 요청의 본문에 데이터가 들어가는 게 아닌 폼 데이터 형태로
    // url 로 데이터가 전송됨. -> 즉, 일반 폼전송은 RequestBody 사용 불가

    // 일반폼전송 - VO
    // 1. get, @ModelAttribute => null null
    // (null 인 이유는 @ModelAttribute 가 매핑할 set 함수가 UserVO에 없어서)
    @GetMapping("/vo/response1")
    @ResponseBody
    public String voResponse1(UserVO userVO) {
        return userVO.getName() + " " + userVO.getAge();
    }

    // 2. post, @ModelAttribute => null null
    @PostMapping("/vo/response2")
    @ResponseBody
    public String voResponse2(UserVO userVO) {
        return userVO.getName() + " " + userVO.getAge();
    }

    // 3. post, @RequestBody => 오류 발생
    @PostMapping("/vo/response3")
    @ResponseBody
    public String voResponse3(@RequestBody UserVO userVO) {
        return userVO.getName() + " " + userVO.getAge();
    }

    ////////////// -- axios 를 이용한 데이터 처리
    @GetMapping("/axios/response1")
    @ResponseBody
    public String axiosResponse1(@RequestParam String name, @RequestParam String age) {
        return name + " " + age;
    } // 1. Axios - get - @RequestParam -> 0

    @GetMapping("/axios/response2")
    @ResponseBody
    public String axiosResponse2(UserDTO userDTO) {
        // @ModelAttribute
        // axios = application/json

        return userDTO.getName() + " " + userDTO.getAge();
    } // 2. Axios - get - @ModelAttribute -> 0

    // 정리
    /*
    ?key=value
    일반 폼전송은 (get, post 상관없이 url)
    @RequestParam -> Param 하나하나 받는 게 번거로워서 아래 방법 나눠짐

    객체로 받는 방법
    1. @ModelAttribute
    : URL 로 들어온 값을 처리하는 친구 -> 객체에 setter 함수를 실행해주는 친구
    - ex) ?name=홍길동
    - 1) 넘어온 key를 @ModelAttribute 뒤의 객체에서 필드가 존재하는지 확인
        -> UserDTO 에 private String name;
    - 2) 필드가 존재한다면 그 필드에 해당하는 set 함수를 실행(setName, setAge 함수)
        -> UserDTO.setName("홍길동")

    2. @RequestBody
    : 클라이언트의 요청 중 본문(body) 에 들어있는 데이터를 처리
    - setter 함수 실행 x 필드 자체에 값을 넣어줘요. 그렇기 때문에 값이 들어감.
    - @RequestBody 는 각각의 필드(변수)에 직접적으로 값을 주입 (필드에 내장된 set 함수를 실행)
    -> 일반 폼 전송 -> url 에 데이터 표시 0 body x

    - .body(post)
    - axios.post({"":""}}
     */

    @PostMapping("/axios/response3")
    @ResponseBody
    // url 이었는데, axios post는 url에 데이터가 x
    // url 에 아무것도 없는데 name, age required=true 기 때문에 에러가 발생
    public String axiosRes3(@RequestParam String name, @RequestParam String age){
        return "이름: " + name + ", 나이: "+ age;
    }

    @PostMapping("/axios/response4")
    @ResponseBody
    public String axiosRes4(UserDTO userDTO){
        return "이름:" + userDTO.getName() + ", 나이: "+ userDTO.getAge();
    } // axios + post 데이터 -> @ModelAttribute 0 (실행은 되지만 null 값 나옴)
    // ModelAttribute 를 이용해 데이터를 보냈을 때 값이 null
    // axios 로 보내면 url 로 데이터를 보내는 게 아니라 본문으로 데이터를 보내게 된다.
    // 즉, @ModelAttribute 가 값을 볼 수 없는 것.

    @PostMapping("/axios/response5")
    @ResponseBody
    public String axiosRes5(@RequestBody UserDTO userDTO){
        return "이름:" + userDTO.getName() + ", 나이: "+ userDTO.getAge();
    } // axios + post 데이터 -> @RequestBody 0

    // ========== VO 이용 with. axios ==========

    // 1. get + @RequestParam
    // get 으로 보낸다는 건 내가 url로 보낸다는 것이니 정상적으로 실행됨.
    @GetMapping("/axios/vo/response1")
    @ResponseBody
    public String axiosVoRes1(@RequestParam String name, @RequestParam String age) {
        return "이름: " + name + ", 나이: " + age;
    }

    // 2. get + @ModelAttribute VO
    // 실행은 되지만 결과값은 null,  VO에 setter 없어서.
    @GetMapping("/axios/vo/response2")
    @ResponseBody
    public String axiosVoRes2(UserVO userVO) {
        return "이름: "+ userVO.getName() + ", 나이: "+ userVO.getAge();
    }

    // 3. post + @RequestParam(require=true) -> x
    @PostMapping("/axios/vo/response3")
    @ResponseBody
    public String axiosVoRes3(@RequestParam String name, @RequestParam String age) {
        return "이름: " + name + ", 나이: " + age;
    }

    // 4. post + @ModelAttribute VO
    // -> 실행은 되지만 null 값
    @PostMapping("/axios/vo/response4")
    @ResponseBody
    public String axiosVoRes4(UserVO userVO){
        return "이름: "+ userVO.getName() + ", 나이: "+ userVO.getAge();
    }

    // 여기까지 봤을 때 @ModelAttribute 는 일단 실행은 된다는 것을 알 수 있음.

    // 5. post + @RequestBody VO -> 0
    @PostMapping("/axios/vo/response5")
    @ResponseBody
    public String axiosVoRes5(@RequestBody UserVO userVO){
        // axios post 로 데이터를 보내면 요청의 본문(body)에 데이터가 들어감
        // @RequestBody 는 요청의 본문에 있는 데이터를 읽을 수 있다.
        // UserVO 클래스는 setter 메소드가 없어요.
        // @RequestBody는 데이터를 각각의 필드(변수)에 직접적으로 주입
        // @RequestBody는
        // UserVo UserDTO 상관없이 setter 메소드의 유무와 관계없이 변수에 값을 넣을 수 잇다.
        return "이름: "+ userVO.getName() + ", 나이: "+ userVO.getAge();
    }

    // 실습 3
    @PostMapping("/info2")
    @ResponseBody
    public String infoResponse2(@RequestBody Prac prac){
        return prac.getName() + " 회원가입 성공!";
    }

}




