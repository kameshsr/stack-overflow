package com.mountblue.stackoverflow.controller;

import com.mountblue.stackoverflow.model.Answer;
import com.mountblue.stackoverflow.model.Question;
import com.mountblue.stackoverflow.model.QuestionComment;
import com.mountblue.stackoverflow.model.User;
import com.mountblue.stackoverflow.repository.AnswerRepository;
import com.mountblue.stackoverflow.repository.QuestionCommentRepository;
import com.mountblue.stackoverflow.repository.QuestionRepository;
import com.mountblue.stackoverflow.service.AnswerService;
import com.mountblue.stackoverflow.service.QuestionCommentService;
import com.mountblue.stackoverflow.service.QuestionService;
import com.mountblue.stackoverflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    private final QuestionCommentService questionCommentService;

    @Autowired
    private final QuestionRepository questionRepository;

    @Autowired
    private QuestionCommentRepository questionCommentRepository;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    int oldest = 0;

    public QuestionController(QuestionService questionService, QuestionCommentService questionCommentService, QuestionRepository questionRepository, QuestionCommentRepository questionCommentRepository, AnswerService answerService, UserService userService) {
        this.questionService = questionService;
        this.questionCommentService = questionCommentService;
        this.questionRepository = questionRepository;
        this.questionCommentRepository = questionCommentRepository;
        this.answerService = answerService;
        this.userService = userService;
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
            return "redirect:/question/showQuestion?questionId="+questionId + "&oldest=" + oldest;
        }
    }

    @PostMapping("/updateQuestionData")
    public String updateQuestionData(@ModelAttribute("question") @Valid Question question,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/question/showFormForQuestionUpdate?error";
        } else {
            questionService.saveQuestion(question);
            return "redirect:/question/showQuestion?questionId="+question.getId() + "&oldest=" + oldest;
        }
    }

    @GetMapping("/showFormForQuestionUpdate")
    String showFormForQuestionUpdate(@RequestParam("questionId") int questionId,Model model){
        Question question=questionService.getQuestion(questionId);
        model.addAttribute("question",question);
        return "question/update-question-form";
    }

    @RequestMapping("/showQuestion")
    public String showQuestion(Model model, @RequestParam("questionId") int questionId
    , @RequestParam("userEmail") String userEmail, @RequestParam("oldest") int oldest) {

        User user = userService.getUserByEmail(userEmail);
        Question question = questionService.getQuestion(questionId);
        List<QuestionComment> questionComments= questionCommentRepository.findByQuestionId(questionId);
        QuestionComment questionComment = new QuestionComment();
        Answer answer = new Answer();
        List<Answer> answers;
        if (oldest == 1) {
            answers = answerService.findSortedAnswerByTimeStamp(questionId);
        } else if(oldest == 0) {
            answers = answerService.findSortedAnswerByVotes(questionId);
        } else if(oldest == 3) {
            answers = answerService.findByQuestionId(questionId);
        } else {
            answers = answerService.findByQuestionId(questionId);
        }
        model.addAttribute("questionComment", questionComment);
        model.addAttribute("questionComments", questionComments);
        model.addAttribute("question", question);
        model.addAttribute("answer", answer);
        model.addAttribute("answers", answers);
        model.addAttribute("user", user);
        return "question/show-question";
    }

    @RequestMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam("questionId") int questionId) {
        questionService.deleteQuestionById(questionId);
        return "redirect:/user/showHomePage";
    }

    @GetMapping("/showAllQuestion")
    public String viewQuestionList(Model model, @RequestParam("userEmail") String userEmail) {
        //User user = userService.getUserByEmail(userEmail);
        model.addAttribute("userEmail", userEmail);
        List<Question> listQuestion = questionService.getAllQuestions();
        model.addAttribute(("listQuestion"), listQuestion);
        return "question/question-list";
    }

    @GetMapping("/showAllQuestionForNonLoggedInUser")
    public String viewPostsList(Model model) {
        List<Question> listQuestion = questionService.getAllQuestions();
        model.addAttribute(("listQuestion"), listQuestion);
        return "question/question-list";
    }
}
