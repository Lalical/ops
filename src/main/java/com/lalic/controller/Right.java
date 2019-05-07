package com.lalic.controller;

import com.lalic.model.RightReps;
import com.lalic.service.RightService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/right")
public class Right {
    private static final Logger logger = LoggerFactory.getLogger(Right.class);
    @Autowired
    RightService service;

    @RequestMapping(value = "",method = RequestMethod.POST)
    public List<RightReps> getRight(@RequestBody String key) {

        logger.info(key);
        return service.getRight(key);
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String testget() {
        service.quickinput();
        return "aa";
    }

    @RequestMapping(value = "/refreshcus",method = RequestMethod.GET)
    public String testpost() {
        service.refreshcus();
        return "hh";
    }


}
