package org.mylog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.post.domain.Post;
import org.mylog.domain.post.dto.PostDto;
import org.mylog.domain.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final PostService postService;

    @GetMapping
    public String homeLogin(Model model) {

        List<Post> posts = postService.findAllPostByDesc().stream()
                .filter(post -> !post.getIsDeleted())
                .filter(post -> !post.getIsPrivate())
                .filter(post -> !post.getIsTemp()).toList();

        List<PostDto> postDtoList = posts.stream()
                .map(PostDto::new).toList();

        model.addAttribute("postDtoList", postDtoList);

        return "index";
    }
}
