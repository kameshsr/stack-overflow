package com.mountblue.stackoverflow.controller;

import com.mountblue.stackoverflow.model.*;
import com.mountblue.stackoverflow.repository.QuestionCommentRepository;
import com.mountblue.stackoverflow.repository.QuestionRepository;
import com.mountblue.stackoverflow.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    private QuestionCommentService questionCommentService;

    private QuestionRepository questionRepository;

    private final QuestionCommentRepository questionCommentRepository;

    private final AnswerService answerService;

    private final UserService userService;

    private final TagService tagService;

    int oldest = 0;

    public QuestionController(QuestionService questionService, QuestionCommentService questionCommentService, QuestionRepository questionRepository, QuestionCommentRepository questionCommentRepository, AnswerService answerService, UserService userService, TagService tagService) {
        this.questionService = questionService;
        this.questionCommentService = questionCommentService;
        this.questionRepository = questionRepository;
        this.questionCommentRepository = questionCommentRepository;
        this.answerService = answerService;
        this.userService = userService;
        this.tagService = tagService;
    }

    @RequestMapping("/showQuestionForm")
    public String showQuestionForm(Model model, @RequestParam("userEmail") String userEmail) {
        Question question = new Question();
        model.addAttribute("question", question);
        model.addAttribute("userEmail", userEmail);
        return "/question/question-form";
    }

    @PostMapping("/saveQuestionData")
    public String saveQuestionData(@ModelAttribute("question") @Valid Question question,
                           @RequestParam("userEmail") String userEmail, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/question/showQuestionForm?error";
        } else {
            int questionId = question.hashCode();
            question.setId(questionId);
            User user = userService.getUserByEmail(userEmail);
            question.setReputation(question.getReputation()+user.getReputation());
            question.setEmail(user.getEmail());
            question.setUserName(user.getName());

            List<String> listTag = Arrays.asList(question.getTags().split(","));
            List<Tag> allTag = tagService.findAll();
            List<String> allTagList = new ArrayList<>();
            for(Tag currentTag:allTag) {
                allTagList.add(currentTag.getName());
            }
            for(String currentTag:listTag) {
                if (!allTagList.contains(currentTag)) {
                    System.out.println(currentTag);
                    Tag tag = new Tag(currentTag);
                    tagService.save(tag);
                    question.getTagList().add(tag);
                }
            }
            questionService.saveQuestion(question);
            return "redirect:/question/showQuestion?questionId="+questionId+"&userEmail="+userEmail+"&oldest="+oldest;
        }
    }

    @PostMapping("/updateQuestionData")
    public String updateQuestionData(@ModelAttribute("question") @Valid Question question,
                                     @RequestParam("userEmail") String userEmail, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/question/showFormForQuestionUpdate?error";
        } else {
            questionService.saveQuestion(question);
            return "redirect:/question/showQuestion?questionId="+question.getId()+"&userEmail="+userEmail+"&oldest="+oldest;
        }
    }

    @GetMapping("/showFormForQuestionUpdate")
    String showFormForQuestionUpdate(@RequestParam("questionId") int questionId,
                                     @RequestParam("userEmail") String userEmail, Model model){
        Question question=questionService.getQuestion(questionId);
        model.addAttribute("question",question);
        model.addAttribute("userEmail",userEmail);
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

        List<String> tagList = Arrays.asList(question.getTags().split(","));
        Answer guestAnswer = new Answer();

        model.addAttribute("guestAnswer", guestAnswer);
        model.addAttribute("tagList", tagList);
        model.addAttribute("questionComment", questionComment);
        model.addAttribute("questionComments", questionComments);
        model.addAttribute("question", question);
        model.addAttribute("answer", answer);
        model.addAttribute("answers", answers);
        model.addAttribute("user", user);
        return "question/show-question";
    }

    @RequestMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam("questionId") int questionId, @RequestParam("userEmail") String userEmail) {
        questionService.deleteQuestionById(questionId);
        return "redirect:/question/showAllQuestion?userEmail="+userEmail+"&oldest="+oldest;
    }

    @GetMapping("/showAllQuestion")
    public String viewQuestionList(@RequestParam("userEmail") String userEmail, Model model) {
        User user = userService.getUserByEmail(userEmail);
        model.addAttribute("user", user);
        List<Question> Questions = questionService.getAllQuestions();
        model.addAttribute(("listQuestion"), Questions);
        return "question/question-list";
    }

    @GetMapping("/showAllQuestionForNonLoggedInUser")
    public String viewPostsList(Model model) {
        List<Question> Questions = questionService.getAllQuestions();
        String userEmail = "guest@mail.com";
        User user = userService.getUserByEmail(userEmail);
        model.addAttribute("user", user);
        model.addAttribute(("listQuestion"), Questions);
        return "question/question-list";
    }

    @GetMapping("/filterQuestions")
    public String filterQuestions(@RequestParam("userEmail") String userEmail,
                                  @RequestParam("searchQuestion") String searchQuestion, Model model) {
        List<Question> Questions = questionService.getFilteredQuestions(searchQuestion);
        User user = userService.getUserByEmail(userEmail);
        model.addAttribute(("listQuestion"), Questions);
        model.addAttribute("user", user);
        return "question/question-list";
    }
}
