package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.mongo.Movement;
import com.jia.tanhua.vo.MovementsVo;
import com.jia.tanhua.vo.PageResult;

public interface MovementsApi {
    void publish(Movement movement);


    PageResult getMovementsById(Long userId, Integer page, Integer pagesize);
}
