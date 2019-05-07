package com.lalic.service;

import com.lalic.model.DeliverSearchReps;
import com.lalic.model.DeliverSendReq;
import com.lalic.model.SimpleSearchReq;

public interface DeliverService {
    DeliverSearchReps searchSimple(SimpleSearchReq req);

    DeliverSearchReps quicksearch();

    int send(DeliverSendReq req);

    int judge(DeliverSendReq req);
}
