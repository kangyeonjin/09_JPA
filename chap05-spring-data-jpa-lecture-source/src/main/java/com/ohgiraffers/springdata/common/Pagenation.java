package com.ohgiraffers.springdata.common;

import org.springframework.data.domain.Page;

public class Pagenation {

    public static PagingButtonInfo getPagingButtonInfo(Page page){
        //현재페이지에대한 정보+1 (숫자체계가 0부터 시작하기때문에 사용자가 보는시점에 맞
        int currentPage = page.getNumber() +1;

        //페이지버튼 기본갯수
        int defaultButtonCount =10;

        //현재시작 페이지를 계산
        int startPage = (int)(Math.ceil((double) currentPage / defaultButtonCount)-1)*defaultButtonCount + 1;

        //끝페이지계산
        int endPage = startPage + defaultButtonCount - 1;

        //실제총페이지가 endpage보다 작으면 endpage를 총페이지로
        if(page.getTotalPages() < endPage){
            endPage = page.getTotalPages();
        }
        //토탈페이지가 0이거나 끝페이가 0이면 끝페이지를 시작페이지로 설정
        if(page.getTotalPages() == 0 && endPage ==0){
            endPage = startPage;
        }
        return new PagingButtonInfo(currentPage, startPage, endPage);

    }

}
