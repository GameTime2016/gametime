package com.sy.gametime.dao.imp;

import org.apache.ibatis.session.SqlSession;
import java.util.List;
import com.sy.gametime.dao.CollectorsDao;
import com.sy.gametime.pojo.Collectors;
import com.sy.gametime.util.MybatisUtil;

public class CollectorsDaoImp implements CollectorsDao{
	@Override
	public void setAddCollectors(Collectors collectors){
		SqlSession session = MybatisUtil.currentSession();
		try{
			CollectorsDao collectorsDao=session.getMapper(CollectorsDao.class);
			collectorsDao.setAddCollectors(collectors);
			session.commit();
		}finally {
        	MybatisUtil.closeSession();
        }
	}
	@Override
	public List<Collectors> selectCollectors(String code){
		List<Collectors> collectorstemp=null;
		SqlSession session = MybatisUtil.currentSession();
		try {
			CollectorsDao collectorsDao=session.getMapper(CollectorsDao.class);
			collectorstemp=collectorsDao.selectCollectors(code);
		}finally {
        	MybatisUtil.closeSession();
        }
		return  collectorstemp;
	}
	@Override
	public void deleteCollectors(Collectors temp)
	{
		SqlSession session = MybatisUtil.currentSession();
		try {
			CollectorsDao collectorsDao=session.getMapper(CollectorsDao.class);
			collectorsDao.deleteCollectors(temp);
			session.commit();
		}finally {
        	MybatisUtil.closeSession();
        }
	}
	
}
