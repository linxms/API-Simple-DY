package org.example.apisimple_dy.service.Impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.apisimple_dy.entity.Browse;
import org.example.apisimple_dy.entity.Video;
import org.example.apisimple_dy.mapper.BrowseMapper;
import org.example.apisimple_dy.service.BrowseService;

public class BrowseServiceImpl extends ServiceImpl<BrowseMapper, Browse> implements BrowseService{
    @Override
    public IPage<Video> VideoCommand(Page<Video> videoPage) {
        return null;
    }

    @Override
    public int likesOn(Video video, Browse browse) {
        return 0;
    }
}
