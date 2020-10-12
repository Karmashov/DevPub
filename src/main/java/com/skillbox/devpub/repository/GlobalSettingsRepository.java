package com.skillbox.devpub.repository;

import com.skillbox.devpub.model.GlobalSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Integer> {

    GlobalSettings findByCode(String code);
}
