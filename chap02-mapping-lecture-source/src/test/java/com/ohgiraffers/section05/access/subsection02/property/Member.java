package com.ohgiraffers.section05.access.subsection02.property;

import jakarta.persistence.*;

//@Entity(name = "member_section05_sub02")
@Access(AccessType.PROPERTY)
/*
* 클래스레벨에 @Access(AccessType.PROPERTY)를 선언할때
* @Id 어노테이션 필드에 있다면 엔티티를 생성하지 못한다
* */
public class Member {

    @Column(name ="member_no")
    private int memberNo;

    @Column(name ="member_id")
    private String memberId;

    @Column(name ="member_pwd")
    private String memberPwd;

    @Column(name ="nickname")
    private String nickname;

    @Id
    public int getMemberNo() {
        return memberNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMemberPwd() {
        return memberPwd;
    }

    public String getNickname() {
        System.out.println("💛💛getNickname()메소드 확인");
        return nickname +"님";
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
