package study_jwt.security_login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study_jwt.security_login.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
}
