package com.epweike.mapper;

import java.util.List;

import com.epweike.model.Lexicon;
import com.github.abel533.mapper.Mapper;

public interface LexiconMapper extends Mapper<Lexicon> {
	
	int insertLexBatch(List<Lexicon> record);
	
}