package com.ohgiraffers.section07.subquery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SubQueryTests {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    //jpq도 일반 sql처럼서브쿼리를 지원한다
    //select, from절에서는 사용할수없고, wherr, having절에서만 사용이 가능하다

    @Test
    @DisplayName("서브쿼리를 이용한 메뉴 조회 테스트")
    void test1(){

        //given
        String categoryNameParameter = "한식";

        //when
        //서브쿼리 :  한식이름에 맞는 카테고리를 찾고,
        //외부쿼리 : 서브뭐리에서 조회된 카테고리에 맞는 메뉴들을 조회
        String jpql = """
                    select
                    m
                    from
                    menu_section07 m 
                    where m.categoryCode 
                    =(select c.categoryCode from category_section07 c where c.categoryName =:categoryName)           
                """;

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter("categoryName", categoryNameParameter)
                .getResultList();

        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);

    }



}
