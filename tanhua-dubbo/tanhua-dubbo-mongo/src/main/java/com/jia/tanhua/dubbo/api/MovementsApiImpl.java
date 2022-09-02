package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.dubbo.utils.IdWorker;
import com.jia.tanhua.dubbo.utils.TimeLineService;
import com.jia.tanhua.mongo.Friend;
import com.jia.tanhua.mongo.Movement;
import com.jia.tanhua.mongo.MovementTimeLine;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;


@DubboService
public class MovementsApiImpl implements MovementsApi {

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TimeLineService timeLineService;
    public void publish(Movement movement) {
        //1.设置pid、时间
        try {
            movement.setPid(idWorker.getNextId("movement"));
            movement.setCreated(System.currentTimeMillis());
            mongoTemplate.save(movement);
//            //2.查询当前用户好友
//            Criteria criteria = Criteria.where("userId").is(movement.getUserId());
//            Query query = Query.query(criteria);
//            List<Friend> friends = mongoTemplate.find(query, Friend.class);
//            //3.循环好友数据，构建时间线数据，存入数据库。
//            for (Friend friend : friends) {
//                MovementTimeLine timeLine = new MovementTimeLine();
//                timeLine.setMovementId(movement.getId());
//                timeLine.setUserId(friend.getUserId());
//                timeLine.setFriendId(friend.getFriendId());
//                timeLine.setCreated(System.currentTimeMillis());
//                mongoTemplate.save(timeLine);
//            }
            timeLineService.saveTimeLine(movement.getUserId(),movement.getId());
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
