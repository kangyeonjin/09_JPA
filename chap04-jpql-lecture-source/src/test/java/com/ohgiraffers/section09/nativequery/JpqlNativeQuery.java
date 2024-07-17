package com.ohgiraffers.section09.nativequery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JpqlNativeQuery {
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
    * native query
    * -sql쿼리를 그대로 사용하는것을 의미한다
    * -orm의 기능을 이용하고, sql쿼리도 활용할수잇어서 더욱 강력한 데이터베이스 접근가능
    * -복잡한 쿼리, 특정 데이터 베이스 에서만 사용가능한 기능을 사용할때 사용한다
    *
    * 네이티브 쿼리 api종류3가지
    * 1. 결과타입 정의(엔티티클래스만 지정가능)
    * pubilc query createNativeQuery(String sqlstring, class resultclass)
    * 2. 결과 타입을 정의할수없을떄
    * public query createNativeQuery(string sqlstring)
    * 3. 결과매핑
    * public query createNativequery(string sqlstring, string resultsetmapping)
    * */

    @Test
    @DisplayName("결과타입을 지정해서 Native Query 사용하기")
    void test1(){

        //given
        Long menuCode = 15L;

        //when
        String jpql = """
                select
                *
                from 
                tbl_menu
                where
                menu_code=?
                """;
        Query query = entityManager.createNativeQuery(jpql, Menu.class)
                .setParameter(1, menuCode);

        Menu menu = (Menu) query.getSingleResult();
        System.out.println("menu = " + menu);

        Menu menu2 = entityManager.find(Menu.class, menuCode);
        System.out.println("menu2 = " + menu2);
        //then
        assertNotNull(menu);

    }
    @Test
    @DisplayName("결과타입을 지정하지 않고 native query사용하기")
    void test2(){

        //given
        Long menuCode = 15L;

        //when
        String sql = """
                select
                menu_code,
                menu_name,
                (select category_name from tbl_category where category_code = m.category_code)
                from tbl_menu m
                where
                menu_code = ?                
                """;

        Query query = entityManager.createNativeQuery(sql)
                        .setParameter(1, menuCode);
        Object[] row = (Object[]) query.getSingleResult();
        for(Object o : row){
            System.out.println(o);
        }

        //then
        //row의 크기가 3이면 pass
        assertThat(row).hasSize(3);

    }

    @Test
    @DisplayName("@NamedNativeQuery 사용하기 -엔티티")
    void test3(){
        //given
        Long menuCode = 15L;

        //when
        Query query = entityManager.createNamedQuery("section09.Menu.findByMenuCode", Menu.class)
                .setParameter(1, menuCode);
        Menu menu = (Menu) query.getSingleResult();
        System.out.println("menu = " + menu);

        //then
        assertNotNull(menu);

    }

}
