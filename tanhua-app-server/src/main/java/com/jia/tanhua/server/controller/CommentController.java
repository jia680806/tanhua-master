package com.jia.tanhua.server.controller;

import com.jia.tanhua.server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity comments(@RequestBody Map map){
        String movementId = (String) map.get("movementId");
        String comment = (String) map.get("comment");
        commentService.saveComments(movementId,comment);
        return ResponseEntity.ok(null);

    }
}
