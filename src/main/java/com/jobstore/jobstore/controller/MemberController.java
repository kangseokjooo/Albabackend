package com.jobstore.jobstore.controller;

import com.jobstore.jobstore.dto.request.member.MemberAndStoreDetailsDto;
import com.jobstore.jobstore.dto.MemberDto;
import com.jobstore.jobstore.dto.request.member.DoubleCheckDto;
import com.jobstore.jobstore.dto.request.member.ImageUploadDto;
import com.jobstore.jobstore.dto.response.ResultDto;
import com.jobstore.jobstore.entity.Member;
import com.jobstore.jobstore.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@Controller
@Tag(name = "Member", description = "Member CRUD")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {
    @Autowired
    MemberService memberService;

    //회원가입
//    @GetMapping("/join")
//    public String joinMember(Model model){
//        model.addAttribute("memberDto",new MemberDto());
//        return "join";
//    }
//    @PostMapping("/join")
//    @ResponseBody
//    public MemberDto createMember(@RequestBody MemberDto memberDto){
//        memberService.join(memberDto);
//        System.out.println("컨트롤러");
//        return memberDto;
//    }
    //로그인
//    @GetMapping("/login")
//    public String loginMember(Model model){
//        // model.addAttribute("loginDto",new loginDto());
//        return "login";
//    }

    @PostMapping("/all")
    @Operation(summary = "전체유저조회", description = "전체유저정보가 list형식으로 반환됩니다.")
    @ResponseBody
    public ResponseEntity<ResultDto<List<MemberDto>>> findAllMember(@RequestBody MemberDto memberDto){
        if(memberDto==null){
            return ResponseEntity.ok(ResultDto.of("resultcode","전체유저 조회실패",null));
        }
        return ResponseEntity.ok(ResultDto.of("resultcode","전체유저 조회완료",memberService.findAllMember()));
    }

    @PostMapping("/detail")
    @Operation(summary = "memberid에대한 user,admin정보", description = "memberid에 대한 상세객체가 반환됩니다")
    @ResponseBody
    public ResponseEntity<ResultDto<MemberAndStoreDetailsDto>> findMemberDetails(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "요청파라미터", required = true,
                    content = @Content(schema=@Schema(implementation = DoubleCheckDto.class)))
            @RequestBody DoubleCheckDto doubleCheckDto){
        // System.out.println("asdadadsadsaasdsada"+doubleCheckDto.getMemberid());
        MemberAndStoreDetailsDto memberAndStoreDetailsDto=memberService.findMemberDetails(doubleCheckDto.getMemberid());
        if(memberAndStoreDetailsDto!=null){
            return ResponseEntity.ok(ResultDto.of("resultcode","성공",memberService.findMemberDetails(doubleCheckDto.getMemberid())));
        }else{
            ResponseEntity.ok(ResultDto.of("resultcode","실패",null));
        }
        return null;
    }

    @PatchMapping("/update")
    @Operation(summary = "memberid에대한 user정보 업데이트", description = "수정이 된 객체가 반환됩니다")
    @ResponseBody
    public ResponseEntity<ResultDto<Member>> updateMember(@RequestBody MemberDto memberDto){
        if(memberDto==null){
            return ResponseEntity.ok(ResultDto.of("resultcode","수정 실패",null));
        }
        return ResponseEntity.ok(ResultDto.of("resultcode","수정 완료",memberService.updateMember(memberDto)));
    }
    @DeleteMapping("/delete/{memberid}/{storeid}")
    @Operation(summary = "Admin:memberid와 storeid 에대한 삭제", description = "memberid 삭제여부를 문자열값으로 반환합니다.")
    @ResponseBody
    public ResponseEntity<ResultDto<String>> deleteAdmin(@PathVariable String memberid,@PathVariable long storeid){
        if(memberid==null){
            return ResponseEntity.ok(ResultDto.of("resultcode","삭제 실패",null));
        }
        return ResponseEntity.ok(ResultDto.of("resultcode","AdminUser 삭제완료",memberService.deleteBymemberid(memberid,storeid)));
    }
    @DeleteMapping("/delete/{memberid}")
    @Operation(summary = "User:memberid 에대한 삭제", description = "memberid 삭제여부를 문자열값으로 반환합니다.")
    @ResponseBody
    public ResponseEntity<ResultDto<String>> deleteUser(@PathVariable String memberid){
        if(memberid==null){
            return ResponseEntity.ok(ResultDto.of("resultcode","삭제 실패",null));
        }
        return ResponseEntity.ok(ResultDto.of("resultcode","User 삭제완료",memberService.deleteByUserto_memberid(memberid)));
    }


    /** 이미지 등록 */
    @PostMapping(value = "/member/image/upload", consumes = {"multipart/form-data"})
    @Operation(summary = "User,Admin:대한 이미지 등록 api", description = "이미지 등록 api입니다.")
    public ResponseEntity<ResultDto<Object>> imageUpdate (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "요청파라미터", required = true,
                    content = @Content(
                            schema=@Schema(implementation = ImageUploadDto.class)))
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            ImageUploadDto imageUploadDto

    ) throws IOException {

        String reultURL = memberService.ImageUpdate(multipartFile, imageUploadDto);

        if (reultURL.equals("")) {

            return ResponseEntity.ok(ResultDto.of("resultcode","이미지 등록 실패", null));

        } else {
            HashMap hashMap = new HashMap();
            hashMap.put("imageurl", reultURL);
            return ResponseEntity.ok(ResultDto.of("resultcode","이미지 등록 완료", hashMap));
        }
    }
}
