package com.ohgiraffers.section01;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MenuAndCategory {

    private int menuCode;
    private String menuName;
    private int menuPrice;
    private Category category;
    private String orderableStatus;

}
