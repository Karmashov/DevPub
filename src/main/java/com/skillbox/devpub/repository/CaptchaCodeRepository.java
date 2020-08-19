package com.skillbox.devpub.repository;

import com.skillbox.devpub.model.CaptchaCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaptchaCodeRepository extends JpaRepository<CaptchaCode, Integer> {
}
