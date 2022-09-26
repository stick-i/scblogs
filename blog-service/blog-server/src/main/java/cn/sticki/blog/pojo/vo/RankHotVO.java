package cn.sticki.blog.pojo.vo;


import cn.sticki.blog.pojo.domain.Blog;
import lombok.Data;

@Data
public class RankHotVO extends Blog {

    /**
     * 热度值
     */
    Double hot;
}
