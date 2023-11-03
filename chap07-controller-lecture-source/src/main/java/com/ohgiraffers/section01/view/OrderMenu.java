package com.ohgiraffers.section01.view;

import com.ohgiraffers.section01.controller.OrderController;
import com.ohgiraffers.section01.model.dto.CategoryDTO;
import com.ohgiraffers.section01.model.dto.MenuDTO;
import com.ohgiraffers.section01.model.dto.OrderMenuDTO;

import java.util.*;

public class OrderMenu {

    private OrderController orderController = new OrderController();

    public void displayMainMenu() {

        List<OrderMenuDTO> orderMenuList = new ArrayList<>();
        int totalOrderPrice = 0;

        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("=============== 음식 주문 프로그램 ===============");

            List<CategoryDTO> categoryList = orderController.selectAllCategory();
            for(CategoryDTO category : categoryList) {
                System.out.println(category.getName());
            }
            System.out.println("===========================================");
            System.out.print("주문하실 종류를 입력해주세요 : ");
            String inputCategory = sc.nextLine();

            int categoryCode = 0;
            for(int i = 0; i < categoryList.size(); i++) {
                CategoryDTO category = categoryList.get(i);
                if(category.getName().equals(inputCategory)) {
                    categoryCode = category.getCode();
                }
            }

            System.out.println("============ 주문 가능 메뉴 ===============");
            List<MenuDTO> menuList = orderController.selectMenuBy(categoryCode);
            for(MenuDTO menu : menuList) {
                System.out.println(menu.getName() + " : " + menu.getPrice() + "원");
            }

            System.out.print("주문하실 메뉴를 선택해주세요 : ");
            String inputMenu = sc.nextLine();

            int menuCode = 0;
            int menuPrice = 0;
            for(int i = 0; i < menuList.size(); i++) {
                MenuDTO menu = menuList.get(i);
                if(menu.getName().equals(inputMenu)) {
                    menuCode = menu.getCode();
                    menuPrice = menu.getPrice();
                }
            }

            System.out.print("주문하실 수량을 입력하세요 : ");
            int orderAmount = sc.nextInt();

            OrderMenuDTO orderMenu = new OrderMenuDTO();
            orderMenu.setMenuCode(menuCode);
            orderMenu.setOrderAmount(orderAmount);

            orderMenuList.add(orderMenu);

            totalOrderPrice += (menuPrice * orderAmount);

            System.out.print("계속 주문하시겠습니까?(예/아니오) : ");
            sc.nextLine();
            boolean isContinue = sc.nextLine().equals("예")? true: false;

            if(!isContinue) {
                break;
            }

        } while(true);

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("totalOrderPrice", totalOrderPrice);
        requestMap.put("orderMenuList", orderMenuList);
        //위에는 정수형태, 아래는 객체 타입 다른 타입의 값을 넘길때 맵이라는 객체 하나에 두개의 값을 담아서 보내는 형식임
        //두개 따로 따로 보내도 되지만 나중에 맵형태로 전송하는 경우가 많기 때문에 이런 형식으로 작성

        orderController.registOrder(requestMap);
    }

}
