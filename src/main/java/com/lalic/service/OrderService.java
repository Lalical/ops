package com.lalic.service;

import com.lalic.model.BaseResponse;
import com.lalic.model.FinanceSearchReps;
import com.lalic.model.OrderDelReq;
import com.lalic.model.OrderInputReq;
import com.lalic.model.OrderSearchReq;

public interface OrderService {
    int input(OrderInputReq input,String key);

    FinanceSearchReps simpleSearch(OrderSearchReq req);

    FinanceSearchReps quickSearch();


    BaseResponse del(OrderDelReq req);
}
