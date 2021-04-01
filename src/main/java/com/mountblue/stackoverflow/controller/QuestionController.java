package com.mountblue.stackoverflow.controller;

import com.mountblue.stackoverflow.model.Question;
import com.mountblue.stackoverflow.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            QuestionService.saveQuestion(question);
            return "redirect:/question/showQuestion?success";
        }
    }


    /*@GetMapping("/listUnPublishedPosts")
    public String listUnPublishedPosts(Model model) {
        model.addAttribute("listPosts", postsService.getAllUnPublishedPosts());
        return "UnPublishedPosts";
    }

    @GetMapping("/posts/showNewPostsForm")
    public String showNewPostsForm(Model model) {
        Post posts = new Post();
        model.addAttribute("posts", posts);
        return "NewPosts";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable("id") int postId, Model model) {
        model.addAttribute("posts", postsService.getPostsById(postId));
        model.addAttribute("comments", commentRepository.findByPostId(postId));
        return "ViewPost";
    }

    @PostMapping("/posts/savePosts")
    public String savePosts(@ModelAttribute("posts") Post posts) {

        postsService.savePosts(posts);
        String tag = posts.getTag();
        String[] listTag = tag.split(",");
        for (String tags : listTag) {
            Tag tag1 = new Tag(tags);
            tagRepository.save(tag1);
            posts.getTags().add(tag1);
        }
        postsService.savePosts(posts);
        return "redirect:/posts/list";
    }

    @GetMapping("/posts/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        Post posts = postsService.getPostsById(id);
        model.addAttribute("posts", posts);
        return "UpdatePosts";
    }

    @GetMapping("/posts/deletePosts/{id}")
    public String deletePosts(@PathVariable(value = "id") int id) {
        this.postsService.deletePostsById(id);
        return "redirect:/posts/list";
    }*/
}
