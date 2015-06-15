package com.epweike.mapper;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

import com.epweike.model.Lexicons;

public interface LexiconsMapper extends Mapper<Lexicons> {
	
	int insertLexBatch(List<Lexicons> record);
	
}