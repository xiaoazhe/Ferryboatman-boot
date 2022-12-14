package com.ferry.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ferry.core.file.emums.FieldStatusEnum;
import com.ferry.core.file.emums.StateEnums;
import com.ferry.core.file.util.IdWorker;
import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import com.ferry.core.page.PageResult;
import com.ferry.server.blog.entity.BlLabel;
import com.ferry.server.blog.entity.BlMusic;
import com.ferry.server.blog.entity.BlUserLabel;
import com.ferry.server.blog.mapper.BlLabelMapper;
import com.ferry.server.blog.mapper.BlMusicMapper;
import com.ferry.server.blog.mapper.BlUserLabelMapper;
import com.ferry.web.service.LabelService;
import com.ferry.web.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 摆渡人
 * @Date: 2021/6/9
 */
@Service
public class LabelServiceImpl extends ServiceImpl <BlLabelMapper, BlLabel> implements LabelService {

    @Autowired
    private BlLabelMapper labelMapper;

    @Autowired
    private BlUserLabelMapper userLabelMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BlMusicMapper musicMapper;

    @Override
    public PageResult selectAllByUser(PageRequest pageRequest) {
        Page <BlLabel> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        String token = request.getHeader(FieldStatusEnum.HEARD).substring(7);
        Claims claims = jwtUtil.parseJWT(token);
        String userId = claims.getId();
        Map map = new HashMap();
        map.put(BlUserLabel.COL_UID, userId);
        List <BlUserLabel> userLabels= userLabelMapper.selectByMap(map);
        List<String > lids= userLabels.stream().map(BlUserLabel::getLid)
                .collect(Collectors.toList());
        QueryWrapper <BlLabel> queryWrapper = new QueryWrapper();
        queryWrapper.ne(BlLabel.COL_STATE, 0);
        Page <BlLabel> labelPage = labelMapper.selectPage(page, queryWrapper);
        for (BlLabel label: labelPage.getRecords()) {
            if (lids.contains(label.getId())) {
                label.setUserId(Integer.valueOf(userId));
            }
        }
        PageResult pageResult = new PageResult(labelPage);
        return pageResult;
    }

    @Override
    public List <BlLabel> toplist() {
        QueryWrapper <BlLabel> queryWrapper= new QueryWrapper <>();
        queryWrapper.eq(BlLabel.COL_STATE, 1);
        queryWrapper.orderByDesc(BlLabel.COL_COUNT);
        return labelMapper.selectList(queryWrapper).stream().limit(6).collect(Collectors.toList());
    }

    @Override
    public List<BlMusic> getMusicList() {
        QueryWrapper<BlMusic> queryWrapper = new QueryWrapper();
        queryWrapper.ne(BlMusic.COL_ENABLE, 1);
        queryWrapper.ne(BlMusic.COL_DELETED, 1);
        return musicMapper.selectList(queryWrapper);
    }
}
