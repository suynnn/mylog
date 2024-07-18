package org.mylog.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.comment.domain.Comment;
import org.mylog.domain.comment.dto.CommentDto;
import org.mylog.domain.comment.dto.CommentRegisterDto;
import org.mylog.domain.comment.service.CommentService;
import org.mylog.global.response.Message;
import org.mylog.global.response.ResponseStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/register")
    public ResponseEntity<Message> registerComment(@RequestBody CommentRegisterDto commentRegisterDto) {

        Comment comment = commentService.saveComment(commentRegisterDto);
        CommentDto commentDto = new CommentDto(comment);

        Message message = new Message();

        message.setResponseStatus(ResponseStatus.OK);
        message.setMessage("성공 코드");
        message.setData(commentDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Message> getCommentsByPostId(
            @RequestParam("postId") Long postId,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        Page<CommentDto> commentPage = commentService.getCommentsByPostId(postId, page, size);

        Message message = new Message();
        message.setResponseStatus(ResponseStatus.OK);
        message.setMessage("성공 코드");
        message.setData(commentPage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteComment(@PathVariable("id")Long id) {
        boolean deleteSuccess = commentService.deleteComment(id);

        Message message = new Message();
        message.setResponseStatus(ResponseStatus.OK);
        message.setMessage("성공 코드");
        message.setData(deleteSuccess);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
