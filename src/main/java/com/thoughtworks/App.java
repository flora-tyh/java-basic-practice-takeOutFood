package com.thoughtworks;

import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("请点餐（菜品Id x 数量，用逗号隔开）：");
    String selectedItems = scan.nextLine();
    String summary = bestCharge(selectedItems);
    System.out.println(summary);
  }

  /**
   * 接收用户选择的菜品和数量，返回计算后的汇总信息
   *
   * @param selectedItems 选择的菜品信息
   * 输入样例 ITEM0001 x 1,ITEM0013 x 2,ITEM0022 x 1
   */
  public static String bestCharge(String selectedItems) {
    // 此处补全代码
    String[] itemIds = getItemIds();
    int itemKinds = itemIds.length;
    String[] itemNames = getItemNames();
    double[] itemPrices = getItemPrices();
    String[] halfPriceIds = getHalfPriceIds();

    String[] selectedItemList = selectedItems.split(",");
    int selectedItemKinds = selectedItemList.length;
    String[] selectedItemIdList = new String[selectedItemKinds];
    int[] selectedItemCountList = new int[selectedItemKinds];
    int[] itemToltalPriceList = new int[selectedItemKinds];
    String[] selectItemNames = new String[selectedItemKinds];

    for (int i = 0; i < selectedItemKinds; i++) {
      selectedItemIdList[i] = selectedItemList[i].split(" x ")[0];
      selectedItemCountList[i] =  Integer.parseInt(selectedItemList[i].split(" x ")[1]);
    }

    int originTotalPrice = 0;
    for (int i = 0; i < selectedItemKinds; i++) {
      for (int j = 0; j < itemKinds; j++) {
        if (selectedItemIdList[i].equals(itemIds[j])) {
          itemToltalPriceList[i] = (new Double(selectedItemCountList[i] * itemPrices[j])).intValue();
          originTotalPrice += itemToltalPriceList[i];
          selectItemNames[i] = itemNames[j];
        }
      }
    }

    int halfDiscount = 0;
    String halfDiscountItemName = "";
    for (int i = 0; i < itemKinds; i++) {
      for (int j = 0; j < halfPriceIds.length; j++) {
        if (itemIds[i].equals(halfPriceIds[j])) {
          halfDiscountItemName += "，" + itemNames[i];
        }
      }
    }
    halfDiscountItemName = halfDiscountItemName.substring(1);
    for (int i = 0; i < selectedItemKinds; i++) {
      for (int j = 0; j < halfPriceIds.length; j++) {
        if (selectedItemIdList[i].equals(halfPriceIds[j])) {
          halfDiscount += itemToltalPriceList[i] / 2;
        }
      }
    }
    int halfCutPrice = originTotalPrice - halfDiscount;

    int thirtyCutSixPrice = originTotalPrice > 30 ? originTotalPrice - 6 : originTotalPrice;

    String dicountInfo = "";
    int discountTotalPrice = originTotalPrice;
    if (originTotalPrice == thirtyCutSixPrice && originTotalPrice == halfCutPrice) {
      dicountInfo = "";
    } else if (thirtyCutSixPrice < halfCutPrice) {
      dicountInfo = "-----------------------------------\n"
              + "使用优惠:\n"
              + "满30减6元，省6元\n";
      discountTotalPrice = thirtyCutSixPrice;
    } else {
      dicountInfo = "-----------------------------------\n"
              + "使用优惠:\n"
              + "指定菜品半价(" + halfDiscountItemName + ")，省" + halfDiscount + "元\n";
      discountTotalPrice = halfCutPrice;
    }

    String orderItemInfo = "";
    for (int i = 0; i < selectedItemKinds; i++) {
      orderItemInfo += selectItemNames[i] + " x " + selectedItemCountList[i] + " = " + itemToltalPriceList[i] + "元\n";
    }

    String orderInfo = "============= 订餐明细 =============\n"
            + orderItemInfo
            + dicountInfo
            + "-----------------------------------\n"
            + "总计：" +discountTotalPrice + "元\n"
            + "===================================";
    return orderInfo;
  }

  /**
   * 获取每个菜品依次的编号
   */
  public static String[] getItemIds() {
    return new String[]{"ITEM0001", "ITEM0013", "ITEM0022", "ITEM0030"};
  }

  /**
   * 获取每个菜品依次的名称
   */
  public static String[] getItemNames() {
    return new String[]{"黄焖鸡", "肉夹馍", "凉皮", "冰粉"};
  }

  /**
   * 获取每个菜品依次的价格
   */
  public static double[] getItemPrices() {
    return new double[]{18.00, 6.00, 8.00, 2.00};
  }

  /**
   * 获取半价菜品的编号
   */
  public static String[] getHalfPriceIds() {
    return new String[]{"ITEM0001", "ITEM0022"};
  }
}
