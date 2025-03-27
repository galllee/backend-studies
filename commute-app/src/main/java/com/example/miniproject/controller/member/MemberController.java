package com.example.miniproject.controller.member;

import com.example.miniproject.domain.member.Member;
import com.example.miniproject.dto.request.member.CreateMemberRequest;
import com.example.miniproject.dto.response.member.GetMemberResponse;
import com.example.miniproject.service.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/api/v1/member")
    public void saveMember(@RequestBody CreateMemberRequest request) {
        memberService.saveMember(request);
    }

    @GetMapping("/api/v1/member")
    public List<GetMemberResponse> getAllMembers() {
        return memberService.getAllMembers();
    }
}
