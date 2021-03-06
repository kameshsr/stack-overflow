package com.mountblue.stackoverflow.controller;

import com.mountblue.stackoverflow.model.Answer;
import com.mountblue.stackoverflow.model.Question;
import com.mountblue.stackoverflow.model.User;
import com.mountblue.stackoverflow.service.AnswerService;
import com.mountblue.stackoverflow.service.QuestionService;
import com.mountblue.stackoverflow.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("votes")
public class votesController {
    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    int oldest =0;

    public votesController(UserService userService, QuestionService questionService, AnswerService answerService) {
        this.userService = userService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @RequestMapping("/voteQuestion")
    public String voteQuestion(@RequestParam("questionId") int questionId,
                               @RequestParam("vote") int vote,
                               @RequestParam("userEmail") String userEmail) {
        //System.out.println("i am in vote question");
        Question question = questionService.getQuestion(questionId);
        User user = userService.getUserByEmail(userEmail);
        if (vote != 1) {
            question.setVote(question.getVote() - 1);
            question.setReputation(question.getReputation() - 2);
            user.setReputation(user.getReputation()-1);
        } else {
            question.setVote(Integer.valueOf(question.getVote()) + 1);
            question.setReputation(question.getReputation() + 10);
        }
        questionService.save(question);
        //return "redirect:/question/showAllQuestion?userEmail="+userEmail+"&oldest="+oldest;
        return "redirect:/question/showQuestion?questionId="+questionId+"&userEmail="+userEmail+"&oldest="+oldest;
    }

    @RequestMapping("/voteAnswer")
    public String voteAnswer(@RequestParam("questionId") int questionId,
                             @RequestParam("answerId") int answerId,
                             @RequestParam("vote") int vote,
                             @RequestParam("userEmail") String userEmail) {
        Answer answer = answerService.findById(answerId);
        User user = userService.getUserByEmail(userEmail);
        if (vote == 1) {
            answer.setVote(answer.getVote() + 1);
            answer.setReputation(answer.getReputation() + 10);
        } else {
            answer.setVote(answer.getVote() - 1);
            answer.setReputation(answer.getReputation() - 2);
            user.setReputation(user.getReputation()-1);
        }
        answerService.save(answer);
        return "redirect:/question/showQuestion?questionId="+questionId+"&userEmail="+userEmail+"&oldest="+oldest;
    }
}
