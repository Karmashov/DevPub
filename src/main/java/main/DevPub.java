package main;

import main.model.GlobalSettings;
import main.model.GlobalSettingsValues;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevPub {
    public static void main(String[] args) {
        SpringApplication.run(DevPub.class, args);

//        GlobalSettings globalSettings = new GlobalSettings();
//        globalSettings.setCode(GlobalSettingsValues.POST_PREMODERATION);
//        globalSettings.setName(globalSettings.getCode().getName());
//        globalSettings.getCode().setValue(false);
//        globalSettings.setValue(globalSettings.getCode().getValueText());
//        System.out.println(globalSettings.getName());
//        System.out.println(globalSettings.getValue());
    }
}
