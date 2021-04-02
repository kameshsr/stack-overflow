package com.mountblue.stackoverflow.controller;

import com.mountblue.stackoverflow.model.Answer;
import com.mountblue.stackoverflow.model.AnswerComment;
import com.mountblue.stackoverflow.service.AnswerCommentService;
import com.mountblue.stackoverflow.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/answerComments")
public class AnswerCommentController {

    @Autowired
     private AnswerService answerService;
    private final AnswerCommentService answerCommentService;

    @Autowired
    public AnswerCommentController(AnswerCommentService answerCommentService) {
        this.answerCommentService = answerCommentService;
    }


    @GetMapping("/commentsList")
    public String listComment(Model theModel) {

        List<AnswerComment> comments = answerCommentService.findAll();
        theModel.addAttribute("comments", comments);
        return "answer/list-comment";
    }

    @GetMapping("/showFormForCommentAdd/{answerId}")
    String showFormForAnswerComment(@PathVariable("answerId") int answerId,Model model){
        model.addAttribute("answerComment", answerCommentService.findAll());
        AnswerComment comments = new AnswerComment();
        model.addAttribute("comments", comments);
        model.addAttribute("answer", answerService.findById(answerId));
        return "answer/comment-form";

    }

    @PostMapping("/saveAnswerComments/{answerId}")
    public String saveAnswerComment(@PathVariable("answerId") int answerId, @ModelAttribute("comments") AnswerComment answerComment) {
        Answer answer = answerService.findById(answerId);
        answerComment.setAnswer(answer);
        answerCommentService.save(answerComment);
        answerService.save(answer);
        return "redirect:/user/showHomePage";
    }

    @GetMapping("/{answerId}/deleteComments/{answerCommentId}")
    public String delete(@PathVariable("answerId") int theId, @PathVariable("answerCommentId") int answerCommentId, BindingResult bindingResult) {
       if(bindingResult.hasErrors()){
           return "redirect:/answerComments/commentsList?error";
       }else{
           this.answerCommentService.deleteById(answerCommentId);
           return "redirect:/user/showHomePage";
           //"redirect:/answer/showAnswer";
       }

    }


}
