package com.epweike.mapper;

import java.util.List;

import com.epweike.model.Employ;

import tk.mybatis.mapper.common.Mapper;

public interface EmployMapper extends Mapper<Employ> {
	int insertBatch(List<Employ> record);
}