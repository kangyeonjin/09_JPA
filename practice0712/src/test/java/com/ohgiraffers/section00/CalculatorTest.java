package com.ohgiraffers.section00;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setup(){
        calculator = new Calculator();
        System.out.println("테스트를 시작합니다");
    }

    @AfterEach
    void tearDown(){
        System.out.println("테스트 끝");
    }

    @DisplayName("덧셈테스트")
    @Test
    void testAddition(){
        int a =5;
        int b =3;

        int result = calculator.add(a, b);

        assertEquals(8, result);

    }


}