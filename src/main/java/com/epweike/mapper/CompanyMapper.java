package com.epweike.mapper;

import java.util.List;

import com.epweike.model.Company;

import tk.mybatis.mapper.common.Mapper;

public interface CompanyMapper extends Mapper<Company> {
	int insertBatch(List<Company> record);
}