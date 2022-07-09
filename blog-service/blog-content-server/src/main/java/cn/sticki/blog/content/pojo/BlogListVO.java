package cn.sticki.blog.content.pojo;

import cn.sticki.common.result.ListVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/8 17:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogListVO extends ListVO<BlogDoc> {

}
