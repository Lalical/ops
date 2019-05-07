package com.lalic.model;

import java.util.List;

public class DeliverSearchReps {

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

        String address;

        String phone;

        String isDeliver;

        List<Detail> detail;

        public String getIsDeliver() {
            return isDeliver;
        }

        public void setIsDeliver(String isDeliver) {
            this.isDeliver = isDeliver;
        }

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public List<Detail> getDetail() {
            return detail;
        }

        public void setDetail(List<Detail> detail) {
            this.detail = detail;
        }

        public static class Detail {

            String id;
            String name;

            String model;

            String attr;

            String delivered;

            String todeliver;

            String count;

            String mess;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDelivered() {
                return delivered;
            }

            public void setDelivered(String delivered) {
                this.delivered = delivered;
            }

            public String getTodeliver() {
                return todeliver;
            }

            public void setTodeliver(String todeliver) {
                this.todeliver = todeliver;
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
