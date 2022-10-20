package com.ferry.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ferry.core.file.emums.StateEnums;
import com.ferry.server.navigate.entity.NavInfo;
import com.ferry.server.navigate.entity.NavType;
import com.ferry.server.navigate.mapper.NavInfoMapper;
import com.ferry.server.navigate.mapper.NavTypeMapper;
import com.ferry.web.response.NavAggregatesResponse;
import com.ferry.web.response.NavInfoResponse;
import com.ferry.web.response.NavTypeResponse;
import com.ferry.web.service.NavService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: 摆渡人
 * @Date: 2021/5/7
 */
@Service
public class NavServiceImpl extends ServiceImpl <NavTypeMapper, NavType> implements NavService {

    @Resource
    private NavTypeMapper navTypeMapper;

    @Resource
    private NavInfoMapper navInfoMapper;



    @Override
    public List<NavAggregatesResponse> findAllNav() {
        QueryWrapper<NavType> queryWrapper = new QueryWrapper<NavType>();
        queryWrapper.orderByAsc(NavType.SORT);
        List<NavType> navTypeList = navTypeMapper.selectList(queryWrapper);
        List<Integer> typeIds = navTypeList.stream().map(NavType::getId).collect(Collectors.toList());

        QueryWrapper<NavInfo> queryInfo = new QueryWrapper<NavInfo>();
        queryInfo.in(NavInfo.NAV_TYPE_ID, typeIds);
        List<NavInfo> navInfoList = navInfoMapper.selectList(queryInfo);

        Map<Integer, List<NavInfo>> navMap = navInfoList.stream().collect(Collectors.groupingBy(NavInfo::getNavTypeId));

        List<NavAggregatesResponse> responses = new ArrayList<>();


        navTypeList.stream().forEach(item -> {
            List<NavInfo> navInfos = navMap.get(item.getId());

            if (Objects.nonNull(item.getNavParentTypeId())) {
                return;
            }
            NavAggregatesResponse aggregatesResponse = new NavAggregatesResponse();
            aggregatesResponse.setNavTypeName(item.getNavTypeName());
            aggregatesResponse.setId(String.valueOf(item.getId()));
            aggregatesResponse.setCreateTime(item.getCreateTime());
            aggregatesResponse.setIcon(item.getIcon());
            aggregatesResponse.setSort(String.valueOf(item.getSort()));
            if (Objects.nonNull(navInfos) && !navInfos.isEmpty()) {
                List<NavInfoResponse> navInfoResponses = navInfoListCover(navInfos);
                aggregatesResponse.setNavInfoList(navInfoResponses);
            }

            responses.add(aggregatesResponse);
        });

        Map<Integer, List<NavType>> childrenNavTypeMap = navTypeList.stream()
                .filter(item -> Objects.nonNull(item.getNavParentTypeId()))
                .collect(Collectors.groupingBy(NavType::getNavParentTypeId));

        responses.stream().forEach(type -> {
            List<NavType> navTypes = childrenNavTypeMap.get(Integer.valueOf(type.getId()));
            if (Objects.isNull(navTypes) ||navTypes.isEmpty()) {
                return;
            }

            List<NavTypeResponse> typeResponseList = new ArrayList<>();

            for (NavType navType : navTypes) {
                NavTypeResponse typeResponse = new NavTypeResponse();
                typeResponse.setId(navType.getId());
                typeResponse.setCreateTime(navType.getCreateTime());
                typeResponse.setNavTypeName(navType.getNavTypeName());

                List<NavInfo> navInfos = navMap.get(navType.getId());
                if (Objects.nonNull(navInfos) && !navInfos.isEmpty()) {
                    List<NavInfoResponse> navInfoResponses = navInfoListCover(navInfos);
                    typeResponse.setNavInfoList(navInfoResponses);
                }
                typeResponseList.add(typeResponse);
            }

            type.setChildren(typeResponseList);

        });

        return responses.stream().filter(item-> Objects.nonNull(item.getNavInfoList()) && !item.getNavInfoList().isEmpty()).collect(Collectors.toList());
    }

    @Override
    public String insert(NavInfo navInfo) {
        if (Objects.isNull(navInfo.getNavTypeId())) {
            navInfo.setNavTypeId(1);
        }
        QueryWrapper<NavInfo> queryWrapper = new QueryWrapper<NavInfo>();
        queryWrapper.eq(NavInfo.NAV_TAG, navInfo.getNavTag());
        List<NavInfo> navTypeList = navInfoMapper.selectList(queryWrapper);
        if (navTypeList.size() > 100) {
            throw new RuntimeException("已超过限制");
        }
        navInfoMapper.insert(navInfo);
        return StateEnums.REQUEST_SUCCESS.getMsg();
    }

    private NavAggregatesResponse navCover(NavType navType) {
        NavAggregatesResponse navAggregatesResponse = new NavAggregatesResponse();
        navAggregatesResponse.setId(String.valueOf(navType.getId()));
        navAggregatesResponse.setNavTypeName(navType.getNavTypeName());
        return navAggregatesResponse;
    }

    private List<NavInfoResponse> navInfoListCover(List<NavInfo> navInfos) {
        List<NavInfoResponse> responseList = new ArrayList<>();
        for (NavInfo info :navInfos) {
            NavInfoResponse response = new NavInfoResponse();
            response.setId(info.getId());
            response.setCreateTime(info.getCreateTime());
            response.setUpdateTime(info.getUpdateTime());
            response.setNavDesc(info.getNavDesc());
            response.setNavLogo(info.getNavLogo());
            response.setNavName(info.getNavName());
            response.setNavTypeId(info.getNavTypeId());
            response.setNavUrl(info.getNavUrl());
            responseList.add(response);
        }
        return responseList;
    }
}
