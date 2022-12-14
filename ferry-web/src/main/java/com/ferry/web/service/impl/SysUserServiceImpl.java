package com.ferry.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ferry.core.file.emums.FieldStatusEnum;
import com.ferry.core.file.util.DateTimeUtils;
import com.ferry.core.file.util.IdWorker;
import com.ferry.core.file.util.PoiUtils;
import com.ferry.core.http.Result;
import com.ferry.core.page.PageRequest;
import com.ferry.core.page.PageResult;
import com.ferry.server.admin.entity.SysRole;
import com.ferry.server.admin.entity.SysUser;
import com.ferry.server.admin.entity.SysUserRole;
import com.ferry.server.admin.mapper.SysRoleMapper;
import com.ferry.server.admin.mapper.SysUserMapper;
import com.ferry.server.admin.mapper.SysUserRoleMapper;
import com.ferry.server.blog.entity.BlUser;
import com.ferry.server.blog.mapper.BlUserMapper;
import com.ferry.web.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

@Service
public class SysUserServiceImpl extends ServiceImpl <SysUserMapper, SysUser> implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private BlUserMapper userMapper;
	@Autowired
	private IdWorker idWorker;
//	@Autowired
//	private BCryptPasswordEncoder encoder;

	@Transactional
	@Override
	public boolean save(SysUser record) {
		Long id = null;
		if(record.getId() == null || record.getId() == 0) {
			// 新增用户
			sysUserMapper.insert(record);
			id = record.getId();
		} else {
			// 更新用户信息
			sysUserMapper.updateById(record);
		}
		// 更新用户角色
		if(id != null && id == 0) {
			return true;
		}
		if(id != null) {
			for(SysUserRole sysUserRole:record.getUserRoles()) {
				sysUserRole.setUserId(id);
			}
		} else {
			sysUserRoleMapper.deleteById(record.getId());
		}
		for(SysUserRole sysUserRole:record.getUserRoles()) {
			sysUserRoleMapper.insert(sysUserRole);
		}
		return true;
	}

	@Override
	public int delete(SysUser record) {
		return sysUserMapper.deleteById(record.getId());
	}

	@Override
	public int delete(List<SysUser> records) {
		for(SysUser record:records) {
			delete(record);
		}
		return 1;
	}

	@Override
	public SysUser findById(Long id) {
		return sysUserMapper.selectById(id);
	}

	@Override
	public PageResult findPage(PageRequest pageRequest) {
		Page<SysUser> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
		String name = pageRequest.getParamValue(FieldStatusEnum.NAME);
		String email = pageRequest.getParamValue(FieldStatusEnum.EMAIL);
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.like(!Objects.isNull(name), SysUser.COL_NAME, name);
		queryWrapper.like(!Objects.isNull(email), SysUser.COL_EMAIL, email);
		Page<SysUser> userIPage = sysUserMapper.selectPage(page, queryWrapper);
		PageResult pageResult = new PageResult(userIPage);
		// 加载用户角色信息
		findUserRoles(pageResult);
		return pageResult;
	}

	/**
	 * 加载用户角色
	 * @param pageResult
	 */
	private void findUserRoles(PageResult pageResult) {
		List<?> content = pageResult.getContent();
		for(Object object:content) {
			SysUser sysUser = (SysUser) object;
			List<SysUserRole> userRoles = findUserRoles(sysUser.getId());
			sysUser.setUserRoles(userRoles);
			sysUser.setRoleNames(getRoleNames(userRoles));
		}
	}

	private String getRoleNames(List<SysUserRole> userRoles) {
		StringBuilder sb = new StringBuilder();
		for(Iterator<SysUserRole> iter=userRoles.iterator(); iter.hasNext();) {
			SysUserRole userRole = iter.next();
			SysRole sysRole = sysRoleMapper.selectById(userRole.getRoleId());
			if(sysRole == null) {
				continue ;
			}
			sb.append(sysRole.getRemark());
			if(iter.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	@Override
	public List<SysUserRole> findUserRoles(Long userId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq(SysUserRole.COL_USER_ID, userId);
		return sysUserRoleMapper.selectList(queryWrapper);
	}

	@Override
	public File createUserExcelFile(PageRequest pageRequest) {
		PageResult pageResult = findPage(pageRequest);
		return createUserExcelFile(pageResult.getContent());
	}

	public static File createUserExcelFile(List<?> records) {
		if (records == null) {
			records = new ArrayList<>();
		}
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		Row row0 = sheet.createRow(0);
		int columnIndex = 0;
		row0.createCell(columnIndex).setCellValue("No");
		row0.createCell(++columnIndex).setCellValue("ID");
		row0.createCell(++columnIndex).setCellValue("用户名");
		row0.createCell(++columnIndex).setCellValue("昵称");
		row0.createCell(++columnIndex).setCellValue("机构");
		row0.createCell(++columnIndex).setCellValue("角色");
		row0.createCell(++columnIndex).setCellValue("邮箱");
		row0.createCell(++columnIndex).setCellValue("手机号");
		row0.createCell(++columnIndex).setCellValue("状态");
		row0.createCell(++columnIndex).setCellValue("头像");
		row0.createCell(++columnIndex).setCellValue("创建人");
		row0.createCell(++columnIndex).setCellValue("创建时间");
		row0.createCell(++columnIndex).setCellValue("最后更新人");
		row0.createCell(++columnIndex).setCellValue("最后更新时间");
		for (int i = 0; i < records.size(); i++) {
			SysUser user = (SysUser) records.get(i);
			Row row = sheet.createRow(i + 1);
			for (int j = 0; j < columnIndex + 1; j++) {
				row.createCell(j);
			}
			columnIndex = 0;
			row.getCell(columnIndex).setCellValue(i + 1);
			row.getCell(++columnIndex).setCellValue(user.getId());
			row.getCell(++columnIndex).setCellValue(user.getName());
			row.getCell(++columnIndex).setCellValue(user.getNickName());
			row.getCell(++columnIndex).setCellValue(user.getDeptName());
			row.getCell(++columnIndex).setCellValue(user.getRoleNames());
			row.getCell(++columnIndex).setCellValue(user.getEmail());
			row.getCell(++columnIndex).setCellValue(user.getStatus());
			row.getCell(++columnIndex).setCellValue(user.getAvatar());
			row.getCell(++columnIndex).setCellValue(user.getCreateBy());
			row.getCell(++columnIndex).setCellValue(DateTimeUtils.getDateTime(user.getCreateTime()));
			row.getCell(++columnIndex).setCellValue(user.getLastUpdateBy());
			row.getCell(++columnIndex).setCellValue(DateTimeUtils.getDateTime(user.getLastUpdateTime()));
		}
		return PoiUtils.createExcelFile(workbook, "download_user");
	}

	public BlUser login(String mobile, String password) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq(BlUser.COL_MOBILE, mobile);
		queryWrapper.eq(BlUser.COL_PASSWORD, password);
		List<BlUser> userList = userMapper.selectList(queryWrapper);
		if(userList != null && !userList.isEmpty()){
			return userList.get(0);
		}
		return null;
	}

	@Override
	public Result add(BlUser user) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq(BlUser.COL_MOBILE, user.getMobile());
		List<BlUser> userList = userMapper.selectList(queryWrapper);
		if (userList != null && userList.size() > 0) {
			return Result.ok("手机号已注册");
		}
		user.setId(idWorker.nextId() + "");
		if (StringUtils.isEmpty(user.getPassword())) {
			return Result.error("密码为空");
		}
		user.setPassword(user.getPassword());
		user.setFollowcount(0);
		user.setFanscount(0);
		user.setOnline(0L);
		user.setRegdate(new Date());
		user.setUpdateTime(new Date());
		user.setLastdate(new Date());
		user.setAvatar("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.wmzhe.top%2Fuploadimg%2Fpc%2F26%2F26f3%2F26f344741948e2fb019089f99cc677fb.jpg&refer=http%3A%2F%2Fimg.wmzhe.top&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669081591&t=f821adb2e0c892620ea510d32e5afab0");
		userMapper.insert(user);
		return Result.ok("注册成功");
	}

}
