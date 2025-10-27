package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.service.MemberService;
import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 스프링 전체 컨텍스트 실행 (통합 테스트용)
@Transactional   // 테스트로 DB에 데이터 넣은 후, @test붙은 테스트 끝나면 롤백(넣었던 데이터 반영안되고 지워짐)해줌
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        // Given (회원 객체 생성)
        Member member = new Member();
        member.setName("hello");

        // When (회원 가입 실행)
        Long saveId = memberService.join(member);

        // Then (가입된 회원 확인)
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // Given (이름이 같은 회원 2명 생성)
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // When (첫 번째 회원 가입 후 두 번째 회원 가입 시도)
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2)); // 예외 발생 예상

        // Then (예외 메시지 검증)
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}
