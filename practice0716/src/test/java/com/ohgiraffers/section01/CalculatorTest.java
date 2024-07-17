package com.ohgiraffers.section01;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp(){
        calculator =  new Calculator();
        System.out.println("BeforeEach :테스트시작");
    }

    @AfterEach
    void tearDown(){
        System.out.println("aftereach :테스트끝");
    }

    @Test
    @DisplayName("덧셈테스트")
    void testAddition(){
        //given
        int a =5;
        int b= 3;
        //when
        int result = calculator.add(a, b);
        //then
        assertEquals(8, result);

    }

    @Test
    @DisplayName("뺄셈테스트")
    void testSubtraction(){
        //given
        int a =5;
        int b =3;
        //when
        int result = calculator.subtract(a, b);

        //then
        assertEquals(2,result);

    }

}
