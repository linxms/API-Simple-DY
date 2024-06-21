package org.example.apisimple_dy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.apisimple_dy.entity.Video;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VideoService extends IService<Video> {
    boolean videoUpload(MultipartFile[] uploadFiles, Video video) throws IOException;

    boolean deleteVideo(Integer userID, Integer videoID);

    IPage<Video> selectMyVideo(Page<Video> videoPage, Integer authorID);

}
