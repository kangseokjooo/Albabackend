package com.jobstore.jobstore.Controller;

import com.jobstore.jobstore.dto.MemberDto;
import com.jobstore.jobstore.entity.Member;
import com.jobstore.jobstore.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@Tag(name = "Member", description = "Member CRUD")
public class MemberController {
    @Autowired
    MemberService memberService;
    //회원가입
    @GetMapping("/join")
    public String joinMember(Model model){
        model.addAttribute("memberDto",new MemberDto());
        return "join";
    }
    @PostMapping("/join")
    @ResponseBody
    public MemberDto createMember(@RequestBody MemberDto memberDto){
        memberService.join(memberDto);
        System.out.println("컨트롤러");
        return memberDto;
    }
    //로그인
    @GetMapping("/login")
    public String loginMember(Model model){
        // model.addAttribute("loginDto",new loginDto());
        return "login";
    }
    @PostMapping("/all")
    @Operation(summary = "전체유저조회", description = "전체유저정보가 list형식으로 반환됩니다.")
    @ResponseBody
    public List<MemberDto> findAllMember(@RequestBody MemberDto memberDto){
        return memberService.findAllMember();
    }

    @PatchMapping("/update")
    @Operation(summary = "memberid에대한 user정보 업데이트", description = "수정이 된 객체가 반환됩니다")
    @ResponseBody
    public Member updateMember(@RequestBody MemberDto memberDto){
        return memberService.updateMember(memberDto);
    }
    @DeleteMapping("/delete/{memberid}")
    @Operation(summary = "memberid에대한 삭제", description = "memberid삭제여부를 문자열값으로 반환합니다.")
    @ResponseBody
    public String deleteMember(@PathVariable String memberid){
        return memberService.deleteBymemberid(memberid);
    }
}