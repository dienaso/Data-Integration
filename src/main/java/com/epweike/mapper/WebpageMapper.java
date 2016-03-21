package com.epweike.mapper;

import java.util.List;
import java.util.Map;

import com.epweike.model.Webpage;

import tk.mybatis.mapper.common.Mapper;

public interface WebpageMapper extends Mapper<Webpage> {
	
	List<Webpage> selectByPage(Map<String, Integer> map);
}