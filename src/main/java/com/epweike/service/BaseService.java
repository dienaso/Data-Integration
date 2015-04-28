package com.epweike.service;

import com.epweike.model.PageModel;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuxp
 */
@Service
public abstract class BaseService<T> {

	@Autowired
	protected Mapper<T> mapper;

	public int insert(T record) {
		return mapper.insert(record);
	}

	public int delete(Object key) {
		return mapper.deleteByPrimaryKey(key);
	}

	public int update(T record) {
		return mapper.updateByPrimaryKey(record);
	}

	public T get(int key) {
		return mapper.selectByPrimaryKey(key);
	}

	public int count(T record) {
		return mapper.selectCount(record);
	}
	
	public List<T> select(T record) {
		return mapper.select(record);
	}

	public T selectOne(T record) {
		List<T> list = mapper.select(record);
		if(list.size() == 1) {
			return list.get(0);
		} else if (list.size() > 1) {
            throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
	}

	/**
	 * 单表分页查询
	 *
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<T> selectPage(T record, PageModel<T> pageModel) {
		//每页显示条数
    	int pageSize = pageModel.getiDisplayLength();
    	//当前页码
    	int pageNum = (pageModel.getiDisplayStart() / pageSize) + 1;
    	
		PageHelper.startPage(pageNum, pageSize);
		// Spring4支持泛型注入
		return mapper.select(record);
	}
}
