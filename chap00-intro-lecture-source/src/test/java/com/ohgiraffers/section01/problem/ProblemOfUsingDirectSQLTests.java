package com.ohgiraffers.section01.problem;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemOfUsingDirectSQLTests {

    private Connection con;

    @BeforeEach
    void setConnection() throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/menudb";
        String user = "ohgiraffers";
        String password = "ohgiraffers";

        Class.forName(driver);

        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
    }

    @AfterEach
    void closeConnection() throws SQLException {
        con.rollback();
        con.close();
    }

    /*
    * jdbc api를 이용해 직접sql을 다룰때 발생할수있는 문제점
    * 1. 데이터변환, sql작성, jdbc api코드등의 중복작성
    * 2. sql에 의존하여 개발
    * 3. 패러다밍 불일치(상속, 연관관계, 객체 그래프 탐색)
    * 4. 동일성 보장문제
    * */

    //1. 데이터변환, sql작성등의 중복 작성문제
    @DisplayName("직접 sql을 작성하여 메뉴를 조회할떄 발생하는 문제 확인")
    @Test
    void testDirectSelectSQL() throws SQLException{

        //given
        String query
                = "select menu_code, menu_name, menu_price, category_code"
                + "orderable_status from tbl_menu";

        //when
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        List<Menu> menuList = new ArrayList<>();
        while (rset.next()){
            Menu menu = new Menu();
            menu.setMenuCode(rset.getInt("menu_code"));
            menu.setMenuName(rset.getString("menu_name"));
            menu.setMenuPrice(rset.getInt("menu_price"));
            menu.setCategoryCode(rset.getInt("category_code"));
            menu.setOrderableStatus(rset.getString("orderable_status"));
            menuList.add(menu);
        }

        //then
        Assertions.assertNotNull(menuList); //null이 아니면 pass

        //menulist에 있는 menu전체 출력
//        menuList.forEach(menu -> System.out::println(menu));
        menuList.forEach(System.out::println);

        rset.close();
        stmt.close();

    }
    @DisplayName("직접 SQL을 작성하여 신규메뉴를 추가할떄 발생하는 문제확인")
    @Test
    void testDirectInsertSql() throws SQLException{

        //given
        Menu menu = new Menu();
        menu.setMenuName("민트초코짜장면");
        menu.setMenuPrice(30000);
        menu.setCategoryCode(1);
        menu.setOrderableStatus("Y");

        String query = "insert into tbl_menu(menu_name, menu_price, category_code,"
                +"orderable_status)values(?,?,?,?)";

        //when
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, menu.getMenuName());

        int result = pstmt.executeUpdate();

        //then
        Assertions.assertEquals(1,result);
        pstmt.close();

    }

    /*
    * - 데이터베이스 테이블에 맞춘 객체 모델
    * public class Menu {
      private int menuCode;
      private String menuName;
      private int menuPrice;
      private int categoryCode;
      private String orderableStat
    }
    public class Category {
      private int categoryCode;
      private String categoryName;
    }
    - 객체 지향 언어에 맞춘 객체 모델
    public class Menu {
      private int menuCode;
      private String menuName;
      private int menuPrice;
      private Category category;
      private String orderableStat
    }
    public class Category {
      private int categoryCode;
      private String categoryName;
    }
    **/

    /*데이터베이스
    * Menu menu = new menu();
    * category category = new category();
    * menu.setcategory(category)
    * menu.getcategory().getcategoryName()*/
    //관계 설정이 되어있지 않으면 nullpointexception발생가능

    /*JPA
    * Menu menu = entityManager.find(Menu.class, menuCode);
    * menu.getCategory().getcategoryName(); //NPE가 발생하지않음*/

    @DisplayName("조회한 두개의 행을 담은 객체의 동일성 비교 테스트")
    @Test
    void testEquals() throws SQLException {
        //given
        String query = "select menu_code, menu_name,"
                + "from tbl_menu where menu_code=12";
        //when
        Statement stmt1 = con.createStatement();
        ResultSet rset1 = stmt1.executeQuery(query);

        Menu menu1 = null;

        while (rset1.next()){
            menu1 = new Menu();
            menu1.setMenuCode(rset1.getInt("menu_code"));
            menu1.setMenuName(rset1.getString("menu_Name"));
            menu1.setMenuPrice(rset1.getInt("menu_Price"));

        }

        Statement stmt2 = con.createStatement();
        ResultSet rset2 = stmt2.executeQuery(query);

        Menu menu2 = null;

        while (rset2.next()){
            menu2 = new Menu();
            menu2.setMenuCode(rset2.getInt("menu_code"));
            menu2.setMenuName(rset2.getString("menu_Name"));
            menu2.setMenuPrice(rset2.getInt("menu_Price"));

        }

        rset1.close();
        rset2.close();
        stmt1.close();
        stmt2.close();

        //then
        Assertions.assertEquals(menu1.getMenuCode(),menu2.getMenuCode());
        Assertions.assertFalse(menu1 == menu2);

        /*
        * 영속성 컨텍스트는 하나의 트랜젝션에 한해 사용된다.
        *
        * Menu menu1 = entityManager.find(Menu.class, 12)
        * Menu menu2 = entityManager.find(Menu.class, 12)
        *
        * menu1 == menu2
        * */




    }

}
