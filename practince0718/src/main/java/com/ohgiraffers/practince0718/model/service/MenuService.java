package com.ohgiraffers.practince0718.model.service;

import com.ohgiraffers.practince0718.model.dto.MenuDTO;
import com.ohgiraffers.practince0718.model.entity.Menu;

import com.ohgiraffers.practince0718.model.repository.MenuRepository;
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

    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
    }

    public MenuDTO findMenuByCode(int menuCode) {
        Menu menu = menuRepository.findById(menuCode)
                .orElseThrow(IllegalArgumentException::new);
        log.info("menu==={}", menu);
        return modelMapper.map(menu, MenuDTO.class);
    }

//    public List<MenuDTO> findMenuList() {
//
//        List<Menu> menuList =
//                menuRepository.findAll();
//        return menuList.stream()
//                .map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
//    }

    public Page<MenuDTO> findAllMenus(Pageable pageable){
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0:pageable.getPageNumber() -1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending());
        Page<Menu> menulist = menuRepository.findAll(pageable);
        return menulist.map(menu -> modelMapper.map(menu, MenuDTO.class));
    }





}
