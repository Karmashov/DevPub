package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.model.Init;
import com.skillbox.devpub.service.InitService;
import org.springframework.stereotype.Service;

@Service
public class InitServiceImpl implements InitService {

    @Override
    public Init init() {
        Init init = new Init(
                "DevPub", "Рассказы разработчиков",
                "+7 926 787-77-73", "a.v.karmashov@gmail.com",
                "Андрей Кармашов", "2020"
        );
        return init;
    }
}
