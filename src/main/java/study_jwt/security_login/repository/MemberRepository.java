package study_jwt.security_login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study_jwt.security_login.entity.MemberEntity;

import java.util.Optional;

// JpaRepository에서 MemberRepository을 상속 받는다.
// MemberEntity은 Entity 클래스의 이름을 쓰는 것(MemberEntity 라고 써서 MemberEntity 라고 하는 것)이고, Long은 @Id가 붙은 것에 pk의 타입을 말한다.
// Repository가 DB와 연결을 해주는 작업을 한다.(최종 요소) -> 반드시 entity 객체로 넘겨 줘야 한다.
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // 이메일로 회원 정보 조회
    // query : select * from member_table where member_email=?
    // Optional -> null 방지를 해주는 역할
    // 주고 받는 값은 MemberEntity
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
