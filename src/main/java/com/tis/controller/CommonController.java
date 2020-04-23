package com.tis.controller;

import com.github.pagehelper.PageInfo;
import com.tis.bean.Topic;
import com.tis.bean.User;
import com.tis.common.BaseDto;
import com.tis.service.TopicService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;

@RestController
public class CommonController {

    @Resource
    private TopicService topicService;

    /**
     * 获取讨论列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/common/topic/list")
    public BaseDto<PageInfo<Topic>> getTopicList( @RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                                                  @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize){
        return BaseDto.success(new PageInfo<Topic>(topicService.getTopicList(pageNum,pageSize)));
    }

    /**
     * 发布讨论
     * @param topic
     * @param session
     * @return
     */
    @PostMapping("/common/topicSend")
    public BaseDto<Topic> sendTopic(Topic topic, HttpSession session){
        User teacher = (User) session.getAttribute("teacher");
        topic.setSenderId(teacher.getId());
        topic.setSendTime(LocalDateTime.now());
        boolean b = topicService.insert(topic) > 0;
        if(b){
            return BaseDto.success(topic);
        }
        return BaseDto.failed(null);
    }

    /**
     * 获取讨论信息
     * @param topicId
     * @param session
     * @return
     */
    @GetMapping("/common/topicView/{topicId}")
    public BaseDto<Topic> topicView(@PathVariable Integer topicId,HttpSession session){
        Topic topic = topicService.getByPrimaryKey(topicId);
        if (topic==null){
            return BaseDto.failed("该讨论不存在");
        }
        return BaseDto.success(topic);
    }
}
