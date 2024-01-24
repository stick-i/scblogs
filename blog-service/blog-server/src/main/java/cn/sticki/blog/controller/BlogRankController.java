package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.vo.RankAuthorVO;
import cn.sticki.blog.pojo.vo.RankHotVO;
import cn.sticki.blog.service.RankService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author durance
 * @version 1.0
 * @date 2022/10/5 14:12
 */
@Slf4j
@RestController
@RequestMapping("/blog/rank")
public class BlogRankController {

	@Resource
	private RankService rankService;

	/**
	 * 获取当日博客热度排行榜信息
	 */
	@GetMapping("/hot/today")
	public List<RankHotVO> getTodayHotRank() {
		return rankService.getTodayHotRank();
	}

	/**
	 * 获取上周博客热度排行榜信息
	 */
	@GetMapping("/hot/week")
	public List<RankHotVO> getWeekHotRank() {
		return rankService.getWeekHotRank();
	}

	/**
	 * 获取上周作者排行榜
	 */
	@GetMapping("/author/week")
	public List<RankAuthorVO> getWeekAuthorRank() {
		return rankService.getWeekAuthorRank();
	}

	/**
	 * 获取总作者排行榜
	 */
	@GetMapping("/author/total")
	public List<RankAuthorVO> getTotalAuthorRank() {
		return rankService.getTotalAuthorRank();
	}

}
