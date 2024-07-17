package com.ohgiraffers.section01.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Table(name="tbl_member_section01")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {
    @Id
    @Column(name = "member_no")
    private int memberNo;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pwd")
    private String memberPwd;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "enroll_date")
    private LocalDate enrollDate;

    @Column(name = "status")
    private String status;

}
