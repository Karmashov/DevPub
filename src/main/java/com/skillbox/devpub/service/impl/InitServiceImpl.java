package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.universal.InitResponseDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.service.InitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InitServiceImpl implements InitService {

    @Value("${init.title}")
    private String title;
    @Value("${init.subtitle}")
    private String subtitle;
    @Value("${init.phone}")
    private String phone;
    @Value("${init.email}")
    private String email;
    @Value("${init.copyright}")
    private String copyright;
    @Value("${init.copyrightFrom}")
    private String copyrightFrom;

    @Override
    public Response init() {
        return new InitResponseDto(title, subtitle, phone, email, copyright, copyrightFrom);
    }
}
