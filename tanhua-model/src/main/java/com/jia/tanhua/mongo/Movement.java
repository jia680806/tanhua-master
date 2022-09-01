package com.jia.tanhua.mongo;

import org.bson.types.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movement")
public class Movement implements java.io.Serializable {


    private ObjectId id; //主键id
    private Long pid; //Long类型，用于推荐系统的模型(自动增长)
    private Long created; //发布时间
    private Long userId;
    private String textContent; //文字
    private List<String> medias; //媒体数据，图片或小视频 url
    private String longitude; //经度
    private String latitude; //纬度
    private String locationName; //位置名称
    //状态 0：未审（默认），1：通过，2：驳回
    private Integer state = 0;

    private Integer likeCount = 0; //点赞数
    private Integer commentCount = 0; //评论数
    private Integer loveCount = 0; //喜欢数


}