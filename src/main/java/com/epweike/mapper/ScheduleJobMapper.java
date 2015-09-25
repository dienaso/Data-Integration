package com.epweike.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertUseGeneratedKeysMapper;

import com.epweike.model.ScheduleJob;

public interface ScheduleJobMapper extends Mapper<ScheduleJob> ,InsertUseGeneratedKeysMapper<ScheduleJob>{
}