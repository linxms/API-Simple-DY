package org.example.apisimple_dy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.apisimple_dy.entity.Browse;
import org.example.apisimple_dy.entity.Video;

public interface BrowseService extends IService<Browse> {
    public IPage<Video> VideoCommand(Page<Video> videoPage);

    public int likesOn(Video video, Browse browse);

}
