package org.mylog.domain.tag.controller;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.tag.domain.Tag;
import org.mylog.domain.tag.dto.TagRegisterDto;
import org.mylog.domain.tag.service.TagService;
import org.mylog.global.response.Message;
import org.mylog.global.response.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagApiController {

    private final TagService tagService;

    @PostMapping("/register")
    public ResponseEntity<Message> registerTag(@RequestBody TagRegisterDto tagRegisterDto) {

        Tag tag = tagService.saveTag(tagRegisterDto.getName());
        Message message = new Message();

        message.setResponseStatus(ResponseStatus.OK);
        message.setMessage("성공 코드");
        message.setData(tag);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
