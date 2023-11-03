package com.ohgiraffers.section01.model.dto;

import java.util.List;

public class OrderDTO implements java.io.Serializable{
    private int code;
    private String date;
    private String time;
    private int totalOrderprice;
    private List<OrderMenuDTO> orderMenuList;

    public OrderDTO() {
    }

    public OrderDTO(int code, String date, String time, int totalOrderprice, List<OrderMenuDTO> orderMenuList) {
        this.code = code;
        this.date = date;
        this.time = time;
        this.totalOrderprice = totalOrderprice;
        this.orderMenuList = orderMenuList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotalOrderprice() {
        return totalOrderprice;
    }

    public void setTotalOrderprice(int totalOrderprice) {
        this.totalOrderprice = totalOrderprice;
    }

    public List<OrderMenuDTO> getOrderMenuList() {
        return orderMenuList;
    }

    public void setOrderMenuList(List<OrderMenuDTO> orderMenuList) {
        this.orderMenuList = orderMenuList;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "code=" + code +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", totalOrderprice=" + totalOrderprice +
                ", orderMenuList=" + orderMenuList +
                '}';
    }
}
