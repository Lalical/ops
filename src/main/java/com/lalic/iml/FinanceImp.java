package com.lalic.iml;

import com.lalic.dao.OrderDao;
import com.lalic.dao.OrderDetailDao;
import com.lalic.entity.OrderDetailTable;
import com.lalic.entity.OrderTable;
import com.lalic.model.FinanceEnsureReq;
import com.lalic.model.FinanceSearchReps;
import com.lalic.model.FinanceSearchReq;
import com.lalic.service.FinanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FinanceImp implements FinanceService {

    @Autowired
    OrderDao dao;

    @Autowired
    OrderDetailDao detailDao;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public FinanceSearchReps searchSimple(FinanceSearchReq req) {
        FinanceSearchReps ret = new FinanceSearchReps();
        List<FinanceSearchReps.Item> items = new ArrayList<>();

        List<OrderTable> orders;
        if (req.getCname() != null && !"".equals(req.getCname().trim())) {
            orders = dao.byTimeAndName(req.getStarttime(), req.getEndtime(), req.getCname());
        } else {
            orders = dao.byTime(req.getStarttime(), req.getEndtime());
        }

        for (OrderTable order : orders) {
            FinanceSearchReps.Item item = new FinanceSearchReps.Item();
            item.setCustomername(order.getCustomer());
            item.setId(order.getId());
            item.setIspay(order.getIspay());
            item.setMoney(order.getMoney());
            item.setTime(sdf.format(new Date(Long.valueOf(order.getTime()))));


            List<OrderDetailTable> details = detailDao.byOrderId(order.getId());
            List<FinanceSearchReps.Item.Detail> itemDetails = new ArrayList<>();
            for (OrderDetailTable detail : details) {
                FinanceSearchReps.Item.Detail itemDetail = new FinanceSearchReps.Item.Detail();
                itemDetail.setAttr(detail.getAttr());
                itemDetail.setCount(detail.getCount());
                itemDetail.setMess(detail.getMess());
                itemDetail.setModel(detail.getModel());
                itemDetail.setName(detail.getName());
                itemDetail.setPerprice(detail.getPerprice());
                itemDetails.add(itemDetail);
            }
            item.setDetail(itemDetails);
            items.add(item);
        }
        ret.setData(items);
        return ret;
    }

    @Override
    @Transactional
    public int confirm(FinanceEnsureReq req) {
        String time = sdf.format(new Date());
        String payway = req.getPayway();
        String orderid = req.getId();
        int confirm = dao.financeConfirm(orderid, time, payway);
        return confirm;
    }

    @Override
    public FinanceSearchReps quicksearch() {
        FinanceSearchReps ret = new FinanceSearchReps();
        List<FinanceSearchReps.Item> items = new ArrayList<>();


        List<OrderTable> orders = dao.quickFinanceSearch();
        for (OrderTable order : orders) {
            FinanceSearchReps.Item item = new FinanceSearchReps.Item();
            item.setCustomername(order.getCustomer());
            item.setId(order.getId());
            item.setIspay(order.getIspay());
            item.setMoney(order.getMoney());
            item.setTime(sdf.format(new Date(Long.valueOf(order.getTime()))));


            List<OrderDetailTable> details = detailDao.byOrderId(order.getId());
            List<FinanceSearchReps.Item.Detail> itemDetails = new ArrayList<>();
            for (OrderDetailTable detail : details) {
                FinanceSearchReps.Item.Detail itemDetail = new FinanceSearchReps.Item.Detail();
                itemDetail.setAttr(detail.getAttr());
                itemDetail.setCount(detail.getCount());
                itemDetail.setMess(detail.getMess());
                itemDetail.setModel(detail.getModel());
                itemDetail.setName(detail.getName());
                itemDetail.setPerprice(detail.getPerprice());
                itemDetails.add(itemDetail);
            }
            item.setDetail(itemDetails);
            items.add(item);
        }
        ret.setData(items);
        return ret;
    }
}
