package com.lalic.service;

import com.lalic.model.RightReps;

import java.util.List;

public interface RightService {
    List<RightReps> getRight(String key);

    void quickinput();

    void refreshcus();
}
