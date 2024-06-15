package study_jwt.security_login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study_jwt.security_login.dto.MemberDTO;

@Controller
public class MemberController {

    // 회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    // 회원가입 새로운 내용 입력 저장(이메일, 비밀번호, 이름)
    @PostMapping("/member/save")
    // 스프링이 제공하는 어노테이션 : @ModelAttribute는 DTO로 쓰면 내용 다 가지고 온다.
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.save");
        // soutp 하면 3가지 값을 다 볼 수 있다.
        System.out.println("memberDTO = " + memberDTO);
        return "index";
    }
}
