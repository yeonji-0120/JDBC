package com.ohgiraffers.section02.service.run;

import com.ohgiraffers.section02.service.mode.service.MenuService;

public class Application {
    public static void main(String[] args) {
        MenuService menuService = new MenuService();
        menuService.registNewMenu();
//        new MenuService().registNemMenu(); //단발성으로 사용할때
    }
}
