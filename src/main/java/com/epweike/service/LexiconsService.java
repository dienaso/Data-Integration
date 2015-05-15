package com.epweike.service;

import java.util.List;

import com.epweike.mapper.LexiconsMapper;
import com.epweike.model.Lexicons;
import com.epweike.model.PageModel;

import org.apache.ibatis.session.RowBounds;
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
	
	public List<Lexicons> selectAll(PageModel<Lexicons> pageModel) {
		return mapper.selectAll(new RowBounds(pageModel.getiDisplayStart(), pageModel.getiDisplayLength()));
	}
}
