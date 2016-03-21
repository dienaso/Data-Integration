package com.epweike.service;

import com.epweike.mapper.LexiconsMapper;
import com.epweike.model.LogsStat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxp
 */
@Service
public class LogsStatService extends BaseService<LogsStat> {

	@Autowired
	private LexiconsMapper mapper;
}
