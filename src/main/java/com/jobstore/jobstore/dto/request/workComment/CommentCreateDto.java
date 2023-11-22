package com.jobstore.jobstore.dto.request.workComment;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDto {

    private String memberid;
    private long workid;
    private String comment;
    private String name;
}
