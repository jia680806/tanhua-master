package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.mongo.Friend;
import com.jia.tanhua.mongo.Movement;
import com.jia.tanhua.vo.PageResult;

import java.util.List;

public interface MovementsApi {
    void publish(Movement movement);


    PageResult getMovementsById(Long userId, Integer page, Integer pagesize);

    List<Movement> findFriendMovements(Integer page, Integer pagesize, Long userId);

    List<Movement> findMovementsByPids(List<Long> pids);

    List<Movement> randomMvents(Integer counts);

    Movement findById(String movementId);
}
