package cn.sticki.blog.pojo.bo;


import cn.sticki.blog.pojo.domain.Blog;
import lombok.Data;

/**
 * @author durance
 */
@Data
public class RankHotBO extends Blog {

    /**
     * 热度值
     */
    Double hot;
}
