package study_jwt.security_login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study_jwt.security_login.dto.MemberDTO;
import study_jwt.security_login.entity.MemberEntity;
import study_jwt.security_login.repository.MemberRepository;

import java.util.Optional;

@Service // 스프링으로 관리하는 객체(=스프링 빈)
@RequiredArgsConstructor
public class MemberService {
    // 생성자 주입(service -> repository)
    private final MemberRepository memberRepository;

    // controller와 service에서는 DTO 객체를 받아옴
    // 회원가입
    public void save(MemberDTO memberDTO) { // save 메서드
        // tip! 맥은 option + enter 하면 좌변을 자동으로 만들어주는 단축키 기능이 있음

        // 1. dto -> entity 변환
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);

        // 2. repository의 save 메서드 호출
        // save 메서드를 호출해서 스프링 데이터 jpa가 쿼리를 만들어 준다.(insert query)
        memberRepository.save(memberEntity);

        // repository의 save 메서드 호출(jpa를 사용/ 조건은 entity 객체를 넘겨줘야 함)
    }

    // 로그인
    public MemberDTO login(MemberDTO memberDTO) {
        // 1. 회원이 입력한 이메일로 DB에서 조회를 함
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()) {
            // 조회 결과가 있다.(해당 이메일을 가진 회원 정보가 있다.)
            MemberEntity memberEntity = byMemberEmail.get();
            // entity 와 DTO 비교해서 있는지 판단
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                // 비밀번호 일치(로그인 성공)
                // entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                // 비밀번호 불일치(로그인 실패)
                return null;
            }
        } else {
            // 조회 결과가 없다.(해당 이메일을 가진 회원 정보가 없다.)
            return null;
        }
        // 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단

    }
}
