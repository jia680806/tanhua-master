package com.jia.tanhua.server.controller;


import com.jia.tanhua.server.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/phone")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @PostMapping("/sendVerificationCode")
    public ResponseEntity sendVerificationCode(){

        phoneService.sendVerificationCode();
        return ResponseEntity.ok(null);
    }

    @PostMapping("/checkVerificationCode")
    public ResponseEntity checkVerificationCode(@RequestBody String verificationCode){
        boolean bool =  phoneService.checkVerificationCode(verificationCode);
        return ResponseEntity.ok(bool);
    }


}
