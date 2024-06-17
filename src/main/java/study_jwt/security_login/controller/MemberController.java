package study_jwt.security_login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study_jwt.security_login.dto.MemberDTO;
import study_jwt.security_login.service.MemberService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입 방식(객체를 스프링 빈으로 등록할 때 자동적으로 서비스 클래스에 대한 객체를 주입을 받는다. -> controller가 service의 메서드나 필드들을 사용할 수 있게 되는 권한이 생김)
    // controller -> service
    private final MemberService memberService; // memberService 필드를 매개변수로 하는 controller 생성자를 만들어준다.

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
//        MemberService memberService = new MemberService(); // controller에서 service로 넘길 때 호출하면서 메서드 호출 시 이 방식을 사용하지 않는다.
          memberService.save(memberDTO);
        return "login"; // 회원 가입이 완료 되면 로그인 페이지로 가도록 할 것이다.
    }

    // 회원 로그인 정보 가져오기
    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }

    // 회원 로그인 입력
    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }

    // 회원 목록
    @GetMapping("/member/")
    // findAll : DB의 모든 값을 끌어온다.
    // Model은 스프링에서 실어나르는 역할을 해주는 개체
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll(); // 여러 개를 가져와야 하므로 List 사용
        // model 객체에 List를 담아서 addAttribute 메서드에 작업해본다.
        // 어떠한 html로 가져갈 데이터가 있다면 model 사용
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }

    // 회원 조회
    @GetMapping("/member/{id}")
    // /member/{id} 이 경로 상에 있는 값을 rest API 같은 주소 체계를 쓸 땐 @PathVariable를 사용
    // @PathVariable -> 경로 상에 있는 값을 가져올 떈 @PathVariable 어노테이션을 사용
    // model 객체는 화면 상에 가져가야 하므로 필요
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id); // 한 명이므로 dto
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    //회원 수정(db 내용 조회)
    @GetMapping("/member/update")
    // 내 정보를 세션에 담아 두었기 때문에 세션 안에 이메일, 비번, 이름 값을 가져와서 나의 전체 정보를 db로 부터 가져와 model에 담아서 update.html에 가져가는 과정
    public String myEmail(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("loginEmail"); // object인데 강제 형변환 해서 우측에 (String)을 넣어준다.
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO); // 1. service에서 updateMember에 내용을 담고
        return "update"; // 2. update.html로 간다.
    }

    // 회원 수정(db 내용 수정)
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        // model에 담아서 return 값으로 넘어감 하지만 여기서는 그 과정이 없다.
        // 다른 컨트롤러를 리다이렉트해서 그 값을 넘겨줌
        // 내 정보를 수정하고 나서 수정이 완료된 나의 상세 페이지를 띄어줌
        return "redirect:/member/" + memberDTO.getId();
    }
}
