package org.example.apisimple_dy.service.Impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.apisimple_dy.entity.Browse;
import org.example.apisimple_dy.entity.Video;
import org.example.apisimple_dy.mapper.BrowseMapper;
import org.example.apisimple_dy.mapper.VideoMapper;
import org.example.apisimple_dy.service.BrowseService;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class BrowseServiceImpl extends ServiceImpl<BrowseMapper, Browse> implements BrowseService{

    private final BrowseMapper browseMapper;
    private final VideoMapper videoMapper;

    public BrowseServiceImpl(BrowseMapper browseMapper, VideoMapper videoMapper) {
        this.browseMapper = browseMapper;
        this.videoMapper = videoMapper;
    }
    @Override
    public int likesOn(Integer videoID, Integer userID) {
        try {
            int islike = this.baseMapper.findVideoIsLiked(videoID, userID);
            if (islike > 0){
                return 2; //视频已点赞
            }
            int i = videoMapper.incrementLikes(videoID);
            int putIsLike = this.browseMapper.putIsLiked(videoID, userID);
            if (i > 0 && putIsLike > 0){
                return i; // 点赞成功
            }else {
                throw new RuntimeException("更新异常");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Video> videoCommand(Integer userID) {
        try {
            List<Video> videos = videoMapper.getVideoCommand();
            
            if (videos.isEmpty()) {
                throw new RuntimeException("获取视频错误");
            }

            List<Integer> browses = browseMapper.findBrowsedVideo(userID);
            
            Iterator<Video> videoIterator = videos.iterator();
            Iterator<Integer> browseIterator = browses.iterator();
            while (videoIterator.hasNext()){
                if (browses.contains(videoIterator.next().getVideoID())){
                    videoIterator.remove();
                }
            }
            return videos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean browseVideo(Browse browse) {
        try {
            int i = this.baseMapper.findVideoIsBrowsed(browse.getVideoID(), browse.getUserID());
            if (i > 0){
                throw new RuntimeException("视频已阅");
            }
            save(browse);
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
