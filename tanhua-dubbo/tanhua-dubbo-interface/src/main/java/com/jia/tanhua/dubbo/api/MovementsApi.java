package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.mongo.Movement;

public interface MovementsApi {
    void publish(Movement movement);
}
