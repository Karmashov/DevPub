package com.skillbox.devpub.main.services;

import com.skillbox.devpub.main.model.Init;
import org.springframework.stereotype.Service;

@Service
public class InitService {

    public Init get() {
        Init init = new Init(
                "DevPub", "Рассказы разработчиков",
                "+7 926 787-77-73", "a.v.karmashov@gmail.com",
                "Андрей Кармашов", "2020"
        );
        return init;
    }
}
