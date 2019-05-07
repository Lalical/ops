package com.lalic.iml;

import com.alibaba.fastjson.JSON;
import com.lalic.controller.DeliverCtr;
import com.lalic.dao.CustomerDao;
import com.lalic.dao.OrderDao;
import com.lalic.dao.OrderDetailDao;
import com.lalic.entity.CustomerTable;
import com.lalic.entity.OrderDetailTable;
import com.lalic.entity.OrderTable;
import com.lalic.model.DeliverSearchReps;
import com.lalic.model.DeliverSendReq;
import com.lalic.model.SimpleSearchReq;
import com.lalic.service.DeliverService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DeliverImp implements DeliverService {

    private static final Logger logger = LoggerFactory.getLogger(DeliverCtr.class);

    @Autowired
    OrderDao dao;

    @Autowired
    OrderDetailDao detailDao;

    @Autowired
    CustomerDao customerDao;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public DeliverSearchReps searchSimple(SimpleSearchReq req) {
        DeliverSearchReps ret = new DeliverSearchReps();
        List<DeliverSearchReps.Item> items = new ArrayList<>();


        List<OrderTable> orders = dao.byTime(req.getStarttime(), req.getEndtime());
        for (OrderTable order : orders) {
            if ("已配送".equals(order.getIsdeliver())) {
                continue;
            }
            DeliverSearchReps.Item item = new DeliverSearchReps.Item();
            item.setCustomername(order.getCustomer());
            item.setId(order.getId());
            CustomerTable customer = customerDao.getByID(order.getCustomerid());
            item.setAddress(customer.getAddress());
            item.setPhone(customer.getPhone());
            item.setTime(sdf.format(new Date(Long.valueOf(order.getTime()))));
            item.setIsDeliver(order.getIsdeliver());

            List<OrderDetailTable> details = detailDao.byOrderId(order.getId());
            List<DeliverSearchReps.Item.Detail> itemDetails = new ArrayList<>();
            for (OrderDetailTable detail : details) {
                DeliverSearchReps.Item.Detail itemDetail = new DeliverSearchReps.Item.Detail();
                itemDetail.setAttr(detail.getAttr());
                itemDetail.setCount(detail.getCount());
                itemDetail.setMess(detail.getMess());
                itemDetail.setModel(detail.getModel());
                itemDetail.setName(detail.getName());
                itemDetail.setDelivered(detail.getDelivercount());
                itemDetail.setTodeliver((Integer.valueOf(detail.getCount()) - Integer.valueOf(detail.getDelivercount())) + "");
                itemDetail.setId(detail.getId());
                itemDetails.add(itemDetail);
            }
            item.setDetail(itemDetails);
            items.add(item);
        }
        ret.setData(items);
        return ret;
    }

    @Override
    public DeliverSearchReps quicksearch() {
        DeliverSearchReps ret = new DeliverSearchReps();
        List<DeliverSearchReps.Item> items = new ArrayList<>();


        List<OrderTable> orders = dao.deliverQuickSearch();
        for (OrderTable order : orders) {
            DeliverSearchReps.Item item = new DeliverSearchReps.Item();
            item.setCustomername(order.getCustomer());
            item.setId(order.getId());
            CustomerTable customer = customerDao.getByID(order.getCustomerid());
            item.setAddress(customer.getAddress());
            item.setPhone(customer.getPhone());
            item.setTime(sdf.format(new Date(Long.valueOf(order.getTime()))));
            item.setIsDeliver(order.getIsdeliver());

            List<OrderDetailTable> details = detailDao.byOrderId(order.getId());
            List<DeliverSearchReps.Item.Detail> itemDetails = new ArrayList<>();
            for (OrderDetailTable detail : details) {
                DeliverSearchReps.Item.Detail itemDetail = new DeliverSearchReps.Item.Detail();
                itemDetail.setAttr(detail.getAttr());
                itemDetail.setCount(detail.getCount());
                itemDetail.setMess(detail.getMess());
                itemDetail.setModel(detail.getModel());
                itemDetail.setName(detail.getName());
                itemDetail.setDelivered(detail.getDelivercount());
                itemDetail.setTodeliver((Integer.valueOf(detail.getCount()) - Integer.valueOf(detail.getDelivercount())) + "");
                itemDetail.setId(detail.getId());
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
    public int send(DeliverSendReq req) {

        logger.info("send invoke " + JSON.toJSONString(req));
        String orderid = req.getId();

        List<OrderDetailTable> orderDetails = detailDao.byOrderId(orderid);


        List<DeliverSendReq.Detail> details = req.getDetail();
        for (DeliverSendReq.Detail detail : details) {
            if (detail.getDeliver() == null) continue;
            OrderDetailTable orderDetail = detailDao.byId(detail.getId());
            int delivercount = Integer.valueOf(orderDetail.getDelivercount());
            int thisDeliver = 0;
            if (detail.getDeliver() != null) {
                thisDeliver = Integer.valueOf(detail.getDeliver());
            }

            if (delivercount + thisDeliver > Integer.valueOf(orderDetail.getCount())) {
                throw new RuntimeException("输入错误");
            }
            detailDao.send(detail.getId(), (delivercount + thisDeliver) + "");
        }

        int finishdetailcount = 0;
        for (OrderDetailTable detailIntable : orderDetails) {

            String detailid = detailIntable.getId();
            for (DeliverSendReq.Detail detailInput : details) {

                if (detailid.equals(detailInput.getId())) {
                    Integer allcount = Integer.valueOf(detailIntable.getCount());
                    Integer deliveredcount = Integer.valueOf(detailIntable.getDelivercount());
                    Integer inputcount=0;
                    if(detailInput.getDeliver()!=null){
                        inputcount = Integer.valueOf(detailInput.getDeliver());
                    }
                    if (inputcount + deliveredcount >= allcount) {
                        finishdetailcount++;
                    }
                    break;
                }
            }
        }


        int alldetail = detailDao.countOrderDetail(orderid);
        OrderTable thisorder = dao.getOne(orderid);
        String deliverNo = thisorder.getDeno();
        String deliverTime = thisorder.getDetime();
        if (deliverNo == null) deliverNo = "";
        if (deliverTime == null) deliverTime = "";

        logger.info("judge finishdetailcount=" + finishdetailcount + " alldetail=" + alldetail);
        if (finishdetailcount >= alldetail) {
            dao.finish(orderid, deliverNo + "," + req.getDeliverno(), deliverTime + "," + sdf.format(new Date()));
        } else {
            dao.finishPart(orderid, deliverNo + "," + req.getDeliverno(), deliverTime + "," + sdf.format(new Date()));
        }

        logger.info("send invoke success");

        return 0;
    }

    @Override
    @Transactional
    public int judge(DeliverSendReq req) {
        logger.info("judge");
        String orderid = req.getId();

        int i = judgeDeliver(orderid); //判断是否配送完成

        int sum = detailDao.countOrderDetail(orderid);

        OrderTable thisorder = dao.getOne(orderid);
        String deliverNo = thisorder.getDeno();
        String deliverTime = thisorder.getDetime();
        if (deliverNo == null) deliverNo = "";
        if (deliverTime == null) deliverTime = "";

        logger.info("judge i=" + i + " sum=" + sum);
        if (i >= sum) {
            dao.finish(orderid, deliverNo + "," + req.getDeliverno(), deliverTime + "," + sdf.format(new Date()));
        } else {
            dao.finishPart(orderid, deliverNo + "," + req.getDeliverno(), deliverTime + "," + sdf.format(new Date()));
        }


        return 0;
    }

    public int judgeDeliver(String orderid) {
        int ret = 0;
        detailDao.flush();
        List<OrderDetailTable> orderDetailTables = detailDao.byOrderId(orderid);
        for (OrderDetailTable item : orderDetailTables) {
            if (item.getCount().equals(item.getDelivercount())) {
                ret++;
            }
        }
        return ret;
    }
}
