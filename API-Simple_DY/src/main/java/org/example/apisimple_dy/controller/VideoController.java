package org.example.apisimple_dy.controller;

import org.example.apisimple_dy.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    VideoService videoService;

}
