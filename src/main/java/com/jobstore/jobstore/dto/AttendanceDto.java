package com.jobstore.jobstore.dto;

import com.jobstore.jobstore.entity.Attendance;
import com.jobstore.jobstore.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {
    private long attendid;
    private String memberid;
    private String storeid;
    private LocalDateTime start;
    private LocalDateTime end;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime gowork;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime leavework;
    private long wage;
    //private String registertime;
    //private LocalDateTime registertime;



    public Attendance toEntity(){
        return Attendance.builder()
                .attendid(this.attendid)
                .start(this.start)
                .end(this.end)
                .gowork(this.gowork)
                .leavework(this.leavework)
                .wage(this.wage)
              //  .registertime(this.registertime)
                .build();
    }
}
