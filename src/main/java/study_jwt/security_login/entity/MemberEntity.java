package study_jwt.security_login.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "member_table")
public class MemberEntity { // table 역할을 한다.
    // table의 이름을 정함 -> 정의 한대로 컬럼이 만들어짐 -> 스프링 데이터 jpa가 데이터베이스 테이블을 알아서 db에다가 정해줌
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(unique = true) // unique 제약 조건 추가
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;
}
