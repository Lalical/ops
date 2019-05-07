package com.lalic.service;

import com.lalic.model.FinanceEnsureReq;
import com.lalic.model.FinanceSearchReps;
import com.lalic.model.FinanceSearchReq;

public interface FinanceService {
    FinanceSearchReps searchSimple(FinanceSearchReq req);

    int confirm(FinanceEnsureReq req);

    FinanceSearchReps quicksearch();
}
