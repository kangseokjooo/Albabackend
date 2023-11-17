package com.jobstore.jobstore.dto.request;

import com.jobstore.jobstore.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserjoinDto {

    private String memberid;
    private String password;
    private String phonenumber;
    private String name;
    private String role;
    private String memberimg;



    public Member toEntity(String encodedPassword){
        return Member.builder()
                .memberid(this.memberid)
                .password(encodedPassword)
                .phonenumber(this.phonenumber)
                .name(this.name)
                .role("USER").build();
    }
}
