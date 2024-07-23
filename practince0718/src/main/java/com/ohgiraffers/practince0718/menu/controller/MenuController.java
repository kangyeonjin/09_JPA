package com.ohgiraffers.practince0718.menu.controller;

import com.ohgiraffers.practince0718.common.Pagenation;
import com.ohgiraffers.practince0718.common.PagingButtonInfo;
import com.ohgiraffers.practince0718.model.dto.MenuDTO;
import com.ohgiraffers.practince0718.model.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService){
        this.menuService = menuService;
    }

    @GetMapping("/{menuCode}")
    public String findMenuByCode(@PathVariable("menuCode") int menuCode, Model model){
        log.info("menuCode ={}", menuCode);
        MenuDTO menu = menuService.findMenuByCode(menuCode);

        model.addAttribute("menu", menu);

        return "menu/detail";
    }

//    @GetMapping("/list")
//    public String findMenuList(Model model){
//        List<MenuDTO> menuList = menuService.findMenuList();
//        model.addAttribute("menuList", menuList);
//        return "menu/list";
//    }

    @GetMapping("/list")
    public String findAllMenus(@PageableDefault Pageable pageable, Model model){     log.info("pageable={}", pageable);

        log.info("pageable={}", pageable);
        Page<MenuDTO> menulist = menuService.findAllMenus(pageable);

        log.info("조회한내용목록 :{}", menulist.getContent());
        log.info("총 페이지수:{}", menulist.getTotalPages());
        log.info("총메뉴수:{}", menulist.getTotalElements());
        log.info("해당페이징 표시될 요소수:{}", menulist.getSize());
        log.info("해당 페이지에 실제 요소수:{}",menulist.getNumberOfElements());
        log.info("첫페이지 여부:{}", menulist.isFirst());
        log.info("마지막 페이지 여부:{}", menulist.isLast());
        log.info("정렬방식:{}", menulist.getSort());
        log.info("여러페이지 중 현재 인덱스:{}", menulist.getNumber());

        PagingButtonInfo paging = Pagenation.getPagingButtonInfo(menulist);
        model.addAttribute("paging", paging);
        model.addAttribute("menuList", menulist);
        return "menu/list";
    }


}
