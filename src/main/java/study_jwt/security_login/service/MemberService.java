package study_jwt.security_login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study_jwt.security_login.dto.MemberDTO;
import study_jwt.security_login.entity.MemberEntity;
import study_jwt.security_login.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
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

    // 회원 목록
    public List<MemberDTO> findAll() {
        // repository와 연관된 것은 반드시 entity이다.
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        // entity가 여러개 담긴 객체 -> dto가 여러개 담긴 객체로 반환 해줘야 한다.
        List<MemberDTO> memberDTOList = new ArrayList<>(); // 담아 갈 객체 만들어줌
        // entity 객체를 하나 하나 꺼내서 dto에 하나 하나 넣어 줘야 한다.
        for (MemberEntity memberEntity : memberEntityList) { // 2. memberEntity 객체를 for each로 접근한다.
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity)); // 1. memberDTOList는 dto객체를 담기위한 리스트 / 3. toMemberDTO 하나를 dto로 변환 시키고
            // 위 한 문장과 밑에 두 줄은 같은 것이다. 둘 중에 하나만 사용 해도 무방
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity); // 4. dto 변환된 객체를
//            memberDTOList.add(memberDTO); // 5. List에 담는다.
        }
        return memberDTOList;
    }

    // 회원 조회
    public MemberDTO findById(Long id) {
        // 이메일로 회원 정보 조회
        // query 문 : select * from member_table where member_email=?
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO;
            // 위에 주석처리한 3줄을 밑에 한 줄로 표현함
            return MemberDTO.toMemberDTO(optionalMemberEntity.get()); // 1. optionalMemberEntity.get()에서 optional로 감싸져 있는 것을 열고 toMemberDTO에 MemberDTO 메서드를 보내서 dto로 변환을 한 결과를 컨트롤러로 리턴을 준다.
        } else {
            return null;
        }
    }

    // 회원 수정(db 내용 조회)
    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()) {
            // 회원 조회 부분과 동일
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    // 회원 수정(db 내용 수정)
    public void update(MemberDTO memberDTO) {
        // id가 없으면 insert query를 실행해줌 -> db에 있는 id가 entity 객체가 넘어오면 업데이트 쿼리를 날려줌
        // update가 없어서 save 사용
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO)); // update를 하기 위해서는 entity에 setId를 추가해줘야 한다, 그렇지 않으면 insert 문으로 작동
    }

    // 회원 삭제
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
