package cn.sticki.blog.service.impl;

import cn.sticki.blog.mapper.BlogBasicMapper;
import cn.sticki.blog.pojo.domain.BlogBasic;
import cn.sticki.blog.service.BlogBasicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlogBasicServiceImpl extends ServiceImpl<BlogBasicMapper, BlogBasic> implements BlogBasicService {

}
