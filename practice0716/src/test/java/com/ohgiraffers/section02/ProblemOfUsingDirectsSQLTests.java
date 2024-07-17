package com.ohgiraffers.section02;

import com.ohgiraffers.section02.problem.Category;
import com.ohgiraffers.section02.problem.Menu;
import com.ohgiraffers.section02.problem.MenuAndCategory;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemOfUsingDirectsSQLTests {

    private Connection con;

    @BeforeEach
    void setConnection() throws ClassNotFoundException, SQLException{

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/menudb";
        String user = "ohgiraffers";
        String password = "ohgiraffers";

        Class.forName(driver);

        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);

    }

    @AfterEach
    void closeconnection() throws SQLException{
        con.rollback();
        con.close();
    }

    @Test
    @DisplayName("직접 sql을 작성하여 메뉴를 조회할때 발생하는문제확인")
    void testDirectSelectSQL() throws SQLException{
        //given
        String query = "select menu_code,menu_name, menu_price, category_code,orderable_status from tbl_menu";

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
        Assertions.assertNotNull(menuList);
        menuList.forEach(System.out::println);

        rset.close();
        stmt.close();
    }

    @DisplayName("직접 sql작성하여 신규 메뉴를 추가할때 발생하는 문제 확인")
    @Test
    void testDriectInsertSql() throws SQLException {

        //given
        Menu menu = new Menu();
        menu.setMenuName("민트초코짜장면");
        menu.setMenuPrice(30000);
        menu.setCategoryCode(1);
        menu.setOrderableStatus("Y");

        String query = "insert into tbl_menu(menu_name, menu_price, category_code,orderable_status) values(?,?,?,?)";
        //when
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1,menu.getMenuName());
        pstmt.setDouble(2,menu.getMenuPrice());
        pstmt.setInt(3,menu.getCategoryCode());
        pstmt.setString(4, menu.getOrderableStatus());

        int result = pstmt.executeUpdate();
        //then
        Assertions.assertEquals(1,result);
        pstmt.close();
    }

    @Test
    @DisplayName("연관된 객체 문제 확인")
    void testAssociateObject() throws SQLException{
        //given
        String query = "select a.menu_code,a.menu_name, a.menu_price, b.category_code, b.category_name, a.orderable_status from tbl_menu a join tbl_category b on(a.category_code =b.category_code)";
        //when
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        List<MenuAndCategory> menuAndCategories = new ArrayList<>();
        while (rset.next()){
            MenuAndCategory menuAndCategory = new MenuAndCategory();
            menuAndCategory.setMenuCode(rset.getInt("menu_code"));
            menuAndCategory.setMenuName(rset.getString("menu_name"));
            menuAndCategory.setMenuPrice(rset.getInt("menu_price"));
            menuAndCategory.setCategory(new Category(rset.getInt("category_code"), rset.getString("category_name")));
            menuAndCategory.setOrderableStatus(rset.getString("orderable_status"));
            menuAndCategories.add(menuAndCategory);

        }
        menuAndCategories.get(0).getCategory().getCategoryName();
        //then
        Assertions.assertNotNull(menuAndCategories);
        menuAndCategories.forEach(System.out::println);

        rset.close();
        rset.close();
    }

}
