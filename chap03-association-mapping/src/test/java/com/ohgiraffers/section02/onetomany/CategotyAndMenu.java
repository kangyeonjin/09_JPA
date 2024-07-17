package com.ohgiraffers.section02.onetomany;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name ="category_and_menu")
@Table(name ="tbl_category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategotyAndMenu {
    /*
    * fetch
    * @onetomany(fetch =fatchtype.lazy)
    * @manytomany --지연로딩이 default
    * @manytoone(fetch = fetchtype.eager)
    * @onetoone
    *
    * */

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    @JoinColumn(name ="category_code")
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Menu> menuList;

}
