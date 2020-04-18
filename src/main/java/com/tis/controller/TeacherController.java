package com.tis.controller;

import com.tis.bean.Lesson;
import com.tis.bean.SignIn;
import com.tis.bean.User;
import com.tis.common.BaseDto;
import com.tis.service.LessonService;
import com.tis.service.SignInService;
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
}
