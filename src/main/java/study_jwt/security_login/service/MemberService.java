package study_jwt.security_login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study_jwt.security_login.dto.MemberDTO;
import study_jwt.security_login.repository.MemberRepository;

@Service // 스프링으로 관리하는 객체(=스프링 빈)
@RequiredArgsConstructor
public class MemberService {
    // 생성자 주입(service -> repository)
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) { // save 메서드

    }
}
