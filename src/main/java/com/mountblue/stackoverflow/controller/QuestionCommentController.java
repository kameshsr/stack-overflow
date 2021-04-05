package com.mountblue.stackoverflow.controller;
import com.mountblue.stackoverflow.model.Question;
import com.mountblue.stackoverflow.model.QuestionComment;
import com.mountblue.stackoverflow.model.User;
import com.mountblue.stackoverflow.repository.QuestionCommentRepository;
import com.mountblue.stackoverflow.service.QuestionCommentService;
import com.mountblue.stackoverflow.service.QuestionService;
import com.mountblue.stackoverflow.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/questionComment")
public class QuestionCommentController {
    private final QuestionCommentService questionCommentService;
    private final QuestionService questionService;
    private UserService userService;
    int oldest = 0;

    public QuestionCommentController(QuestionCommentService questionCommentService, QuestionService questionService, QuestionCommentRepository questionCommentRepository, UserService userService) {
        this.questionCommentService = questionCommentService;
        this.questionService = questionService;
        this.userService = userService;
    }

    @GetMapping("/questionCommentList")
    public String listAllComments(Model model){
       List<QuestionComment> comments=questionCommentService.findAll();
       return "question/comment-list";
    }

    @GetMapping("showQuestionCommentForm/{questionId}")
    public String showFormForQuestionComment(@PathVariable("questionId") int questionId,Model model){
        model.addAttribute("comments",questionCommentService.findAll());
        QuestionComment questionComment=new QuestionComment();
        model.addAttribute("questionComment",questionComment);
        model.addAttribute("question", questionService.getQuestion(questionId));
        return "question/comment-form";
    }

    @PostMapping("/saveQuestionComment")
    public String saveQuestionComment(@RequestParam("questionId") int questionId, @RequestParam("questionCommentId") int questionCommentId,
                                      @RequestParam("userEmail") String userEmail, Model model, @ModelAttribute("questionComments") QuestionComment questionComment) {
        Question question = questionService.getQuestion(questionId);
        User user = userService.getUserByEmail(userEmail);
        questionComment.setQuestion(question);
        if (questionCommentId != 0)
            questionComment.setId(questionCommentId);
        questionComment.setUserName(user.getName());
        questionCommentService.save(questionComment);
        question.getComments().add(questionComment);
        questionService.save(question);
        return "redirect:/question/showQuestion?questionId="+questionId+"&userEmail="+userEmail+"&oldest="+oldest;
    }

    @RequestMapping("/showFormForUpdateQuestionComment")
    public String updateQuestionComment(@RequestParam("questionCommentId") int questionCommentId,
                                        @RequestParam("questionId") int questionId,
                                        @RequestParam("userEmail") String userEmail, Model model) {
        QuestionComment questionComment = questionCommentService.findQuestionCommentById(questionCommentId);
        model.addAttribute("questionComment",questionComment);
        model.addAttribute("questionId", questionId);
        model.addAttribute("userEmail", userEmail);
        return "question/update-comment-form";
    }

     @RequestMapping("/deleteQuestionComment")
    public String deleteQuestionComment(@RequestParam("questionId") int questionId, @RequestParam("questionCommentId") int questionCommentId,
                                        @RequestParam("userEmail") String userEmail){
           questionCommentService.deleteById(questionCommentId);
           return "redirect:/question/showQuestion?questionId="+questionId+"&userEmail="+userEmail+"&oldest="+oldest;
       }
}
