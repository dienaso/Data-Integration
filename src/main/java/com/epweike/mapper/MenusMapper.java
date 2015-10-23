package com.epweike.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertUseGeneratedKeysMapper;

import com.epweike.model.Menus;

public interface MenusMapper extends Mapper<Menus>,InsertUseGeneratedKeysMapper<Menus> {
}