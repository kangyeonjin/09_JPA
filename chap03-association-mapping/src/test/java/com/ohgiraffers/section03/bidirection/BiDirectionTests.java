package com.ohgiraffers.section03.bidirection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BiDirectionTests {

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
    * 데이터베이스테이블은 외래키하나로 양방향 조회가 가능하지만
    * 객체는 서로다른 두 단방향 참조를 합쳐서 양방향이라고한다
    * 양방향 연관관계는 항상 설정하지 않고
    * 반대방향으로 접근하여 객체그래프탐색을 할일이 많을 경우에만 사용한다
    * 양방향 매핑에서 연관관계의주인은 외래키를 가지고있는 엔티티이다
    * */

    @Test
    @DisplayName("양방향 연관관계 매핑조회테스트")
    public void test1(){
        //given
        int menuCode =10;
        int categoryCode=10;

        //when
        //manyToOne 연관관계는 처음 조회시부터 조인한 결과를 가져온다
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        //oneToMany연관관계는 필요시 연관관계 엔티티를 조회하는 쿼리를 다시 실행한다.
        Category foundCategory = entityManager.find(Category.class, categoryCode);

        //then
        //tostring() 오버라이딩 또는 lombok을 사용하면 양방향 연관관계에서는 재귀 호출이 일어나기 떄문에
        //stackoverflow발생
        System.out.println(foundMenu);
        System.out.println(foundCategory);

        for(Menu menu : foundCategory.getMenuList()){
            System.out.println(menu);
        }

    }

    @Test
    @DisplayName("양방향 연관관계 주인객체를 이용한 삽입테스트")
    public void test2(){
        //given
        Menu menu = new Menu();
        menu.setMenuCode(123);
        menu.setMenuName("연관관계주인메뉴");
        menu.setMenuPrice(10000);
        menu.setOrderableStatus("Y");

        menu.setCategory(entityManager.find(Category.class,4));

        //when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(menu);
        transaction.commit();

        //then
        Menu foundMenu = entityManager.find(Menu.class, menu.getMenuCode());
        assertEquals(menu.getMenuCode(), foundMenu.getMenuCode());
        System.out.println(foundMenu);

    }

    @Test
    @DisplayName("양방향 연관관계 주인이 아닌 객체를 이용한 삽입테스트")
    public void test3(){
        //given
        Category category = new Category();
        category.setCategoryCode(1004);
        category.setCategoryName("양방향 카테고리");
        category.setRefCategoryCode(null);

        //when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(category);
        transaction.commit();

        //then
        Category foundCategory = entityManager.find(Category.class, category.getCategoryCode());
        assertEquals(category.getCategoryCode(),foundCategory.getCategoryCode());
        System.out.println(foundCategory);

    }

}

