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

@RestController
public class StudentController {

    @Resource
    private LessonService lessonService;

    @Resource
    private SignInService signInService;

    /**
     * 加入课堂
     * @param lessonId 课堂Id
     * @param session
     * @return
     */
    @GetMapping("/student/lesson/{lessonId}")
    public BaseDto<Lesson> joinLesson(@PathVariable Integer lessonId, HttpSession session){
        Lesson lesson = lessonService.getByPrimaryKey(lessonId);
        if (lesson != null)
        {
            if(lesson.getState() != 1)
                return BaseDto.failed("该课堂已下课");
            SignIn signIn = new SignIn();
            signIn.setLessonId(lessonId);
            User student = (User) session.getAttribute("student");
            signIn.setStudentId(student.getId());
            int insert = signInService.insert(signIn);
            if (insert > 0)
                return BaseDto.success(null);
            else
                return BaseDto.failed("进入课堂失败");
        }
        return BaseDto.failed("课堂不存在,请重新输入");
    }
}
