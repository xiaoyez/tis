package com.tis.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.tis.bean.*;
import com.tis.common.BaseDto;
import com.tis.service.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StudentController {

    @Resource
    private LessonService lessonService;

    @Resource
    private SignInService signInService;

    @Resource
    private AnswerService answerService;

    @Resource
    private QuestionService questionService;

    @Resource
    private HomeworkService homeworkService;

    @Resource
    private FileUploadService fileUploadService;

    @Resource
    private InfoService infoService;

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
            if(lesson.getState() == 0)
                return BaseDto.failed("该课堂已下课");
            SignIn signIn = new SignIn();
            signIn.setLessonId(lessonId);
            User student = (User) session.getAttribute("student");
            signIn.setStudentId(student.getId());
            SignIn signIn1 = signInService.get(signIn);
            if (signIn1 != null)
                return BaseDto.failed("你已加入该课堂");
            int insert = signInService.insert(signIn);
            if (insert > 0)
                return BaseDto.success(lesson);
            else
                return BaseDto.failed("进入课堂失败");
        }
        return BaseDto.failed("课堂不存在,请重新输入");
    }

    /**
     * 签到
     * @param lessonId 课堂Id
     * @param session
     * @return
     */
    @GetMapping("/student/signin/{lessonId}")
    public BaseDto<Lesson> signIn(@PathVariable Integer lessonId, HttpSession session){
        Lesson lesson = lessonService.getByPrimaryKey(lessonId);
        if (lesson == null) {
            return BaseDto.failed("该课程不存在");
        }
        if (lesson.getState() == 0) {
            return BaseDto.failed("该课程已下课");
        }
        if (lesson.getState() == 1) {
            return BaseDto.failed("签到未开启，请等待老师开始签到!");
        }
        User student = (User) session.getAttribute("student");
        int studentId = student.getId();
        SignIn signIn = new SignIn();
        signIn.setLessonId(lessonId);
        signIn.setStudentId(studentId);
        signIn = signInService.get(signIn);
        if (signIn == null) {
            return BaseDto.failed("你并未加入这个课堂!");
        }
        signIn.setHasSigned(1);
        signInService.update(signIn);
        return BaseDto.success(null);
    }

    /**
     * 退出课堂
     * @param lessonId 课堂Id
     * @param session
     * @return
     */
    @GetMapping("/student/exit/{lessonId}")
    public BaseDto<Lesson> exitLesson(@PathVariable Integer lessonId, HttpSession session) {
        Lesson lesson = lessonService.getByPrimaryKey(lessonId);
        if (lesson == null) {
            return BaseDto.failed("该课程不存在");
        }
        if (lesson.getState() == 0) {
            return BaseDto.failed("该课程已下课");
        }
        User student = (User) session.getAttribute("student");
        int studentId = student.getId();
        SignIn signIn = new SignIn();
        signIn.setLessonId(lessonId);
        signIn.setStudentId(studentId);
        signIn = signInService.get(signIn);
        if (signIn == null) {
            return BaseDto.failed("你并未加入这个课堂!");
        }
        signInService.delete(signIn.getId());
        return BaseDto.success(null);
    }

    /**
     * 获取学生已加入的课堂
     * @param session
     * @return
     */
    @GetMapping("/student/lesson/hasJoin")
    public BaseDto<Lesson> hasJoinLesson(HttpSession session){
        User student = (User)session.getAttribute("student");
        Lesson lesson = lessonService.getOnLessonByStudentId(student.getId());
        if (lesson == null)
            return BaseDto.failed("你还未加入任何课堂");
        return BaseDto.success(lesson);
    }

    /**
     * 获取回答列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/student/answer/list")
    public BaseDto<PageInfo<Answer>> getAnswerByStudentId(HttpSession session,
                                                @RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                                                @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize){
        User student = (User)session.getAttribute("student");
        Lesson lesson = lessonService.getOnLessonByStudentId(student.getId());
        if (lesson == null)
            return BaseDto.failed("你还未加入任何课堂");
        Question question = questionService.getOnQuestionByLessonId(lesson.getId());
        PageInfo<Answer> answerPageInfo = answerService.getAnswersByLessonId(question.getId(),pageNum,pageSize);
        return BaseDto.success(answerPageInfo);
    }

    /**
     * 获取开启回答中的问题
     * @param session
     * @return
     */
    @GetMapping("/student/question/on")
    public BaseDto<Map<String,Object>> getOnQuestionAndLesson(HttpSession session)
    {
        User student = (User)session.getAttribute("student");
        Lesson lesson = lessonService.getOnLessonByStudentId(student.getId());
        if (lesson == null)
            return BaseDto.failed("你还未加入任何课堂");
        Question question = questionService.getOnQuestionByLessonId(lesson.getId());
        Map<String,Object> result = new HashMap<>();
        result.put("lesson",lesson);
        result.put("question",question);
        return BaseDto.success(result);
    }

    /**
     * 发送答案
     * @param session
     * @param answer
     * @return
     */
    @PostMapping("/student/answer/send")
    public BaseDto<String> sendAnswer(HttpSession session, Answer answer){
        User student = (User)session.getAttribute("student");
        answer.setStudentId(student.getId());
        Lesson lesson = lessonService.getOnLessonByStudentId(student.getId());
        if (lesson == null)
            return BaseDto.failed("你还未加入任何课堂");
        Question question = questionService.getOnQuestionByLessonId(lesson.getId());
        answer.setQuestionId(question.getId());
        answerService.insert(answer);
        return BaseDto.success(null);
    }

    /**
     * 获取学生加入过的课堂列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/student/lesson/list")
    public BaseDto<PageInfo<Lesson>> getHasJoinLessons(HttpSession session,
                                                       @RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                                                       @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize)
    {
        User student = (User)session.getAttribute("student");
        PageInfo<Lesson> lessonPageInfo = lessonService.getHasJoinLessonByStudentId(student.getId(),pageNum,pageSize);
        return BaseDto.success(lessonPageInfo);
    }

    /**
     * 上传作业
     * @param session
     * @param lessonId
     * @param file
     * @return
     */
    @PostMapping("/student/homework/upload")
    public BaseDto uploadHomework(HttpSession session, Integer lessonId, MultipartFile file) throws IOException {
        User student = (User)session.getAttribute("student");
        Homework homework = new Homework();
        homework.setLessonId(lessonId);
        homework.setStudentId(student.getId());
        Map<String, String> map = fileUploadService.uploadFile(file);
        homework.setHomeworkPath(map.get("filename"));
        homework.setHomeworkFilename(map.get("originalFilename"));
        homeworkService.insert(homework);
        return BaseDto.success(null);
    }

    /**
     * 获取老师发布的信息列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/student/info/list")
    public BaseDto<PageInfo<Info>> getInfoList(@RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                                               @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize){
        return BaseDto.success(infoService.getInfoList(pageNum,pageSize));
    }

    @GetMapping("/student/info/view/{infoId}")
    public BaseDto<Info> getInfo(@PathVariable Integer infoId){
        return BaseDto.success(infoService.getByPrimaryKey(infoId));
    }
}