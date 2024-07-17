package com.ohgiraffers.section00;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculratorTest {

    private Calculrator calculrator;

    @BeforeEach //각테스트 메서드가 실행되기전에 실행되어야 하는 코드나 초기화작업을 할때 사용
    void setUp() {
        calculrator = new Calculrator();
        System.out.println("테스트를 시작합니다.");
    }

    @AfterEach  //각 테스트 메서드가 실행된 후에 실행되어야 하는 후속 작업 및 정리작업할떄 사용
    void tearDown(){
        System.out.println("테스트 끝");
    }

    @DisplayName("덧셈테스트") //테스트 결과에 대한 이름을 지정할수있다
    @Test //테스트 메소드를 정의할떄 사용하는 어노테이션 , 테스트 메소드로 등록되고,
    //코드검증 및 테스트 결과확인을 하게 해준다
    void testAddition(){

        //given //사전조건
        int a=5;
        int b=3;
        //when//테스트할 시점
        int result = calculrator.add(a, b);

        //then//우리가 기대하는 결과
        assertEquals(8,result);

    }

    @DisplayName("뺄셈테스트")
    @Test
    void testSubtraction(){

        //given //사전조건
        int a=5;
        int b=3;
        //when//테스트할 시점
        int result = calculrator.subtract(a, b);

        //then//우리가 기대하는 결과
        assertEquals(2,result);

    }




}