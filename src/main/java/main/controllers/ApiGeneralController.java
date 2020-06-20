package main.controllers;

import com.google.gson.JsonObject;
import main.model.Init;
import main.services.InitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGeneralController {
    private final InitService initService;

    public ApiGeneralController(InitService initService) {
        this.initService = initService;
    }

    @GetMapping("/api/init/")
    public JsonObject init() {
//        JsonObject item = initService.get();
//        JsonObject item = new JsonObject();
//        item.addProperty("title", "DevPub");
//        item.addProperty("subtitle", "Рассказы разработчиков");
//        item.addProperty("phone", "+7 926 787-77-73");
//        item.addProperty("email", "a.v.karmashov@gmail.com");
//        item.addProperty("copyright", "Андрей Кармашов");
//        item.addProperty("copyrightFrom", "2020");
        return initService.get();
    }
}
