package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.mongo.Movement;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class MovementsApiImpl implements MovementsApi {
    @Override
    public void publish(Movement movement) {

    }
}
