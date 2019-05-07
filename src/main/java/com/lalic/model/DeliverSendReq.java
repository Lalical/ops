package com.lalic.model;

import java.util.List;

public class DeliverSendReq {

    String id;

    List<Detail> detail;

    String deliverno;

    public String getDeliverno() {
        return deliverno;
    }

    public void setDeliverno(String deliverno) {
        this.deliverno = deliverno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    public static class Detail {

        String id;

        String deliver;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDeliver() {
            return deliver;
        }

        public void setDeliver(String deliver) {
            this.deliver = deliver;
        }
    }

}
