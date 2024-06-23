package org.example.apisimple_dy.controller;

import io.micrometer.core.annotation.Timed;
import org.example.apisimple_dy.commonIO.Result;
import org.example.apisimple_dy.config.JWTUtil;
import org.example.apisimple_dy.entity.Browse;
import org.example.apisimple_dy.entity.Video;
import org.example.apisimple_dy.service.BrowseService;
import org.example.apisimple_dy.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/browse")
public class BrowseController {
    @Autowired
    private BrowseService browseService;

    private JWTUtil jwtUtil;
    @Timed(value = "browseController.post")
    @PostMapping("/putLikes")
    public Result<?> putLikes(@RequestHeader("Authorization") String token, @RequestParam("videoID") Integer videoID, @RequestParam("userID") Integer userID){
        if (videoID == null){
            throw new RuntimeException("数据不能为空");
        }
        try {
            Integer tokenUserID = jwtUtil.getUserIdFromToken(token);
            if (!userID.equals(tokenUserID)) {
                return Result.fail("用户ID不匹配");
            }
            int result = browseService.likesOn(videoID, userID);
            if (result == 1){
                return Result.success();
            }else if (result == 2){
                return Result.fail("视频已点赞");
            }
            return Result.fail("更新异常");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @Timed(value = "browseController.get")
    @GetMapping("/videoCommand")
    public Result<?> getCommandVideos(@RequestParam("userID") Integer userID){
        try {
            return Result.success(browseService.videoCommand(userID));
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @Timed(value = "browseController.post")
    @PostMapping("/videoBrowse")
    public Result<?> videoBrowse(@RequestBody Browse browse){
        if (browse.getVideoID() == null){
            throw new RuntimeException("数据不能为空");
        }
        try {
            browseService.browseVideo(browse);
            return Result.success();
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
}
