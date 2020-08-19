package com.skillbox.devpub.controller;

import com.skillbox.devpub.service.InitService;
import com.skillbox.devpub.service.impl.InitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGeneralController {
    @Autowired
    private final InitService initService;

    public ApiGeneralController(InitServiceImpl initService) {
        this.initService = initService;
    }

    @GetMapping("/api/init")
    public ResponseEntity<?> init() {
        return ResponseEntity.ok(initService.init());
    }
}
