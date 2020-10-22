package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.universal.SettingsDto;

public interface SettingsService {

    Response getSettings();

    void editSettings(SettingsDto request);
}
