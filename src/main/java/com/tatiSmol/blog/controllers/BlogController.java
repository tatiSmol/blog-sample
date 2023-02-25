package com.tatiSmol.blog.controllers;

import com.tatiSmol.blog.models.Post;
import com.tatiSmol.blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("blog/add")
    public String addPost(@ModelAttribute Post post) {
        post.setCreationDate(new Date());
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("blog/{id}")
    public String getFullText(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("blog/{id}/edit")
    public String editPost(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("blog/{id}/edit")
    public String saveEditedPost(@ModelAttribute Post updatedPost, @PathVariable(value = "id") long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post oldPost = post.get();
            oldPost.setTitle(updatedPost.getTitle());
            oldPost.setPreview(updatedPost.getPreview());
            oldPost.setText(updatedPost.getText());
            oldPost.setCreationDate(new Date());
            postRepository.save(oldPost);
        }

        return "redirect:/blog/" + id;
    }
}
