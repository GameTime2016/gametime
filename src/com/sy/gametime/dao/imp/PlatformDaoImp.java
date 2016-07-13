package com.sy.gametime.dao.imp;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.sy.gametime.dao.PlatformDao;
import com.sy.gametime.pojo.Platform;
import com.sy.gametime.util.MybatisUtil;
public class PlatformDaoImp implements PlatformDao {
	@Override
	public  List<Platform>  getPlatform() {
		List<Platform> platform = null;
		SqlSession session = MybatisUtil.currentSession();
		try {
			PlatformDao platformDao = session.getMapper(PlatformDao.class);
			platform=  platformDao.getPlatform();
        } finally {
        	MybatisUtil.closeSession();
        }
		return platform;
	}
}
