package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService=memberService;
    }

    // get/postMapping(url) url이 같지만, get/post 방법이 다르기에 구분되어 선택됨
    // 'url 조회 시' get
    @GetMapping("members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    // 'url에서 정보 받을 때' post
    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member=new Member();
        member.setName(form.getName());

        // MemberService에 정의된 join 메서드 (repository에 해당 member 객체 추가)
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members=memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
