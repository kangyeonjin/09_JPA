package com.ohgiraffers.springdata.menu.model.service;

import com.ohgiraffers.springdata.menu.model.dto.CategoryDto;
import com.ohgiraffers.springdata.menu.model.dto.MenuDto;
import com.ohgiraffers.springdata.menu.model.entity.Category;
import com.ohgiraffers.springdata.menu.model.entity.Menu;
import com.ohgiraffers.springdata.menu.model.repository.CategoryRepository;
import com.ohgiraffers.springdata.menu.model.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    public MenuDto findMenuByCode(int menuCode) {

        //MenuDto >일반 클래스
        //menu > entity

        /*findbyID메소드는 이미 구현이 되어있다
        * 반환값은 Optional타입이고 optional type은 npe방지하기 위해 다양한 기능을 제공
        * */
        Menu menu = menuRepository.findById(menuCode)
                .orElseThrow(IllegalArgumentException::new);  //찾을수없을떄 발생하는 예외
        log.info("menu ========={}", menu);
        return modelMapper.map(menu, MenuDto.class);
    }

    public List<MenuDto> findMenuList() {
        List<Menu> menuList =
                menuRepository.findAll();
//                menuRepository.findAll(Sort.by("menuPrice").descending());
        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDto.class)).collect(Collectors.toList());
    }

    public Page<MenuDto> findAllMenus(Pageable pageable) {
        //page파라미터가 pageable의 number로 넘어옴
        //조회했을때는 인덱스기준이 되기 떄문에 -1 처리
        pageable = PageRequest.of(  //pagerequest.of -> pageable객체 조작
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending());
        Page<Menu> menuList = menuRepository.findAll(pageable);
        return menuList.map(menu -> modelMapper.map(menu, MenuDto.class));
    }

//    public List<MenuDto> findByMenuPrice(Integer menuPrice) {
//
//        List<Menu> menuList = null;
//        if(menuPrice ==0){
//            menuList = menuRepository.findAll();
//        } else if (menuPrice >0) {
//            menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice, Sort.by("menuPrice").descending());
//        }
//        return menuList.stream()
//                .map(menu -> modelMapper.map(menu, MenuDto.class))
//                .collect(Collectors.toList());
//    }

//    public List<MenuDto> findByMenuPrice(Integer menuPrice) {
//
//        List<Menu> menuList = null;
//        if(menuPrice ==0){
//            menuList = menuRepository.findAll();
//        } else if (menuPrice >0) {
//            menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPriceDesc(menuPrice);
//        }
//        return menuList.stream()
//                .map(menu -> modelMapper.map(menu, MenuDto.class))
//                .collect(Collectors.toList());
//    }

//public List<MenuDto> findByMenuPrice(Integer menuPrice) {
//
//    List<Menu> menuList = null;
//    if(menuPrice ==0){
//        menuList = menuRepository.findAll();
//    } else if (menuPrice >0) {
//        menuList = menuRepository.findByMenuPriceEqual(menuPrice);
//    }
//    return menuList.stream()
//            .map(menu -> modelMapper.map(menu, MenuDto.class))
//            .collect(Collectors.toList());
//}

    public List<CategoryDto> findAllCategory() {
//        List<Category> categoryList = categoryRepository.findAll();
//        List<Category> categoryList = categoryRepository.findAllCategoryByJPQL();
        List<Category> categoryList = categoryRepository.findAllCategoryByNativeQuery();
        return categoryList.stream().map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void registNewMenu(MenuDto newMenu) {
//        Menu menu = modelMapper.map(newMenu, Menu.class);
        //builder 적용
        Menu menu = new Menu().builder()
                .menuName(newMenu.getMenuName())
                .menuPrice(newMenu.getMenuPrice())
                .categoryCode(newMenu.getCategoryCode())
                .orderableStatus(newMenu.getOrderableStatus())
                .build();
        menuRepository.save(menu);
    }


    @Transactional
    public void modifyMenu(MenuDto modifyMenu) {
        //modifyMenu -> 비영속
        //영속
        Menu foundMenu = menuRepository.findById(modifyMenu.getMenuCode())
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));
        foundMenu.setMenuName(modifyMenu.getMenuName());
        log.info("foundMenu===>{}", foundMenu);
    }

    @Transactional
    public void deleteMenu(Integer menuCode) {
          menuRepository.deleteById(menuCode);
    }
}
