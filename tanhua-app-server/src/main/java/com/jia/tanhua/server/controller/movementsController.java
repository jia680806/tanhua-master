package com.jia.tanhua.server.controller;

import com.jia.tanhua.mongo.Movement;
import com.jia.tanhua.server.service.MovementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/movements")
public class movementsController {

    @Autowired
    private MovementsService movementsService;


    /**
     * 发布动态
     */
    @PostMapping
    public ResponseEntity movements(Movement movement, MultipartFile imageContent[]) throws IOException {
        movementsService.publishMovements(movement,imageContent);

        return ResponseEntity.ok(null);
    }
}
