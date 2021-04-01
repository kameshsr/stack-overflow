package com.mountblue.stackoverflow.controller;

import com.mountblue.stackoverflow.model.Answer;
import com.mountblue.stackoverflow.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    private AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @RequestMapping("/showAnswer")
    public String showAnswer(Model model, @RequestParam("answerId") int answerId) {
        Answer answer = answerService.findById(answerId);
        model.addAttribute("answer", answer);
        return "answer/show-answer";
    }


    @GetMapping("/showFormForAdd")
        String answerData(Model model){
        Answer answer=new Answer();
        model.addAttribute("answer", answer);
        return "answer/answer-form";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute("answer") Answer answer) {
        answerService.save(answer);
        return "redirect:/user/showHomePage";
    }

    @GetMapping("/showFormForAnswerUpdate")
    String showFormForAnswerUpdate(@RequestParam("answerId") int answerId,Model model){
        Answer answer=answerService.findById(answerId);
       model.addAttribute("answer",answer);
        return "answer/answer-form";
    }

    @RequestMapping("/delete")
    public String deleteAnswer(@RequestParam("answerId") int answerId , BindingResult bindingResult){
       if(bindingResult.hasErrors()){
            return "redirect:/answer/showAnswer?error";
       }
       else{
           answerService.deleteById(answerId);
           return "redirect:/user/showHomePage";
       }
    }
}
