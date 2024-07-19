package com.ohgiraffers.springdata.menu.model.repository;

import com.ohgiraffers.springdata.menu.model.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//entityManagerFactoty, entityManager, EntityTransection > 자동구현
//jpaRepository<엔티티명, pk타입>
public interface MenuRepository extends JpaRepository<Menu, Integer> {

//    //쿼리 메소드
//    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort menuPrice1);
//    List<Menu> findByMenuPriceGreaterThanOrderByMenuPriceDesc(Integer menuPrice);
//
//    //menuprice랑 같은 금액의 메뉴목록조회
//    List<Menu> findByMenuPriceEqual(Integer menuPrice);
//    //크거나같은
//    List<Menu> findByMenuPriceThanEqual(Integer menuPrice);

}
