package com.ohgiraffers.section05.groupfuntion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GroupFuntionTests {

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

    /*
    *그룹함수
    * jpql에서 사용하는 그룹함수랑, 기존 sql에서 사용하는 그룹함수는차이가 없다
    * count, sum, avg, max, min
    *
    * 주의사항
    * 1 그룹함수의 반환타입은 결과 값이 정수면 long, 실수면double를 반환한다
    * 2. 값이 없느 상태에서 count를 제오한 그룹함수는 null이 되고 count는 0이된다
    * ->null로 받은 상태에서는 long또는 double같은 기본자료형을 받게 되면
    * 언박싱 과정에서 npe가 발생한다
    * 3. 그룹함수의 반환자료형은 long or double형이기때문에 having절에서
    * 그룹함수 결과 값과 비교하기 위한 파라미터타입은 long or double로 해야한다
    *
    * ->받을 떄 레퍼타입을 써라
    * */

    @Test
    @DisplayName("특정 카테고리의 등록된 메뉴수 조회")
    void test1(){

        //given
        int categoryCodeParameter =4;

        //when
        String jpql = """
                select count(m.menuPrice)
                from menu_section05 m 
                where m.categoryCode = :categoryCode
                """;
        long countOfMenu = entityManager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categoryCodeParameter)
                .getSingleResult();

        //then
        assertTrue(countOfMenu >= 0);
        System.out.println("countOfMenu = " + countOfMenu);;

    }

    @Test
    @DisplayName("count를 제외한 다른 그룹 함수의 조회 결과가 없는 경우 테스트")
    void test2(){

        //given
        int categoryCodeParameter =4;

        //when
        String jpql = """
                select sum(m.menuPrice)
                from menu_section05 m 
                where m.categoryCode = :categoryCode
                """;
        Long countOfMenu = entityManager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categoryCodeParameter)
                .getSingleResult();

        //then
//        assertTrue(countOfMenu >= 0);
//        System.out.println("countOfMenu = " + countOfMenu);;

//        assertThrows(NullPointerException.class, () ->{
//            long sumOfMenu = entityManager.createQuery(jpql, Long.class)
//                    .setParameter("categoryCode", categoryCodeParameter)
//                    .getSingleResult();
//        });

        assertDoesNotThrow(() ->{
            long sumOfMenu = entityManager.createQuery(jpql, Long.class)
                    .setParameter("categoryCode", categoryCodeParameter)
                    .getSingleResult();
        });

    }

    @Test
    @DisplayName("groupby절과 having절을 사용한 조회 테스트")
    void test3(){

        //given
        long minPrice =5000L; //long타입 표시
        //그룹함수의 반환타입은 long이므로 비교를 위한 파라미터도 Long타입을 사용해야한다

        //when
        String jpql = """
                select m.categoryCode, sum(m.menuPrice)
                from menu_section05 m
                group by m.categoryCode
                having sum(m.menuPrice) >= :minPrice
                """;
        List<Object[]> sumPriceOfCategoryList
                = entityManager.createQuery(jpql, Object[].class)
                .setParameter("minPrice", minPrice)
                .getResultList();

        //then
        assertNotNull(sumPriceOfCategoryList);
        sumPriceOfCategoryList.forEach(row ->{
            Arrays.stream(row).forEach(System.out::println);
        });

    }



}
