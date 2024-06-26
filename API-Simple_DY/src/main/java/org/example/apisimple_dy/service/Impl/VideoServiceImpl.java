package org.example.apisimple_dy.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.apisimple_dy.entity.Video;
import org.example.apisimple_dy.mapper.BrowseMapper;
import org.example.apisimple_dy.mapper.VideoMapper;
import org.example.apisimple_dy.service.VideoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    private final BrowseMapper browseMapper;

    public VideoServiceImpl(VideoMapper videoMapper, BrowseMapper browseMapper){
        this.baseMapper = videoMapper;
        this.browseMapper = browseMapper;
    }
    @Override
    public boolean videoUpload(String sourcePath, String videoName, Video video) throws IOException {

//        if (uploadFile.isEmpty()){
//            throw new RuntimeException("上传文件不能为空");
//        }
        String dirPath = "C:\\Users\\林\\Desktop\\大三下\\Api\\第4次作业-简易抖音\\Videos\\" +
                            video.getAuthorID().toString();

        File dir = new File(dirPath);

        if (!dir.isDirectory()){
            dir.mkdirs();
        }

        Path sourceVideo = Paths.get(sourcePath);
        // 目标文件路径
        Path targetPath = Paths.get(dirPath + "\\" + videoName);


        try {
            // 移动文件到目标位置（如果目标文件已存在，则覆盖）
            Files.move(sourceVideo, targetPath, StandardCopyOption.REPLACE_EXISTING);
            File videoServer = new File(dir, videoName);

            //System.out.println("真实路径：" + fileServer.getAbsolutePath());


            video.setVideoPath(videoServer.getAbsolutePath());
            System.out.println(video.getVideoPath());
            int videoNum = this.baseMapper.getFIleNum(video.getAuthorID(), videoServer.getAbsolutePath());

            if (videoNum == 0){
                save(video);
                System.out.println("文件移动成功！");
                return true;
            } else {
                throw new RuntimeException("本视频已经存在");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean deleteVideo(Integer userID, Integer videoID) {
        try {
            // 先删除 browse 表中的记录
            int deleteBrowseCount = browseMapper.deleteBrowseByVideoId(videoID);
            if (deleteBrowseCount < 1) {
                throw new RuntimeException("删除 browse 表中的记录失败");
            }

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
