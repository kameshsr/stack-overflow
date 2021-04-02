package com.mountblue.stackoverflow.controller;
import com.mountblue.stackoverflow.model.Question;
import com.mountblue.stackoverflow.model.QuestionComment;
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

    @PostMapping("/saveQuestionComment/{questionId}")
    public String saveQuestionComment(@PathVariable("questionId") int questionId,
                                      @ModelAttribute("questionComments") QuestionComment questionComment) {
        Question question = questionService.getQuestion(questionId);
        questionComment.setQuestion(question);
        questionCommentService.save(questionComment);
        questionService.save(question);
        return "redirect:/user/showHomePage";
    }

     @RequestMapping("/deleteQuestionComment/{questionId}/{questionCommentId}")
    public String deleteQuestionComment(@PathVariable("questionId") int questionId, @PathVariable("questionCommentId") int questionCommentId, BindingResult bindingResult){
       if(bindingResult.hasErrors()){
           return "redirect:/questionComments/questionCommentList?error";
       }
       else{
           questionCommentService.deleteById(questionCommentId);
           return "redirect:/user/showHomePage";
       }
    }

}