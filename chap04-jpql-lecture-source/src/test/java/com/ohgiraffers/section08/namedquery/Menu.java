package com.ohgiraffers.section08.namedquery;

import jakarta.persistence.*;
import lombok.*;



@Entity(name ="menu_section08")
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
//@namedquery 정적쿼리를 정의하는데 사용되는 어노테이션
//jpql을 미리 엔티티 클래스에 정의를 해둠으로서 재사용성을 높일 수 있다.
@NamedQueries({
        @NamedQuery(
                name = "section08.namedquery.Menu.findAll",  //쿼리이름
                query= """
                select m from menu_section08 m                
                """   //쿼리정의
        ),
        //findByMenuName -> 메뉴이름으로 메뉴 찾아오는 jpql
        // 메뉴 이름을 파라미터로 받고, 일치하는 메뉴 객체를 반환하는 jpql
        @NamedQuery(
                name = "section08.namedquery.Menu.findByMenuName",  //쿼리이름
                query= """
                select m from menu_section08 m  where m.menuName = :menuName               
                """   //쿼리정의
        ),
        //밥이들어간 메뉴이름을 전부 조회하는 jpql
        @NamedQuery(
                name = "section08.namedquery.Menu.findByMenuNameBOB",  //쿼리이름
//                select m from menu_section08 m  where m.menuName like "%밥%"
        query= """
                select m from menu_section08 m  where m.menuName like concat('%', :menuName, '%')                           
                """   //쿼리정의
        )
})

public class Menu {

    @Id
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;


}
