package com.mountblue.stackoverflow.controller;

public class QuestionCommentController {

    /*@Autowired
    private PostService postsService;

    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping("/posts/addComment/{postsId}/comment")
    public String addComments(@PathVariable("postsId") int postId, @ModelAttribute("newComment") Comment newComment) {
        Post currentPosts = postsService.getPostsById(postId);
        newComment.setPost(currentPosts);
        commentRepository.save(newComment);
        currentPosts.getComments().add(newComment);
        postsService.savePosts(currentPosts);
        return "redirect:/posts/{postsId}";
    }

    @RequestMapping(value = "posts/addComment/{id}", method = RequestMethod.GET)
    public String addComment(@PathVariable("id") int postsId, Model model) {
        model.addAttribute("comment", commentRepository.findAll());
        Comment newComment = new Comment();
        model.addAttribute("newComment", newComment);
        model.addAttribute("posts", postsService.getPostsById(postsId));
        return "AddComment";
    }

    @RequestMapping(value = "/posts/{postsId}/updateComments/{commentId}", method = RequestMethod.GET)
    public String updateComment(@PathVariable("postsId") int postsId,
                                @PathVariable("commentId") int commentId, Model model) {
        model.addAttribute("posts", postsService.getPostsById(postsId));
        model.addAttribute("newComment", commentRepository.findById(commentId));
        return "AddComment";
    }

    @RequestMapping(value = "/posts/{postsId}/deleteComments/{commentId}", method = RequestMethod.GET)
    public String deleteComment(@PathVariable("postsId") int postsId,
                                @PathVariable("commentId") int commentId, Model model) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        this.commentRepository.deleteById(commentId);
        return "redirect:/posts/{postsId}";
    }
*/
}
