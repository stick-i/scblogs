package cn.sticki.blog.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
     * 消息队列使用String 进行传输
     */
    String blogId;

    /**
     * 执行的增加的分数
     */
    Integer score;
}
