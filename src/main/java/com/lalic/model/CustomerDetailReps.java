package com.lalic.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerDetailReps {

    List<Data> data=new ArrayList<>();

    public void add(Data data)
    {
        this.data.add(data);
    }


    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {

        String name;

        String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
