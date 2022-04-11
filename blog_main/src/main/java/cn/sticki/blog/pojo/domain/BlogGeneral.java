package cn.sticki.blog.pojo.domain;

import lombok.Data;

@Data
public class BlogGeneral {

	Integer blogId; //  COMMENT '博客id'

	Integer viewNum; //  COMMENT '浏览量'

	Integer likeNum; //  COMMENT '点赞量'

	Integer commentNum; //  COMMENT '评论量'

	Integer collectionNum; //  COMMENT '收藏量'

	Double score; //  COMMENT '评分'

}
