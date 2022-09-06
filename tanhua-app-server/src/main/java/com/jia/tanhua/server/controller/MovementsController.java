package com.jia.tanhua.server.controller;

import com.jia.tanhua.dubbo.api.UserInfoApi;
import com.jia.tanhua.mongo.Movement;
import com.jia.tanhua.server.interceptor.BaseContext;
import com.jia.tanhua.server.service.MovementsService;
import com.jia.tanhua.vo.MovementsVo;
import com.jia.tanhua.vo.PageResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/movements")
public class MovementsController {

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

    /**
     * 查询我的动态
     */
    @GetMapping("/all")
    public ResponseEntity myMovements(Long userId,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pagesize) {
        Long userId1 = BaseContext.getUserId();
        PageResult pr = movementsService.findByUserId(userId1,page,pagesize);

        return ResponseEntity.ok(pr);
    }
    @GetMapping
    public ResponseEntity friendMovements(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10")Integer pagesize){
        PageResult pr = movementsService.findFriendMovements(page,pagesize);


        return ResponseEntity.ok(pr);
    }

    /**
     * 推荐动态
     */
    @GetMapping("/recommend")
    public ResponseEntity recommend(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pagesize) {
        PageResult pr = movementsService.findRecommendMovements(page,pagesize);
        return ResponseEntity.ok(pr);
    }
    @GetMapping("/{id}")
    public ResponseEntity one(@PathVariable("id") String movementId){
        MovementsVo movementsVo = movementsService.findOneMovement(movementId);
        return ResponseEntity.ok(movementsVo);

    }
}
