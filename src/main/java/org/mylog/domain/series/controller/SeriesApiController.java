package org.mylog.domain.series.controller;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.series.domain.Series;
import org.mylog.domain.series.dto.SeriesRegisterDto;
import org.mylog.domain.series.service.SeriesService;
import org.mylog.global.response.Message;
import org.mylog.global.response.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/series")
public class SeriesApiController {

    private final SeriesService seriesService;

    @PostMapping("/register")
    public ResponseEntity<Message> registerSeries(@RequestBody SeriesRegisterDto seriesRegisterDto) {

        Series series = seriesService.registerSeries(seriesRegisterDto);
        Message message = new Message();

        message.setResponseStatus(ResponseStatus.OK);
        message.setMessage("성공 코드");
        message.setData(series);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/{blogId}/all")
    public ResponseEntity<Message> findAllSeries(@PathVariable("blogId") Long blogId) {

        List<Series> seriesList = seriesService.findAllByBlogId(blogId);
        Message message = new Message();

        message.setResponseStatus(ResponseStatus.OK);
        message.setMessage("성공 코드");
        message.setData(seriesList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
