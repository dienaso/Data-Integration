package com.epweike.service;

import java.util.List;

import com.epweike.mapper.DicBasicMapper;
import com.epweike.model.DicBasic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxp
 */
@Service
public class DicBasicService extends BaseService<DicBasic> {

	@Autowired
	private DicBasicMapper mapper;
	
	public int insertDicBasicBatch(List<DicBasic> list) {
		return mapper.insertDicBasicBatch(list);
	}
}
