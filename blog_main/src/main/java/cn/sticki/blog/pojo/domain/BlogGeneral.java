package cn.sticki.blog.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class BlogGeneral {

	@TableId
	Integer blogId; // 博客id

	Integer viewNum; // 浏览量

	Integer likeNum; // 点赞量

	Integer commentNum; // 评论量

	Integer collectionNum; // 收藏量

	Integer score; // 评分

}
