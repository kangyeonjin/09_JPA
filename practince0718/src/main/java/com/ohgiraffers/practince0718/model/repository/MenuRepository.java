package com.ohgiraffers.practince0718.model.repository;

import com.ohgiraffers.practince0718.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer> {

}
