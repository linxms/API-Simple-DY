package org.example.apisimple_dy.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.apisimple_dy.entity.Video;
import org.example.apisimple_dy.mapper.VideoMapper;
import org.example.apisimple_dy.service.VideoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    public VideoServiceImpl(VideoMapper videoMapper){
        this.baseMapper = videoMapper;

    }
    @Override
    public boolean videoUpload(MultipartFile uploadVideo, Video video) throws IOException {
        String dirPath = "C:\\Users\\林\\Desktop\\大三下\\Api\\第4次作业-简易抖音\\Videos\\" +
                            video.getAuthorID().toString() + "\\";

        File dir = new File(dirPath);

        if (!dir.isDirectory()){
            dir.mkdir();
        }

        try {
            String filename = uploadVideo.getOriginalFilename();

            File fileServer = new File(dir, filename);
            //System.out.println("真实路径：" + fileServer.getAbsolutePath());

            uploadVideo.transferTo(fileServer);

            video.setFile_location(fileServer.getAbsolutePath());
            int videoNum = this.baseMapper.getFIleNum(video.getAuthorID(), fileServer.getAbsolutePath());

            if (videoNum == 0){
                save(video);
                return true;
            } else {
                throw new RuntimeException("本视频已经存在");
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean deleteVideo(Integer userID, Integer videoID) {
        return false;
    }

    @Override
    public IPage<Video> selectMyVideo(Page<Video> videoPage, Video video) {
        return null;
    }
}
