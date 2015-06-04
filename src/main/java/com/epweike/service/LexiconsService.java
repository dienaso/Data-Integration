package com.epweike.service;

import java.util.List;

import com.epweike.mapper.LexiconsMapper;
import com.epweike.model.Lexicons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxp
 */
@Service
public class LexiconsService extends BaseService<Lexicons> {

	@Autowired
	private LexiconsMapper mapper;
	
	public int insertLexBatch(List<Lexicons> list) {
		return mapper.insertLexBatch(list);
	}
	
}
