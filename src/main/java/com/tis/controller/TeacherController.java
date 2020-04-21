package com.tis.controller;

import com.github.pagehelper.PageInfo;
import com.tis.bean.*;
import com.tis.common.BaseDto;
import com.tis.service.AnswerService;
import com.tis.service.LessonService;
import com.tis.service.QuestionService;
import com.tis.service.SignInService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TeacherController {

    @Resource
    private LessonService lessonService;

    @Resource
    private SignInService signInService;

    @Resource
    private QuestionService questionService;

    @Resource
    private AnswerService answerService;

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

    /**
     * 检查是否有未下课课堂，有则自动进入
     * @param session
     * @return
     */
    @GetMapping("/teacher/checkLesson")
    public BaseDto<Lesson> checkLesson(HttpSession session){
        User teacher = (User) session.getAttribute("teacher");
        Lesson lesson = lessonService.getOnLessonByTeacherId(teacher.getId());
        if(lesson==null){
            return BaseDto.failed(null);
        }
        return BaseDto.success(lesson);
    }

    /**
     * 关闭课堂/下课
     * @param lessonId 课堂ID
     * @param session
     * @return
     */
    @GetMapping("/teacher/closeLesson/{lessonId}")
    public BaseDto<Lesson> closeLesson(@PathVariable Integer lessonId, HttpSession session){
        Lesson lesson = lessonService.getByPrimaryKey(lessonId);
        User teacher = (User) (session.getAttribute("teacher"));
        if(lesson==null){
            return BaseDto.failed("该课堂不存在");
        }
        if(!lesson.getLauncherId().equals(teacher.getId())){
            return BaseDto.failed("您不能操作不是您创建的课堂");
        }
        lesson.setState(0);
        lessonService.update(lesson);
        return BaseDto.success(null);
    }

    /**
     * 开启答题，添加问题
     * @param lessonId 课堂ID
     * @param questionDesc 问题描述
     * @param session
     * @return
     */
    @PostMapping("/teacher/setQuestion")
    public BaseDto<Lesson> setQuestion(Integer lessonId,String questionDesc,HttpSession session){
        Question question = new Question();
        question.setLessonId(lessonId);
        question.setQuestionDesc(questionDesc);
        if(!StringUtils.hasText(questionDesc)){
            return BaseDto.failed("问题描述不能为空");
        }
        boolean b = questionService.insert(question) > 0;
        if (b){
            return BaseDto.success(null);
        }
        return BaseDto.failed("问题添加失败");
    }

    /**
     * 检查是否有正在进行问答的问题
     * @param lessonId
     * @return
     */
    @GetMapping("/teacher/checkQuestion/{lessonId}")
    public BaseDto<Question> checkQuestion(@PathVariable Integer lessonId){
        Question question = questionService.getOnQuestionByLessonId(lessonId);
        if(question!=null){
            return BaseDto.success(question);
        }
        return BaseDto.failed(null);
    }

    /**
     * 获取开启回答中的问题
     * @param session
     * @return
     */
    @GetMapping("/teacher/question/on")
    public BaseDto<Map<String,Object>> getOnQuestionAndLesson(HttpSession session)
    {
        User teacher = (User)session.getAttribute("teacher");
        Lesson lesson = lessonService.getOnLessonByTeacherId(teacher.getId());
        if (lesson == null){
            return BaseDto.failed("你还未创建任何课堂");
        }
        Question question = questionService.getOnQuestionByLessonId(lesson.getId());
        Map<String,Object> result = new HashMap<>();
        result.put("lesson",lesson);
        result.put("question",question);
        return BaseDto.success(result);
    }

    /**
     * 获取回答列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/teacher/answer/list")
    public BaseDto<PageInfo<Answer>> getAnswerByStudentId(HttpSession session,
                                                          @RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                                                          @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize){
        User teacher = (User)session.getAttribute("teacher");
        Lesson lesson = lessonService.getOnLessonByTeacherId(teacher.getId());
        if (lesson == null){
            return BaseDto.failed("你还未创建任何课堂");
        }
        Question question = questionService.getOnQuestionByLessonId(lesson.getId());
        PageInfo<Answer> answerPageInfo = answerService.getAnswersByLessonId(question.getId(),pageNum,pageSize);
        return BaseDto.success(answerPageInfo);
    }
}
