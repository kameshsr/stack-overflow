package com.mountblue.stackoverflow.controller;

import com.mountblue.stackoverflow.model.Answer;
import com.mountblue.stackoverflow.model.Question;
import com.mountblue.stackoverflow.model.User;
import com.mountblue.stackoverflow.repository.AnswerCommentRepository;
import com.mountblue.stackoverflow.repository.AnswerRepository;
import com.mountblue.stackoverflow.service.AnswerService;
import com.mountblue.stackoverflow.service.QuestionService;
import com.mountblue.stackoverflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerCommentRepository answerCommentRepository;
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final UserService userService;

    int oldest =0;

    public AnswerController(AnswerService answerService, AnswerCommentRepository answerCommentRepository, AnswerRepository answerRepository, QuestionService questionService, UserService userService) {
        this.answerService = answerService;
        this.answerCommentRepository = answerCommentRepository;
        this.answerRepository = answerRepository;
        this.questionService = questionService;
        this.userService = userService;
    }

    @PostMapping("/saveAnswerForGuest")
    public String saveAnswerForGuest(@RequestParam("questionId") int questionId,
                                     @ModelAttribute("guestAnswer") Answer guestAnswer,
                                     @RequestParam("userEmail") String userEmail) {
        Question question = questionService.getQuestion(questionId);
        guestAnswer.setQuestion(question);
        answerService.save(guestAnswer);
        question.getAnswers().add(guestAnswer);
        questionService.save(question);
        return "redirect:/question/showQuestion?questionId="+questionId+"&userEmail="+userEmail+"&oldest="+oldest;
    }

    @PostMapping("/saveAnswer")
    public String saveAnswer(@RequestParam("questionId") int questionId,
                             @RequestParam("answerId") int answerId,
                             @ModelAttribute("answer") Answer answer,
                             @RequestParam("userEmail") String userEmail) {

        Question question = questionService.getQuestion(questionId);
        User user = userService.getUserByEmail(userEmail);
        answer.setQuestion(question);
        if (answerId != 0) {
            answer.setId(answerId);
        }
        answer.setReputation(user.getReputation());
        answer.setUserName(user.getName());
        answer.setEmail(user.getEmail());
        answerService.save(answer);
        question.getAnswers().add(answer);
        questionService.save(question);
        return "redirect:/question/showQuestion?questionId="+questionId+"&userEmail="+userEmail+"&oldest="+oldest;
    }

    @GetMapping("/showFormForAnswerUpdate")
    String showFormForAnswerUpdate(@RequestParam("answerId") int answerId,
                                  @RequestParam("questionId") int questionId,
                                   @RequestParam("userEmail") String userEmail, Model model){
        Answer answer=answerService.findById(answerId);
       model.addAttribute("questionId", questionId);
       model.addAttribute("answer",answer);
       model.addAttribute("userEmail",userEmail);
        return "answer/answer-update-form";
    }

    @RequestMapping("/deleteAnswer")
    public String deleteAnswer(@RequestParam("answerId") int answerId ,
                               @RequestParam("questionId") int questionId,
                               @RequestParam("userEmail") String userEmail){
        answerService.deleteById(answerId);
        return "redirect:/question/showQuestion?questionId="+questionId+"&userEmail="+userEmail+"&oldest="+oldest;
    }
}
