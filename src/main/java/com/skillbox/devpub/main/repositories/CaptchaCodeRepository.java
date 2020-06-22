package com.skillbox.devpub.main.repositories;

import com.skillbox.devpub.main.model.CaptchaCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaptchaCodeRepository extends JpaRepository<CaptchaCode, Integer> {
}
