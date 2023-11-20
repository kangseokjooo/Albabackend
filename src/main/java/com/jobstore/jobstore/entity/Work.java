package com.jobstore.jobstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "work")
@Getter
@Setter
public class Work {
    @Id
    private long workid;
    @Column(length = 255,nullable = false)
    private String title;
    @Column(length = 255,nullable = false)
    private String date;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "memberid",referencedColumnName = "memberid"),
    })
    private Member member;

    @OneToOne(mappedBy = "work")
    private Contents contents;
}
