package com.sy.gametime.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sy.gametime.pojo.Picture;

public interface PictureDao {
	public List<Picture> getPicture(@Param("code")String productCode, @Param("type")String picType);
}
