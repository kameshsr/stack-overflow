package com.mountblue.stackoverflow.controller;

import com.mountblue.stackoverflow.model.Answer;
import com.mountblue.stackoverflow.model.AnswerComment;
import com.mountblue.stackoverflow.model.User;
import com.mountblue.stackoverflow.service.AnswerCommentService;
import com.mountblue.stackoverflow.service.AnswerService;
import com.mountblue.stackoverflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/answerComment")
public class AnswerCommentController {

    @Autowired
    private AnswerService answerService;
    private final AnswerCommentService answerCommentService;
    private UserService userService;
    private int oldest = 0;

    @Autowired
    public AnswerCommentController(AnswerCommentService answerCommentService, UserService userService) {
        this.answerCommentService = answerCommentService;
        this.userService = userService;
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
                                        @RequestParam("questionId") int questionId,
                                        @RequestParam("userEmail") String userEmail, Model model) {
        AnswerComment answerComment = null;
        if (answerCommentId != 0)
            answerComment = answerCommentService.findAnswerCommentById(answerCommentId);
        else
            answerComment = new AnswerComment();
        model.addAttribute("answerComment",answerComment);
        model.addAttribute("answerCommentId",answerCommentId);
        model.addAttribute("questionId", questionId);
        model.addAttribute("answerId", answerId);
        model.addAttribute("userEmail", userEmail);
        return "answer/answer-comment-form";
    }

    @PostMapping("/saveAnswerComment")
    public String saveAnswerComment(@RequestParam("answerId") int answerId,
                                    @RequestParam("questionId") int questionId,
                                    @RequestParam("userEmail") String userEmail,
                                    @RequestParam("answerCommentId") int answerCommentId,
                                    @ModelAttribute("answerComment") AnswerComment answerComment) {
        User user = userService.getUserByEmail(userEmail);
        if (answerCommentId != 0)
            answerComment.setId(answerCommentId);
        Answer answer = answerService.findById(answerId);
        answerComment.setAnswer(answer);
        answerComment.setUserName(user.getName());
        answerCommentService.save(answerComment);
        answerService.save(answer);
        return "redirect:/question/showQuestion?questionId="+questionId+"&userEmail="+userEmail+"&oldest="+oldest;
    }

    @GetMapping("/deleteAnswerComment")
    public String deleteAnswerComment(@RequestParam("answerCommentId") int answerCommentId,
                                      @RequestParam("questionId") int questionId,
                                      @RequestParam("userEmail") String userEmail) {
           this.answerCommentService.deleteById(answerCommentId);
           return "redirect:/question/showQuestion?questionId="+questionId+"&userEmail="+userEmail+"&oldest="+oldest;
    }
}
