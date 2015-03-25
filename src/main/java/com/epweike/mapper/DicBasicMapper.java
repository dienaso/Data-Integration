package com.epweike.mapper;

import java.util.List;


import com.epweike.model.DicBasic;
import com.github.abel533.mapper.Mapper;

public interface DicBasicMapper extends Mapper<DicBasic> {
	
    int insertDicBasicBatch(List<DicBasic> list);

}