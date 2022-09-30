package cn.sticki.blog.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 增加排行榜热度时，使用 rabbitMQ 传输具体热度增加信息的实体类
 *
 * @author durance
 */
@Data
@AllArgsConstructor
public class RankSendBO {

    /**
     * 传输的博客id
     */
    Integer blogId;

    /**
     * 执行的用户id
     */
    Integer userId;

}
