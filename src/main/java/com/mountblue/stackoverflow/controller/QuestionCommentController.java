package com.mountblue.stackoverflow.controller;
import com.mountblue.stackoverflow.model.Question;
import com.mountblue.stackoverflow.model.QuestionComment;
import com.mountblue.stackoverflow.repository.QuestionCommentRepository;
import com.mountblue.stackoverflow.service.QuestionCommentService;
import com.mountblue.stackoverflow.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/questionComment")
public class QuestionCommentController {
    @Autowired
    private QuestionCommentService questionCommentService;
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionCommentRepository questionCommentRepository;

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

    @PostMapping("/saveQuestionComment/{myQuestionId}")
    public String saveQuestionComment(@PathVariable("myQuestionId") int myQuestionId, Model model,
                                      @ModelAttribute("questionComments") QuestionComment questionComment) {
        Question question = questionService.getQuestion(myQuestionId);
        questionComment.setQuestion(question);
        questionCommentService.save(questionComment);
        question.getComments().add(questionComment);
        questionService.save(question);
        return "redirect:/question/showQuestion?questionId="+myQuestionId;
    }

    @RequestMapping("/updateQuestionComment")
    public String updateQuestionComment(@RequestParam("questionCommentId") int questionCommentId,
                                        @RequestParam("questionId") int questionId, Model model) {
        QuestionComment questionComment = questionCommentService.findQuestionCommentById(questionCommentId);
        model.addAttribute("questionComments",questionComment);
        model.addAttribute("questionId", questionId);
        return "question/update-comment-form";
    }

     @RequestMapping("/deleteQuestionComment/{questionId}/{questionCommentId}")
    public String deleteQuestionComment(@PathVariable("questionId") int questionId, @PathVariable("questionCommentId") int questionCommentId, BindingResult bindingResult){
       if(bindingResult.hasErrors()){
           return "redirect:/questionComments/questionCommentList?error";
       }
       else{
           questionCommentService.deleteById(questionCommentId);
           return "redirect:/question/showQuestion?questionId="+questionId;
       }
    }
}
