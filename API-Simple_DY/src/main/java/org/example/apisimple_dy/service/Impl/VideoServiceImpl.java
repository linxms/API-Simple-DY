package org.example.apisimple_dy.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public boolean videoUpload(MultipartFile uploadFile, Video video) throws IOException {

        if (uploadFile.isEmpty()){
            throw new RuntimeException("上传文件不能为空");
        }
        String dirPath = "C:\\Users\\林\\Desktop\\大三下\\Api\\第4次作业-简易抖音\\Videos\\" +
                            video.getAuthorID().toString();

        File dir = new File(dirPath);

        if (!dir.isDirectory()){
            dir.mkdirs();
        }

        try {
            String videoName = uploadFile.getOriginalFilename();

            File videoServer = new File(dir, videoName);

            //System.out.println("真实路径：" + fileServer.getAbsolutePath());

            uploadFile.transferTo(videoServer);

            video.setVideoPath(videoServer.getAbsolutePath());
            int videoNum = this.baseMapper.getFIleNum(video.getAuthorID(), videoServer.getAbsolutePath());

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
        try {
            if (this.baseMapper.selectById(videoID).getAuthorID().equals(userID)){
                int result = this.baseMapper.deleteById(videoID);

                if (result < 1){
                    throw new RuntimeException("删除失败");
                }
                return true;
            } else {
                throw new RuntimeException("非本视频创作者，无法删除该视频");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *
     *
     * 本函数主要是分页查询一位author的全部视频作品
     * @param videoPage 用于分页查询
     * @param authorID 用于查询
     * @return 一位author的全部视频对象，pages里面存储了视频以及视频封面的文件地址
     */
    @Override
    public IPage<Video> selectMyVideo(Page<Video> videoPage, Integer authorID) {
        try {
            Video video = new Video(authorID);

            LambdaQueryWrapper<Video> wrapper=new LambdaQueryWrapper<>();
            //wrapper.like(video.getAuthorID()!=null, Video::getAuthorID, video.getAuthorID());
            wrapper.eq(Video::getAuthorID, authorID);

            IPage<Video> pages= this.baseMapper.selectPage(videoPage, wrapper);
            if(pages==null){
                throw new RuntimeException("未知异常");
            }
            return pages;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
