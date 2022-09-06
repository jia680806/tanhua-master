package com.jia.tanhua.server.service;

import cn.hutool.core.collection.CollUtil;
import com.jia.tanhua.autoconfig.template.OssTemplate;
import com.jia.tanhua.commons.utils.Constants;
import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.dubbo.api.MovementsApi;
import com.jia.tanhua.dubbo.api.UserInfoApi;
import com.jia.tanhua.mongo.Movement;
import com.jia.tanhua.server.exception.BusinessException;
import com.jia.tanhua.server.interceptor.BaseContext;
import com.jia.tanhua.vo.ErrorResult;
import com.jia.tanhua.vo.MovementsVo;
import com.jia.tanhua.vo.PageResult;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovementsService {

    @Autowired
    private OssTemplate ossTemplate;
    @DubboReference
    private MovementsApi movementsApi;
    
    @DubboReference
    private UserInfoApi userInfoApi;

    @Autowired
    private RedisTemplate redisTemplate;



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

    public PageResult findByUserId(Long userId, Integer page, Integer pagesize) {
        PageResult pr = movementsApi.getMovementsById(userId,page,pagesize);
        List<Movement> items = (List<Movement>) pr.getItems();
        if (items == null){
            return pr;
        }

        List<MovementsVo> MovementsVoList =new ArrayList<>();
        MovementsVo movementsVo = new MovementsVo();
        UserInfo userInfo = userInfoApi.findUserInfoById(userId);

        for (Movement item : items) {
            BeanUtils.copyProperties(item,movementsVo);
//            BeanUtils.copyProperties(userInfo,movementsVo);
//            MovementsVoList.add(movementsVo);
           MovementsVo vo = MovementsVo.init(userInfo, item);
        }
        pr.setItems(MovementsVoList);

        return pr;
    }

    public PageResult findFriendMovements(Integer page, Integer pagesize) {
        Long userId = BaseContext.getUserId();

        List<Movement> list = movementsApi.findFriendMovements(page,pagesize,userId);
        return getPageResult(page, pagesize, list);

    }

    private PageResult getPageResult(Integer page, Integer pagesize, List<Movement> list) {
        if (CollUtil.isEmpty(list)){
            return new PageResult();
        }

        List<Long> userIds = CollUtil.getFieldValues(list, "userId", Long.class);

        Map<Long, UserInfo> map = userInfoApi.findByIds(userIds, null);

        List<MovementsVo> vos = new ArrayList<>();
        for (Movement movement : list) {
            UserInfo userInfo = map.get(movement.getUserId());
            if (userInfo!=null){
                MovementsVo vo = MovementsVo.init(userInfo,movement);
                vos.add(vo);
            }
        }
        return new PageResult(page, pagesize, 0, vos);
    }


    public PageResult findRecommendMovements(Integer page, Integer pagesize) {
        Long userId = BaseContext.getUserId();
        String redisValue= (String) redisTemplate.opsForValue().get(Constants.MOVEMENTS_RECOMMEND + userId);
        List<Movement> list = Collections.EMPTY_LIST;
        if(StringUtils.isEmpty(redisValue)){
            list = movementsApi.randomMvents(pagesize);
        }else{
           String[] values= redisValue.split(",");
           if((page-1)*pagesize <values.length){
               List<Long> pids = Arrays.stream(values).skip((page - 1) * pagesize).limit(pagesize)
                       .map(e -> Long.valueOf(e)).collect(Collectors.toList());
               list = movementsApi.findMovementsByPids(pids);
           }

        }
        return  getPageResult(page,pagesize,list);
    }

    public MovementsVo findOneMovement(String movementId) {
        Movement movement = movementsApi.findById(movementId);
        if (movement != null){
            UserInfo userInfo = userInfoApi.findUserInfoById(BaseContext.getUserId());
            MovementsVo movementsVo = MovementsVo.init(userInfo, movement);
            return movementsVo;
        }else
            return null;

    }
}
