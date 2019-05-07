package com.lalic.iml;

import com.lalic.dao.CustomerDao;
import com.lalic.dao.OrderDao;
import com.lalic.dao.OrderDetailDao;
import com.lalic.dao.RightDao;
import com.lalic.entity.CustomerTable;
import com.lalic.entity.OrderDetailTable;
import com.lalic.entity.OrderTable;
import com.lalic.entity.RightTable;
import com.lalic.model.Constant;
import com.lalic.model.RightReps;
import com.lalic.service.RightService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RightImp implements RightService {

    @Autowired
    RightDao rightDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderDetailDao orderDetailDao;

    @Override
    public List<RightReps> getRight(String key) {

        List<RightReps> ret = new ArrayList<>();

        RightTable right = rightDao.getRight(key);
        if (right == null) return ret;
        String type = right.getType();
        String[] typeArr = type.split(",");

        for (String s : typeArr) {
            RightReps rightReps = Constant.mapping.get(s);
            ret.add(rightReps);
        }

        return ret;
    }

    Map<String, String> customer = new HashMap<>();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    @Transactional
    public void quickinput() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:/order.txt")));
            String line = "";
            int index = 0;

            String timeStr = null;
            String cname = null;
            String cid = null;

            String orderid = null;

            float money = 0.00f;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if ("0".equals(line)) {
                    continue;
                }
                String[] infos = line.split("\\s+");

                String prodName;
                String isDingzhi;
                String count;
                String perprice;
                String totalprice;

                if (infos.length == 7) {

                    if (timeStr != null) {
                        orderDao.save(new OrderTable(orderid, sdf.parse(orderid).getTime() + "", cname, money + "", cid));
                        money=0;
                    }


                    cname = infos[1];
                    if (!customer.containsKey(cname)) {
                        CustomerTable customerTable = new CustomerTable(cname, "", "");
                        customerDao.save(customerTable);
                        cid = customerTable.getId();
                        customer.put(cname, cid);
                    } else {
                        cid = customer.get(cname);
                    }
                    timeStr = infos[0];
                    prodName = infos[2];
                    isDingzhi = infos[3];
                    count = infos[4];
                    perprice = infos[5];
                    String singleTotal = infos[6];
                    money += Float.valueOf(singleTotal);

                    orderid = timeStr.substring(0, 8) + String.format("%06d", index);
                    orderDetailDao.save(new OrderDetailTable(orderid, prodName, count, perprice, isDingzhi, ""));

                } else if (infos.length == 6) {

                    cname = infos[0];
                    cid = customer.get(cname);
                    prodName = infos[1];
                    isDingzhi = infos[2];
                    count = infos[3];
                    perprice = infos[4];
                    totalprice = infos[5];
                    money += Float.valueOf(totalprice);

                    if (isDingzhi.length() > 2) {
                        System.out.println(index);
                    }


                    orderDetailDao.save(new OrderDetailTable(orderid, prodName, count, perprice, isDingzhi, ""));

                } else {
                    System.out.println(index);
                }
                index++;
            }



            orderDao.save(new OrderTable(orderid, sdf.parse(orderid).getTime() + "", cname, money + "", cid));


            customerDao.flush();
            orderDao.flush();
            orderDetailDao.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    @Transactional
    public void refreshcus() {

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("D:/customer.csv")));
            String line = "";


            while ((line = br.readLine()) != null) {

                line = line.trim();
                String[] split = line.split("\\s+");

                customerDao.update(split[0], split[1], split[4]);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
