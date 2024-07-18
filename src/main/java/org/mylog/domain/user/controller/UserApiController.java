package org.mylog.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.user.service.UserService;
import org.mylog.global.response.Message;
import org.mylog.global.response.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Message> existsUsername(@PathVariable("username") String username) {
        Boolean isUsernameExist = userService.existsUserId(username);

        Message message = new Message();

        message.setResponseStatus(ResponseStatus.OK);
        message.setMessage("성공 코드");
        message.setData(isUsernameExist);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Message> existsEmail(@PathVariable("email") String email) {
        Boolean isEmailExist = userService.existsEmail(email);

        Message message = new Message();

        message.setResponseStatus(ResponseStatus.OK);
        message.setMessage("성공 코드");
        message.setData(isEmailExist);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
