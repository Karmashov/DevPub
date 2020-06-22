package com.skillbox.devpub.main.repositories;

import com.skillbox.devpub.main.model.GlobalSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Integer> {
}
