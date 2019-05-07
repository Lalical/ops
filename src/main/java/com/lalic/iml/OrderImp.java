package com.lalic.iml;

import com.lalic.dao.CustomerDao;
import com.lalic.dao.OrderDao;
import com.lalic.dao.OrderDetailDao;
import com.lalic.entity.CustomerTable;
import com.lalic.entity.OrderDetailTable;
import com.lalic.entity.OrderTable;
import com.lalic.model.FinanceSearchReps;
import com.lalic.model.OrderInputReq;
import com.lalic.model.OrderSearchReq;
import com.lalic.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class OrderImp implements OrderService {

    @Autowired
    OrderDao dao;

    @Autowired
    OrderDetailDao orderDetailDao;

    @Autowired
    CustomerDao customerDao;


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    SimpleDateFormat sdfinput = new SimpleDateFormat("yyyyMMddHHmmss");

    SimpleDateFormat optionTime = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    @Transactional
    public int input(OrderInputReq input,String key) {
        OrderTable item = new OrderTable();

        Date date=null;
        String timeFromPage = input.getOptionTime().trim();
        try {
            date = optionTime.parse(timeFromPage);

            date=new Date(date.getTime()+ new Random().nextInt(100000)+100000);
        } catch (ParseException e) {
//            e.printStackTrace();
            System.out.println("not input time  date==null");
        }

        if(date==null)
        {
            date = new Date();
        }

        String id = sdfinput.format(date);
        item.setId(id);
        item.setCustomerid(input.getCustomerid());
        item.setTime(date.getTime() + "");
        CustomerTable customer = customerDao.getByID(input.getCustomerid());
        if (customer == null) {
            return -1;
        }
        item.setCustomer(customer.getName());
        item.setMoney(calMoney(input) + "");
        item.setIspay("未支付");
        item.setIsproensure("未确认");
        item.setIsdeliver("未配送");
        item.setFrom(key);
        dao.save(item);

        saveDetail(id, input.getProduct());

        return 0;
    }

    @Override
    public FinanceSearchReps simpleSearch(OrderSearchReq req) {
        FinanceSearchReps ret = new FinanceSearchReps();
        List<FinanceSearchReps.Item> items = new ArrayList<>();

        String name = req.getName();
        List<OrderTable> orders;
        if (name == null || "".equals(name.trim())) {
            orders = dao.byTime(req.getStarttime(), req.getEndtime());
        } else {
            orders = dao.byTimeAndName(req.getStarttime(), req.getEndtime(), name);
        }

        for (OrderTable order : orders) {
            FinanceSearchReps.Item item = new FinanceSearchReps.Item();
            item.setCustomername(order.getCustomer());
            item.setId(order.getId());
            item.setIspay(order.getIspay());
            item.setMoney(order.getMoney());
            item.setTime(sdf.format(new Date(Long.valueOf(order.getTime()))));
            item.setIsdeliver(order.getIsdeliver());
            item.setDeliverno(order.getDeno());


            List<OrderDetailTable> details = orderDetailDao.byOrderId(order.getId());
            List<FinanceSearchReps.Item.Detail> itemDetails = new ArrayList<>();
            for (OrderDetailTable detail : details) {
                FinanceSearchReps.Item.Detail itemDetail = new FinanceSearchReps.Item.Detail();
                itemDetail.setAttr(detail.getAttr());
                itemDetail.setCount(detail.getCount());
                itemDetail.setMess(detail.getMess());
                itemDetail.setModel(detail.getModel());
                itemDetail.setName(detail.getName());
                itemDetail.setPerprice(detail.getPerprice());
                itemDetail.setDelivered(detail.getDelivercount());
                itemDetails.add(itemDetail);
            }
            item.setDetail(itemDetails);
            items.add(item);
        }
        items.sort(Comparator.comparing(FinanceSearchReps.Item::getTime));
        ret.setData(items);
        return ret;
    }

    @Override
    public FinanceSearchReps quickSearch() {
        FinanceSearchReps ret = new FinanceSearchReps();
        List<FinanceSearchReps.Item> items = new ArrayList<>();

        List<OrderTable> orders = dao.byPay();

        for (OrderTable order : orders) {
            FinanceSearchReps.Item item = new FinanceSearchReps.Item();
            item.setCustomername(order.getCustomer());
            item.setId(order.getId());
            item.setIspay(order.getIspay());
            item.setMoney(order.getMoney());
            item.setTime(sdf.format(new Date(Long.valueOf(order.getTime()))));
            item.setIsdeliver(order.getIsdeliver());
            item.setDeliverno(order.getDeno());


            List<OrderDetailTable> details = orderDetailDao.byOrderId(order.getId());
            List<FinanceSearchReps.Item.Detail> itemDetails = new ArrayList<>();
            for (OrderDetailTable detail : details) {
                FinanceSearchReps.Item.Detail itemDetail = new FinanceSearchReps.Item.Detail();
                itemDetail.setAttr(detail.getAttr());
                itemDetail.setCount(detail.getCount());
                itemDetail.setMess(detail.getMess());
                itemDetail.setModel(detail.getModel());
                itemDetail.setName(detail.getName());
                itemDetail.setPerprice(detail.getPerprice());
                itemDetail.setDelivered(detail.getDelivercount());
                itemDetails.add(itemDetail);
            }
            item.setDetail(itemDetails);
            items.add(item);
        }
        items.sort(Comparator.comparing(FinanceSearchReps.Item::getTime));
        ret.setData(items);
        return ret;
    }

    private void saveDetail(String id, List<OrderInputReq.Product> product) {
        for (OrderInputReq.Product item : product) {
            OrderDetailTable one = new OrderDetailTable();
            one.setAttr(item.getAttr());
            one.setCount(item.getCount());
            one.setOrderid(id);
            one.setMess(item.getMess());
            one.setDelivercount("0");
            one.setName(item.getName());
            one.setModel(item.getModel());
            one.setPerprice(item.getPerprice());
            orderDetailDao.save(one);
        }
    }

    private long calMoney(OrderInputReq input) {

        List<OrderInputReq.Product> product = input.getProduct();

        long money = 0;

        for (OrderInputReq.Product item : product) {
            Integer perprice = Integer.valueOf(item.getPerprice());
            Integer count = Integer.valueOf(item.getCount());
            money += perprice * count;
        }

        return money;
    }
}
