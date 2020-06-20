package main.services;

import com.google.gson.JsonObject;
import main.model.Init;
import main.repositories.InitRepository;
import org.springframework.stereotype.Service;

@Service
public class InitService {
    private final InitRepository initRepository;

    public InitService(InitRepository initRepository) {
        this.initRepository = initRepository;
    }

    public JsonObject get() {
//        initRepository.findAll();
        JsonObject item = new JsonObject();
        item.addProperty("title", "DevPub");
        item.addProperty("subtitle", "Рассказы разработчиков");
        item.addProperty("phone", "+7 926 787-77-73");
        item.addProperty("email", "a.v.karmashov@gmail.com");
        item.addProperty("copyright", "Андрей Кармашов");
        item.addProperty("copyrightFrom", "2020");
        return item;
    }
}
