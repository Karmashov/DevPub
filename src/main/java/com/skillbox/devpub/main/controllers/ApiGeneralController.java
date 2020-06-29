package com.skillbox.devpub.main.controllers;

import com.skillbox.devpub.main.model.Init;
import com.skillbox.devpub.main.services.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGeneralController {
    @Autowired
    private final InitService initService;

    public ApiGeneralController(InitService initService) {
        this.initService = initService;
    }

    @GetMapping("/api/init")
    public ResponseEntity<Init> init() {
        return ResponseEntity.ok(initService.get());
    }
}
