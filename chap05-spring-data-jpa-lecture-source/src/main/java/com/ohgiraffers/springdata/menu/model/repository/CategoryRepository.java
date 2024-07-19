package com.ohgiraffers.springdata.menu.model.repository;

import com.ohgiraffers.springdata.menu.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    //jpql작성시에는 value만작성해도 되지만 native query작성시에는
    //반드시 nativequery = true속성을 지정해줘야한다

    //jpql 사용
    @Query(value = "select c from Category c order by c.categoryCode")
    List<Category> findAllCategoryByJPQL();

    @Query(value = "select * from tbl_category order by category_code",nativeQuery = true)
    List<Category> findAllCategoryByNativeQuery();


}
