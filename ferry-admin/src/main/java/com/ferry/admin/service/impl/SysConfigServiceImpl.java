package com.ferry.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ferry.admin.service.SysConfigService;
import com.ferry.core.file.emums.FieldStatusEnum;
import com.ferry.core.file.util.StringUtils;
import com.ferry.core.page.PageRequest;
import com.ferry.core.page.PageResult;
import com.ferry.server.admin.entity.SysConfig;
import com.ferry.server.admin.mapper.SysConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysConfigServiceImpl extends ServiceImpl <SysConfigMapper, SysConfig> implements SysConfigService {

	@Autowired
	private SysConfigMapper sysConfigMapper;

	@Override
	public boolean save(SysConfig record) {
		if(record.getId() == null || record.getId() == 0) {
			sysConfigMapper.insert(record);
			return true;
		}
		sysConfigMapper.updateById(record);
		return true;
	}

	@Override
	public int delete(SysConfig record) {
		return sysConfigMapper.deleteById(record.getId());
	}

	@Override
	public int delete(List<SysConfig> records) {
		for(SysConfig record:records) {
			delete(record);
		}
		return 1;
	}

	@Override
	public PageResult findPage(PageRequest pageRequest) {
		Page <SysConfig> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
		String label = pageRequest.getParamValue(FieldStatusEnum.LABEL);
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.like(!StringUtils.isBlank(label), SysConfig.COL_LABEL, label);
		Page<SysConfig> userIPage = sysConfigMapper.selectPage(page, queryWrapper);
		PageResult pageResult = new PageResult(userIPage);
		return pageResult;
	}

	@Override
	public List<SysConfig> findByLable(String lable) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq(!StringUtils.isBlank(lable), SysConfig.COL_LABEL, lable);
		return sysConfigMapper.selectList(queryWrapper);
	}

}
