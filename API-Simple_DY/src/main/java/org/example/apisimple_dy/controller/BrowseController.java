package org.example.apisimple_dy.controller;

import org.example.apisimple_dy.commonIO.Result;
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

    @PostMapping("/putLikes")
    public Result<?> putLikes(@RequestBody Video video, @PathVariable Integer userID){
        if (video.getVideoID() == null){
            throw new RuntimeException("数据不能为空");
        }
        try {
            int result = browseService.likesOn(video, userID);
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

    @GetMapping("/videoCommand")
    public Result<?> getCommandVideos(@PathVariable Integer userID){
        try {
            return Result.success(browseService.videoCommand(userID));
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

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
