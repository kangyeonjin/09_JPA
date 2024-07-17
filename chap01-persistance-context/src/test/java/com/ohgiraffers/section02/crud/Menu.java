package com.ohgiraffers.section02.crud;


import jakarta.persistence.*;
import lombok.*;

@Entity(name="select02_menu") //엔티티 객체로 만들기 위한 어노테이션
// (다른 패키지에 동일 이름의 클래스가 존재하면  name지정필수)
@Table(name="tbl_menu") //데이터베이스에 매핑될 테이블 이름설정
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter //세터의 사용을 지양한다.
@ToString
public class Menu {

    @Id //pk에 해당되는 속성에 지정
    @Column(name="menu_code") //데이터베이스에 대응되는 컬럼명지정
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키값을 데이터베이스에서 생성하도록 지정
    private int menuCode;

    @Column(name="menu_name")
    private String menuName;

    @Column(name="menu_price")
    private int menuPrice;

    @Column(name="category_code")
    private int categoryCode;

    @Column(name="orderable_status")
    private String orderableStatus;

}
