package com.sy.gametime.dao.imp;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import com.sy.gametime.dao.PictureDao;
import com.sy.gametime.pojo.Picture;
import com.sy.gametime.util.MybatisUtil;

public class PictureDaoImp implements PictureDao{

	@Override
	public List<Picture> getPicture(@Param("code")String productCode, @Param("type")String picType) {
		// TODO Auto-generated method stub
		List<Picture> pictures = null;
		SqlSession session = MybatisUtil.currentSession();
		try {
			PictureDao pictureDao = session.getMapper(PictureDao.class);
			pictures = pictureDao.getPicture(productCode, picType);
        } finally {
        	MybatisUtil.closeSession();
        }
		return pictures;
	}

}
