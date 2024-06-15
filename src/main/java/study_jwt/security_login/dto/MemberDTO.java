package study_jwt.security_login.dto;

import lombok.*;

// 원래 Getter, Setter를 만들어줘야 하지만 Getter, Setter 어노테이션을 붙이면 자동으로 만들어 준다.
@Getter
@Setter
// 기본 생성자를 자동으로 만들어 줌
@NoArgsConstructor
// 필드를 모두 다 매개 변수로 하는 생성자를 만들어 줌
@AllArgsConstructor
// dto 객체가 가지고 있는 어떤 필드 값을 출력할 때 사용
@ToString
public class MemberDTO { // 회원 정보에 필요한 내용들을 필드로 정의하고 그 필드에 대해서 모두 private로 작성한다.
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
}
