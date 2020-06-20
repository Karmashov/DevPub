package main.repositories;

import main.model.CaptchaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CaptchaCodeRepository extends JpaRepository<CaptchaCode, Integer> {
}
