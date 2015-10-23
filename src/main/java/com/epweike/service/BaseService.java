package com.epweike.service;

import com.epweike.model.PageModel;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

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

	public int deleteByPrimaryKey(Object key) {
		return mapper.deleteByPrimaryKey(key);
	}
	
	public int delete(T record) {
		return mapper.delete(record);
	}

	public int update(T record) {
		return mapper.updateByPrimaryKey(record);
	}
	
	public int updateBySelective(T record) {
		return mapper.updateByPrimaryKeySelective(record);
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
	
	public List<T> select(T record, PageModel<T> pageModel) {
		return mapper.selectByRowBounds(record, new RowBounds(pageModel.getiDisplayStart(), pageModel.getiDisplayLength()));
	}

	public T selectOne(T record) {
		return mapper.selectOne(record);
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
    	int limit = pageModel.getiDisplayLength();
    	//偏移
    	int offset = pageModel.getiDisplayStart();
    	
		// Spring4支持泛型注入
		return mapper.selectByRowBounds(record, new RowBounds(offset, limit));
	}
}
