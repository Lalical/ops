package com.lalic.model;

import java.util.HashMap;
import java.util.Map;

public class Constant {

    public static Map<String,RightReps> mapping=new HashMap<>();


    static {
        mapping.put("订单录入",new RightReps("sales_order","订单录入","page/sales_order.html"));
        mapping.put("财务更新",new RightReps("financial_update","财务更新","page/financial_update.html"));
        mapping.put("配送更新",new RightReps("distribution_renewal","配送更新","page/distribution_renewal.html"));
        mapping.put("客户信息录入",new RightReps("customer_information","客户信息录入","page/customer_information.html"));
        mapping.put("状态查询",new RightReps("state_query","状态查询","page/state_query.html"));
    }


}
