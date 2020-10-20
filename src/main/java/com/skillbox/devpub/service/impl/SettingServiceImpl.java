package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.dto.universal.SettingsDto;
import com.skillbox.devpub.model.GlobalSettings;
import com.skillbox.devpub.repository.GlobalSettingsRepository;
import com.skillbox.devpub.service.SettingsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SettingServiceImpl implements SettingsService {

    private final GlobalSettingsRepository settingsRepository;

    public SettingServiceImpl(GlobalSettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Override
    public Response getSettings() {
        List<GlobalSettings> settings = settingsRepository.findAll();
//        Map<String, String> result = new HashMap<>();
//        for (GlobalSettings settings1 : settings) {
//            result.put(settings1.getCode(), settings1.getValue());
//        }
        return ResponseFactory.getSettings(
                settings.get(0).getValue().equals("YES"),
                settings.get(1).getValue().equals("YES"),
                settings.get(2).getValue().equals("YES")
        );
    }

    @Override
    //@TODO разобраться с сеттингами
    public void editSettings(SettingsDto request) {
//        System.out.println(request);
        if (request.getMultiuserMode() != null) {
            GlobalSettings multiuserMode = settingsRepository.findByCode("MULTIUSER_MODE");
            multiuserMode.setValue(request.getMultiuserMode() ? "YES" : "NO");
            settingsRepository.save(multiuserMode);
        }
        if (request.getPostPremoderation() != null) {
            GlobalSettings postPremoderation = settingsRepository.findByCode("POST_PREMODERATION");
            postPremoderation.setValue(request.getPostPremoderation() ? "YES" : "NO");
            settingsRepository.save(postPremoderation);
        }
        if (request.getPublicStatistics() != null) {
            GlobalSettings publicStatistics = settingsRepository.findByCode("STATISTICS_IS_PUBLIC");
            publicStatistics.setValue(request.getPublicStatistics() ? "YES" : "NO");
            settingsRepository.save(publicStatistics);
        }
    }
}