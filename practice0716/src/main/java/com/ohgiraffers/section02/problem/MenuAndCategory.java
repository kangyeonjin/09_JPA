package com.ohgiraffers.section02.problem;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MenuAndCategory {

    private int menuCode;
    private String menuName;
    private int menuPrice;
    private Category category;
    private String orderableStatus;

}
