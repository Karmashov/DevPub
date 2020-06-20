package main.repositories;

import main.model.GlobalSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Integer> {
}
