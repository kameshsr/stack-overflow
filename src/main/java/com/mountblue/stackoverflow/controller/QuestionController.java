package com.mountblue.stackoverflow.controller;

import com.mountblue.stackoverflow.model.Question;
import com.mountblue.stackoverflow.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping("/showQuestionForm")
    public String showQuestionForm(Model model) {
        Question question = new Question();
        model.addAttribute("question", question);
        return "/question/question-form";
    }

    @PostMapping("/saveQuestionData")
    public String saveQuestionData(@ModelAttribute("question") @Valid Question question,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/question/showQuestionForm?error";
        } else {
            int questionId = question.hashCode();
            question.setId(questionId);
            questionService.saveQuestion(question);
            return "redirect:/question/showQuestion?questionId="+questionId;
        }
    }

    @RequestMapping("/showQuestion")
    public String showQuestion(Model model, @RequestParam("questionId") int questionId) {
        Question question = questionService.getQuestion(questionId);
        model.addAttribute("question", question);
        return "question/show-question";
    }

    @RequestMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam("questionId") int questionId,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/question/show-question?error";
        } else {
            questionService.deleteQuestionById(questionId);
            return "redirect:/user/home";
        }
    }

    @RequestMapping("/updateQuestion")
    public String updateQuestion(@RequestParam("questionId") int questionId, Model model) {
        Question question = questionService.getQuestion(questionId);
        model.addAttribute("question", question);
        return "question/question-form";
    }
}
