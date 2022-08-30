package com.jia.tanhua.server.controller;



import com.jia.tanhua.server.interceptor.BaseContext;
import com.jia.tanhua.server.service.TanhuaService;
import com.jia.tanhua.vo.TodayBest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tanhua")
public class TanhuaController {

    @Autowired
    private TanhuaService tanhuaService;

    @GetMapping("/todayBest")
    public ResponseEntity todayBest(){
        Long userId = BaseContext.getUserId();

        TodayBest todayBest = tanhuaService.todayBest(userId);

        return ResponseEntity.ok(todayBest);
    }


}
