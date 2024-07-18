package com.ohgiraffers.practince0718.common;

import org.springframework.data.domain.Page;
//페이징버튼 표시하기
public class Pagenation {

    public static PagingButtonInfo getPagingButtonInfo(Page page){
        //0부터 시작하는 페이지번호 반환하므로 1을 더해 실제 페이지 번호를 얻음
        int currentPage = page.getNumber()+1;

        //기본표시페이징버튼수를 10으로 설정
        int defaultButtonCount =10;

        //현재페이지번호를 기준으로 시작페이지번호를 계산함
        //현재페이지 번호를 기본버튼수로 나눈뒤 올림처리하여 해당 블록의 시작페이지번호를 계산함
        int startPage = (int) (Math.ceil((double) currentPage / defaultButtonCount) - 1) * defaultButtonCount + 1;

        //시작페이지 번호부터 기본버튼수를 더해 종료페이지 번호를 계산함
        int endPage = startPage + defaultButtonCount - 1;

        //종료페이지 번호가 총페이지수를 초과하지 않도록 조정함
        //총페이지수가 종료페이지번호보다 작으면 종료페이지번호를 총페이지수로 설정
        if(page.getTotalPages() < endPage){
            endPage = page.getTotalPages();
        }

        //총 페이지수와 종료페이지 번호가 0일경우 종료페이지 번호를 시작페이지번호로 설정함
        //페이지가 전혀 없을떄를 대비한것
        if(page.getTotalPages()==0 && endPage==0){
            endPage = startPage;
        }
        return new PagingButtonInfo(currentPage, startPage, endPage);
    }

}
