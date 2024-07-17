package com.ohgiraffers.section06.join;

import jakarta.persistence.*;
import lombok.*;

@Entity(name ="menu_section06")
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Menu {

    @Id
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @JoinColumn(name = "category_code")
    @ManyToOne
    private Category category;

    @Column(name = "orderable_status")
    private String orderableStatus;


}
