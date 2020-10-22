package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.universal.Response;

import java.io.IOException;

public interface CaptchaService {

    Response getCaptcha() throws IOException;
}
