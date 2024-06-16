package study_jwt.security_login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study_jwt.security_login.dto.MemberDTO;
import study_jwt.security_login.entity.MemberEntity;
import study_jwt.security_login.repository.MemberRepository;

@Service // 스프링으로 관리하는 객체(=스프링 빈)
@RequiredArgsConstructor
public class MemberService {
    // 생성자 주입(service -> repository)
    private final MemberRepository memberRepository;

    // controller와 service에서는 DTO 객체를 받아옴
    public void save(MemberDTO memberDTO) { // save 메서드
        // 1. dto -> entity 변환
        // 2. repository의 save 메서드 호출
        // tip! 맥은 option + enter 하면 좌변을 자동으로 만들어주는 단축키 기능이 있음
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity); // jpa가 제공 해주는 메서드
        // repository의 save 메서드 호출(jpa를 사용/ 조건은 entity 객체를 넘겨줘야 함)
    }
}
