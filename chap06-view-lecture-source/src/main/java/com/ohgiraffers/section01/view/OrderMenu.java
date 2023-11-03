package com.ohgiraffers.section01.view;

import com.ohgiraffers.section01.model.dto.CategoryDTO;
import com.ohgiraffers.section01.model.dto.MenuDTO;
import com.ohgiraffers.section01.model.dto.OrderDTO;
import com.ohgiraffers.section01.model.dto.OrderMenuDTO;
import com.ohgiraffers.section01.model.service.OrderService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderMenu {
    private OrderService orderService = new OrderService();
    public void displayMainMenu() {

        /*
         * 반복
         * ---------------------------------------------
         * 순서 1. 카테고리 조회
         * 순서 2. 해당 카테고리의 메뉴 조회
         * 순서 3. 사용자에게 어떤 메뉴를 주문받을 것인지 입력
         * 순서 4. 주문할 수량 입력
         * ---------------------------------------------
         * 순서 5. 주문
         * */
        List<OrderMenuDTO> orderMenuList = new ArrayList<>();
        int totalOrderPrice = 0;

        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("================== 음식 주문 프로그램 =======================");

            List<CategoryDTO> categoryList = orderService.selectAllCategory();
            for(CategoryDTO category : categoryList){
                System.out.println(category.getName());
            }

            System.out.println("=============================================================");
            System.out.print("주문하실 카테고리를 선택해주세요 : ");
            String inputCategory = sc.nextLine();

            System.out.println(inputCategory);

            int categoryCode = 0;
            for(int i = 0; i < categoryList.size(); i++) {

                CategoryDTO category = categoryList.get(i);

                if(category.getName().equals(inputCategory)){
                    categoryCode = category.getCode();
                }
            }

            System.out.println("============== 주문 가능 메뉴 ===================");
            List<MenuDTO> menuList = orderService.selectMenuBy(categoryCode);
            for(MenuDTO menu : menuList){
                System.out.println(menu.getName() + " : " + menu.getPrice() + "원");
            }

            System.out.print("주문하실 메뉴를 선택해주세요 : ");
            String inputMenu = sc.nextLine();

            int menuCode = 0;
            int menuPrice = 0;
            for(int i = 0; i < menuList.size(); i++) {
                MenuDTO menu = menuList.get(i);
                if(menu.getName().equals(inputMenu)){
                    menuCode = menu.getCode();
                    menuPrice = menu.getPrice();
                }
            }

            System.out.print("주문하실 수량을 입력하세요 : ");
            int orderAmount = sc.nextInt();

            OrderMenuDTO orderMenu = new OrderMenuDTO();  // 주문메뉴
            orderMenu.setMenuCode(menuCode);
            orderMenu.setOrderAmount(orderAmount);

            orderMenuList.add(orderMenu);  // 주문list에 추가
            totalOrderPrice += (menuPrice * orderAmount);

            sc.nextLine();

            System.out.print("계속 주문 하시겠습니까?(예/아니오) : ");
            boolean isContinue = sc.nextLine().equals("예")? true : false;

            if(!isContinue){
                break;
            }
        } while(true);

        for(OrderMenuDTO orderMenu : orderMenuList){
            System.out.println(orderMenu);
        }

        java.util.Date orderTime = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(orderTime);
        String time = timeFormat.format(orderTime);

        OrderDTO order = new OrderDTO();
        order.setDate(date);
        order.setTime(time);
        order.setTotalOrderprice(totalOrderPrice);
        order.setOrderMenuList(orderMenuList);

        int result = orderService.registOrder(order);

        if(result > 0) {
            System.out.println("주문에 성공하셨습니다.");
        } else {
            System.out.println("주문에 실패하셨습니다.");
        }
    }
}