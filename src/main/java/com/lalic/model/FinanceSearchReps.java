package com.lalic.model;

import java.util.List;

public class FinanceSearchReps {

    List<Item> data;


    public List<Item> getData() {
        return data;
    }

    public void setData(List<Item> data) {
        this.data = data;
    }

    public static class Item {

        String id;

        String time;

        String customername;

        String money;

        String ispay;

        String isdeliver;

        String deliverno;

        public String getIsdeliver() {
            return isdeliver;
        }

        public void setIsdeliver(String isdeliver) {
            this.isdeliver = isdeliver;
        }

        public String getDeliverno() {
            return deliverno;
        }

        public void setDeliverno(String deliverno) {
            this.deliverno = deliverno;
        }

        List<Detail> detail;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCustomername() {
            return customername;
        }

        public void setCustomername(String customername) {
            this.customername = customername;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getIspay() {
            return ispay;
        }

        public void setIspay(String ispay) {
            this.ispay = ispay;
        }

        public List<Detail> getDetail() {
            return detail;
        }

        public void setDetail(List<Detail> detail) {
            this.detail = detail;
        }

        public static class Detail {
            String name;

            String model;

            String attr;

            String perprice;

            String count;

            String mess;

            String delivered;

            public String getDelivered() {
                return delivered;
            }

            public void setDelivered(String delivered) {
                this.delivered = delivered;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getAttr() {
                return attr;
            }

            public void setAttr(String attr) {
                this.attr = attr;
            }

            public String getPerprice() {
                return perprice;
            }

            public void setPerprice(String perprice) {
                this.perprice = perprice;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getMess() {
                return mess;
            }

            public void setMess(String mess) {
                this.mess = mess;
            }
        }

    }
}
