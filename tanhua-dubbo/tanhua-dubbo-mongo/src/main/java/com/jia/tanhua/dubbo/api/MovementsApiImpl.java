package com.jia.tanhua.dubbo.api;

import cn.hutool.core.collection.CollUtil;
import com.jia.tanhua.dubbo.utils.IdWorker;
import com.jia.tanhua.dubbo.utils.TimeLineService;
import com.jia.tanhua.mongo.Friend;
import com.jia.tanhua.mongo.Movement;
import com.jia.tanhua.mongo.MovementTimeLine;
import com.jia.tanhua.vo.PageResult;
import org.apache.dubbo.config.annotation.DubboService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
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

    @Override
    public PageResult getMovementsById(Long userId, Integer page, Integer pagesize) {
        Criteria criteria = Criteria.where("userId").in(userId);
        Query query = Query.query(criteria).skip((page - 1) * pagesize).limit(pagesize).with(Sort.by(Sort.Order.desc("created")));
        List<Movement> movements = mongoTemplate.find(query, Movement.class);
        return new PageResult(page,pagesize, 0,movements);
    }

    /**
     * 返回根据好友id 返回该好友圈子动态列表
     * @param page
     * @param pagesize
     * @param friendId
     * @return
     */
    @Override
    public List<Movement> findFriendMovements(Integer page, Integer pagesize, Long friendId) {
        //1.查询时间线表
        Criteria criteria = Criteria.where("friendId").is(friendId);
        Query query = Query.query(criteria).skip((page-1) * pagesize).limit(pagesize)
                .with(Sort.by(Sort.Order.desc("created")));
        List<MovementTimeLine> list = mongoTemplate.find(query, MovementTimeLine.class);

        //2.根据时间线查询圈子动态的id
        List<ObjectId> movementIds = CollUtil.getFieldValues(list, "movementId", ObjectId.class);
        Query movementQuery = Query.query(Criteria.where("id").in(movementIds));
        List<Movement> movementList = mongoTemplate.find(movementQuery, Movement.class);
        return movementList;
    }

    //根据pid数组查询动态
    @Override
    public List<Movement> findMovementsByPids(List<Long> pids) {
        Criteria criteria = Criteria.where("pid").in(pids);
        Query query = Query.query(criteria);
        return mongoTemplate.find(query,Movement.class);
    }

    //随机获取多条动态数据
    @Override
    public List<Movement> randomMvents(Integer counts) {
        TypedAggregation aggregation = Aggregation.newAggregation(Movement.class,Aggregation.sample(counts));
        AggregationResults<Movement> results = mongoTemplate.aggregate(aggregation, Movement.class);
        return results.getMappedResults();
    }


}
