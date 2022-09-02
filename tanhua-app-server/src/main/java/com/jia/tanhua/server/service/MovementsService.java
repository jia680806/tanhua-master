package com.jia.tanhua.server.service;

import com.jia.tanhua.autoconfig.template.OssTemplate;
import com.jia.tanhua.dubbo.api.MovementsApi;
import com.jia.tanhua.mongo.Movement;
import com.jia.tanhua.server.exception.BusinessException;
import com.jia.tanhua.server.interceptor.BaseContext;
import com.jia.tanhua.vo.ErrorResult;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovementsService {

    @Autowired
    private OssTemplate ossTemplate;
    @DubboReference
    private MovementsApi movementsApi;

    public void publishMovements(Movement movement, MultipartFile[] imageContent) throws IOException {
        Long userId = BaseContext.getUserId();
        //判断发布内容是否为空
        if(StringUtils.isEmpty(movement.getTextContent())){
            throw new BusinessException(ErrorResult.contentError());
        }
        List<String> medias = new ArrayList<>();
        //将文件存在阿里云
        for (MultipartFile multipartFile : imageContent) {
            String upload = ossTemplate.upload(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
            medias.add(upload);
        }
        movement.setMedias(medias);
        movement.setUserId(userId);
        movementsApi.publish(movement);

    }
}
