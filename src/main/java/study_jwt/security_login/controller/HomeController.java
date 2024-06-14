package study_jwt.security_login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 기본 페이지 요청 메서드
    @GetMapping("/")
        public String index() { // ("/") 주소에 들어가면 메서드 호출 가능
            return "index"; // => templates 폴더의 index.html을 찾아감
    }
}
