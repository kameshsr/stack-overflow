package com.mountblue.stackoverflow.controller;
import com.mountblue.stackoverflow.model.Question;
import com.mountblue.stackoverflow.model.QuestionComment;
import com.mountblue.stackoverflow.repository.QuestionCommentRepository;
import com.mountblue.stackoverflow.service.QuestionCommentService;
import com.mountblue.stackoverflow.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/questionComment")
public class QuestionCommentController {
    private final QuestionCommentService questionCommentService;
    private final QuestionService questionService;
    int oldest = 0;

    public QuestionCommentController(QuestionCommentService questionCommentService, QuestionService questionService, QuestionCommentRepository questionCommentRepository) {
        this.questionCommentService = questionCommentService;
        this.questionService = questionService;
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
                                      Model model, @ModelAttribute("questionComments") QuestionComment questionComment) {
        Question question = questionService.getQuestion(questionId);
        questionComment.setQuestion(question);
        if (questionCommentId != 0)
            questionComment.setId(questionCommentId);
        questionCommentService.save(questionComment);
        question.getComments().add(questionComment);
        questionService.save(question);
        return "redirect:/question/showQuestion?questionId="+questionId+"&oldest="+oldest;
    }

    @RequestMapping("/showFormForUpdateQuestionComment")
    public String updateQuestionComment(@RequestParam("questionCommentId") int questionCommentId,
                                        @RequestParam("questionId") int questionId, Model model) {
        QuestionComment questionComment = questionCommentService.findQuestionCommentById(questionCommentId);
        model.addAttribute("questionComment",questionComment);
        model.addAttribute("questionId", questionId);
        return "question/update-comment-form";
    }

     @RequestMapping("/deleteQuestionComment")
    public String deleteQuestionComment(@RequestParam("questionId") int questionId, @RequestParam("questionCommentId") int questionCommentId){
           questionCommentService.deleteById(questionCommentId);
         return "redirect:/question/showQuestion?questionId="+questionId+"&oldest="+oldest;
       }
}
