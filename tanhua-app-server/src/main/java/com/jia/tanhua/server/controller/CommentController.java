package com.jia.tanhua.server.controller;

import com.jia.tanhua.server.service.CommentService;
import com.jia.tanhua.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity findComments(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pagesize,
                                       String movementId){

        PageResult result =  commentService.findComments(page,pagesize,movementId);
        return ResponseEntity.ok(result);

    }
}
