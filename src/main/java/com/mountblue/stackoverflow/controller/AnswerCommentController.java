package com.mountblue.stackoverflow.controller;

import com.mountblue.stackoverflow.model.Answer;
import com.mountblue.stackoverflow.model.AnswerComment;
import com.mountblue.stackoverflow.model.QuestionComment;
import com.mountblue.stackoverflow.service.AnswerCommentService;
import com.mountblue.stackoverflow.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/answerComment")
public class AnswerCommentController {

    @Autowired
     private AnswerService answerService;
    private final AnswerCommentService answerCommentService;

    int oldest =0;

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

//    @GetMapping("/showFormForAnswerCommentAdd")
//    String showFormForAnswerComment(@RequestParam("answerId") int answerId,
//                                    @RequestParam("questionId") int questionId ,Model model){
//        AnswerComment answerComment = new AnswerComment();
//        model.addAttribute("answerComment", answerComment);
//        model.addAttribute("questionId", questionId);
//        model.addAttribute("answerId", answerId);
//        return "answer/answer-comment-form";
//    }

    @RequestMapping("/showFormForUpdateAnswerComment")
    public String updateQuestionComment(@RequestParam("answerCommentId") int answerCommentId,
                                        @RequestParam("answerId") int answerId,
                                        @RequestParam("questionId") int questionId, Model model) {
        AnswerComment answerComment = null;
        if (answerCommentId != 0)
            answerComment = answerCommentService.findAnswerCommentById(answerCommentId);
        else
            answerComment = new AnswerComment();
        model.addAttribute("answerComment",answerComment);
        model.addAttribute("answerCommentId",answerCommentId);
        model.addAttribute("questionId", questionId);
        model.addAttribute("answerId", answerId);
        return "answer/answer-comment-form";
    }

    @PostMapping("/saveAnswerComment")
    public String saveAnswerComment(@RequestParam("answerId") int answerId,
                                    @RequestParam("questionId") int questionId,
                                    @RequestParam("answerCommentId") int answerCommentId,
                                    @ModelAttribute("answerComment") AnswerComment answerComment) {
        if (answerCommentId != 0)
            answerComment.setId(answerCommentId);
        Answer answer = answerService.findById(answerId);
        answerComment.setAnswer(answer);
        answerCommentService.save(answerComment);
        answerService.save(answer);
        return "redirect:/question/showQuestion?questionId="+questionId+"&oldest="+oldest;
    }

    @GetMapping("/deleteAnswerComment")
    public String deleteAnswerComment(@RequestParam("answerCommentId") int answerCommentId,
                                      @RequestParam("questionId") int questionId) {
        this.answerCommentService.deleteById(answerCommentId);
        return "redirect:/question/showQuestion?questionId="+questionId+"&oldest="+oldest;
    }
}
