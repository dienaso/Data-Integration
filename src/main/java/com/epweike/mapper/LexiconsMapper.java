package com.epweike.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.epweike.model.Lexicons;
import com.github.abel533.mapper.Mapper;

public interface LexiconsMapper extends Mapper<Lexicons> {
	
	int insertLexBatch(List<Lexicons> record);
	
	List<Lexicons> selectAll(RowBounds rowBounds);
}