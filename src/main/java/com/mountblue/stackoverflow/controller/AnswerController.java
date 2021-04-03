package com.mountblue.stackoverflow.controller;

import com.mountblue.stackoverflow.model.Answer;
import com.mountblue.stackoverflow.model.Question;
import com.mountblue.stackoverflow.model.QuestionComment;
import com.mountblue.stackoverflow.repository.AnswerCommentRepository;
import com.mountblue.stackoverflow.repository.AnswerRepository;
import com.mountblue.stackoverflow.service.AnswerService;
import com.mountblue.stackoverflow.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;
    @Autowired
    private AnswerCommentRepository answerCommentRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

    public AnswerController(AnswerService answerService, AnswerCommentRepository answerCommentRepository, AnswerRepository answerRepository, QuestionService questionService) {
        this.answerService = answerService;
        this.answerCommentRepository = answerCommentRepository;
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    @RequestMapping("/showAnswer")
    public String showAnswer(Model model, @RequestParam("answerId") int answerId) {
        Answer answer = answerService.findById(answerId);
        model.addAttribute("comments",answerCommentRepository.findByAnswerId(answerId));
        model.addAttribute("answer", answer);
        return "answer/show-answer";
    }

    @GetMapping("/showAnswerForm")
        String answerData(Model model){
        Answer answer=new Answer();
        model.addAttribute("answer", answer);
        return "answer/answer-form";
    }

    @PostMapping("/saveAnswer")
    public String saveAnswer(@RequestParam("questionId") int questionId,
                             @RequestParam("answerId") int answerId,
                             @ModelAttribute("answer") Answer answer) {

        Question question = questionService.getQuestion(questionId);
        answer.setQuestion(question);
        if (answerId != 0) {
            answer.setId(answerId);
        }
        answerService.save(answer);
        question.getAnswers().add(answer);
        questionService.save(question);
        return "redirect:/question/showQuestion?questionId="+questionId;
    }

    @GetMapping("/showFormForAnswerUpdate")
    String showFormForAnswerUpdate(@RequestParam("answerId") int answerId,
                                  @RequestParam("questionId") int questionId ,Model model){
        Answer answer=answerService.findById(answerId);
       model.addAttribute("questionId", questionId);
       model.addAttribute("answer",answer);
        return "answer/answer-update-form";
    }

    @RequestMapping("/deleteAnswer")
    public String deleteAnswer(@RequestParam("answerId") int answerId ,
                               @RequestParam("questionId") int questionId){
        answerService.deleteById(answerId);
        return "redirect:/question/showQuestion?questionId="+questionId;
    }
}
