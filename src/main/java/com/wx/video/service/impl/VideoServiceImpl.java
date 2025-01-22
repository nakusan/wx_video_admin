package com.wx.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wx.video.enums.VideoStatusEnum;
import com.wx.video.mapper.CategoryMapper;
import com.wx.video.mapper.VideoMapper;
import com.wx.video.model.Category;
import com.wx.video.model.Video;
import com.wx.video.model.vo.VideoQueryVO;
import com.wx.video.model.vo.VideoVO;
import com.wx.video.service.VideoService;
import com.wx.video.utils.PagedResult;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 视频服务实现
 */
@Service
@CacheConfig(cacheNames = {"VideoServiceImpl"})
public class VideoServiceImpl implements VideoService {

    @Resource
    private VideoMapper videoMapper;
    @Resource
    private CategoryMapper categoryMapper;


    @Override
//    @CacheEvict(allEntries = true)
    public void addVideo(Video video) {
        // 将video插入数据库中
        videoMapper.insert(video);
    }

    @Override
    // 方法执行后清空所有缓存
//    @CacheEvict(allEntries = true)
    public void delVideo(String videoId) {
        // 根据id删除视频
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId);
        // 更新内容
        Video video = videoMapper.selectOne(queryWrapper);
        video.setIsDelete(VideoStatusEnum.DELETED.getValue());
        videoMapper.update(video, queryWrapper);
    }

    @Override
    public void updateVideo(Video video) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", video.getVideoId());
        videoMapper.update(video, queryWrapper);
    }

    @Override
//    @CacheEvict(allEntries = true)
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
//    @Cacheable(key = "methodName + #p0 + #p1")
    public PagedResult queryVideoList(VideoQueryVO queryVO, Integer page, Integer pageSize) {
        // 计算偏移量
        int offset = (page - 1) * pageSize;
        // 查询分页数据
        List<VideoVO> videoList = videoMapper.queryAllVideos(queryVO, offset, pageSize);
        // 格式化价格
        List<VideoVO> formattedVideoList = videoList.stream()
                .peek(video -> {
                    if (video.getPrice() != null) {
                        BigDecimal priceInYuan = new BigDecimal(video.getPrice()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                        video.setPriceYuan(priceInYuan.toString());
                    }
                }).collect(Collectors.toList());
        // 查询总记录数
        int totalRecords = videoMapper.countAllVideos();

        // 创建自定义分页结果
        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(totalRecords / pageSize + (totalRecords % pageSize > 0 ? 1 : 0)); // 总页数
        pagedResult.setRows(formattedVideoList); // 当前页的数据
        pagedResult.setRecords(totalRecords); // 总记录数
        pagedResult.setPage(page); // 当前页数
        return pagedResult;
    }

    @Override
    public Video queryVideoById(String videoId) {
        // 根据id查询视频
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId);
        queryWrapper.eq("is_delete", VideoStatusEnum.ACTIVE.getValue());
        return videoMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Category> getAllCategories() {
        // 根据id查询视频
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("category_id");
        return categoryMapper.selectList(queryWrapper);
    }
}
