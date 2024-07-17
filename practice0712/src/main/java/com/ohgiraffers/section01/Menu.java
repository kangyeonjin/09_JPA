package com.ohgiraffers.section01;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Menu {

    private int menuCode;
    private String menuName;
    private int menuPrice;
    private int categorycode;
    private String orderableStatus;

}

