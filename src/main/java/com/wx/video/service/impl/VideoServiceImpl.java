package com.wx.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.video.mapper.VideoMapper;
import com.wx.video.model.Video;
import com.wx.video.service.VideoService;
import com.wx.video.utils.PagedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @description 视频服务实现
 */
@Service
@CacheConfig(cacheNames = {"VideoServiceImpl"})
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;


    @Override
    @CacheEvict(allEntries = true)
    public void addVideo(Video video) {
        // 将video插入数据库中
        videoMapper.insert(video);
    }

    @Override
    // 方法执行后清空所有缓存
    @CacheEvict(allEntries = true)
    public void delVideo(String videoId) {
        // 根据id删除视频
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId);
        videoMapper.delete(queryWrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void updateVideoStatus(String videoId, Integer status) {
        // 更新条件
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId);
        // 更新内容
        Video video = videoMapper.selectOne(queryWrapper);
        video.setStatus(status);
        // 根据id选择性更新视频状态
        videoMapper.update(video, queryWrapper);
    }

    @Override
    @Cacheable(key = "methodName + #p0 + #p1")
    public PagedResult queryVideoList(Integer page, Integer pageSize) {
        // 执行分页
        Page<Video> pageRequest = new Page<>(page, pageSize);
        // 使用 MyBatis Plus 的 QueryWrapper 进行查询
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        // 执行分页查询
        IPage<Video> pageResult = videoMapper.selectPage(pageRequest, queryWrapper);

        // 创建自定义分页结果
        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageResult.getPages()); // 总页数
        pagedResult.setRows(pageResult.getRecords()); // 当前页的数据
        pagedResult.setRecords(pageResult.getTotal()); // 总记录数
        pagedResult.setPage(page); // 当前页数
        return pagedResult;
    }



}
