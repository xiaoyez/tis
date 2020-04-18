package com.tis.controller;

import com.tis.bean.Lesson;
import com.tis.bean.SignIn;
import com.tis.bean.User;
import com.tis.common.BaseDto;
import com.tis.service.LessonService;
import com.tis.service.SignInService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
public class TeacherController {

    @Resource
    private LessonService lessonService;

    @Resource
    private SignInService signInService;

    /**
     * 创建课堂
     * @param lessonName 课堂名称
     * @param session
     * @return
     */

    @GetMapping("/teacher/lesson/{lessonName}")
    public BaseDto<Lesson> createLesson(@PathVariable String lessonName, HttpSession session){
        Lesson lesson = new Lesson();
        lesson.setName(lessonName);
        int id;
        while (true){
            id = (int)(Math.random()*100000);
            Lesson lesson1 = lessonService.getByPrimaryKey(id);
            if(lesson1 == null){
                break;
            }
        }
        lesson.setId(id);
        lesson.setLaunchTime(LocalDateTime.now());
        User teacher = (User) (session.getAttribute("teacher"));
        lesson.setLauncherId(teacher.getId());
        lessonService.insert(lesson);
        return BaseDto.success(lesson);
    }

    /**
     * 开启签到
     * @param lessonId 课堂id
     * @param session
     * @return
     */
    @GetMapping("/teacher/lessonBeginSignIn/{lessonId}")
    public BaseDto<Lesson> lessonBeginSignIn(@PathVariable Integer lessonId, HttpSession session){
        Lesson lesson = lessonService.getByPrimaryKey(lessonId);
        User teacher = (User) (session.getAttribute("teacher"));
        if(lesson==null){
            return BaseDto.failed("该课堂不存在");
        }
        if(!lesson.getLauncherId().equals(teacher.getId())){
            return BaseDto.failed("您不能操作不是您创建的课堂");
        }
        lesson.setState(2);
        lessonService.update(lesson);
        return BaseDto.success(null);

    }

//    @GetMapping("/teacher/checkLesson")
//    public BaseDto<Lesson> checkLesson(HttpSession session){
//        User teacher = (User) session.getAttribute("teacher");
//        Lesson lesson = new Lesson();
//        lesson.setLauncherId(teacher.getId());
//        lessonService.get(lesson);
//    }

}
