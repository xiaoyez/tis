package com.tis.controller;

import com.github.pagehelper.PageInfo;
import com.tis.bean.Floor;
import com.tis.bean.Topic;
import com.tis.bean.User;
import com.tis.common.BaseDto;
import com.tis.service.FloorService;
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

    @Resource
    private FloorService floorService;

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
        User user = (User) session.getAttribute("teacher");
        if (user == null)
            user = (User)session.getAttribute("student");
        if (user == null)
            return BaseDto.failed("未登录");
        topic.setSenderId(user.getId());
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
        Topic topic = topicService.getByTopicId(topicId);
        if (topic==null){
            return BaseDto.failed("该讨论不存在");
        }
        return BaseDto.success(topic);
    }

    /**
     * 根据讨论Id获取楼层列表
     * @param topicId
     * @return
     */
    @GetMapping("/common/floors/list")
    public BaseDto<PageInfo<Floor>> getFloorListByTopicId(Integer topicId,
                                                          @RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                                                          @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize){
        return BaseDto.success(new PageInfo<Floor>(floorService.getFloorListByTopicId(topicId,pageNum,pageSize)));
    }

    @PostMapping("/common/floorSend")
    public BaseDto sendFloor(Floor floor, HttpSession session){
        User user = (User) session.getAttribute("teacher");
        if (user == null)
            user = (User)session.getAttribute("student");
        if (user == null)
            return BaseDto.failed("未登录");
        floor.setSenderId(user.getId());
        floor.setSendTime(LocalDateTime.now());
        floorService.insert(floor);
        return BaseDto.success(null);
    }
}
