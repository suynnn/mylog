package org.mylog.domain.series.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.series.domain.Series;
import org.mylog.domain.series.dto.SeriesDto;
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
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/series")
public class SeriesApiController {

    private final SeriesService seriesService;

    @PostMapping("/register")
    public ResponseEntity<Message> registerSeries(@RequestBody SeriesRegisterDto seriesRegisterDto) {

        seriesService.registerSeries(seriesRegisterDto);
        Message message = new Message();

        message.setResponseStatus(ResponseStatus.OK);
        message.setMessage("성공 코드");
        message.setData(seriesRegisterDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/{blogId}/all")
    public ResponseEntity<Message> findAllSeries(@PathVariable("blogId") Long blogId) {

        List<Series> seriesList = seriesService.findAllByBlogId(blogId);
        List<SeriesDto> seriesDtoList = seriesList.stream().map(SeriesDto::new).toList();

        Message message = new Message();

        message.setResponseStatus(ResponseStatus.OK);
        message.setMessage("성공 코드");
        message.setData(seriesDtoList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
