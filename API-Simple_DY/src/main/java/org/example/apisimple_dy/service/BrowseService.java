package org.example.apisimple_dy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.apisimple_dy.entity.Browse;
import org.example.apisimple_dy.entity.Video;

import java.util.List;

public interface BrowseService extends IService<Browse> {
    public int likesOn(Video video, Integer userID);

    public List<Video> videoCommand(Integer userID);

    public boolean browseVideo(Browse browse);
}
