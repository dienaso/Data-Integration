package com.epweike.service;

import java.util.List;

import com.epweike.mapper.LexiconMapper;
import com.epweike.model.Lexicon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxp
 */
@Service
public class LexiconService extends BaseService<Lexicon> {

	@Autowired
	private LexiconMapper mapper;
	
	public int insertLexBatch(List<Lexicon> list) {
		return mapper.insertLexBatch(list);
	}
}
