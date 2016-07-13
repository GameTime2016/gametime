package com.sy.gametime.dao.imp;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.sy.gametime.dao.LanguageDao;
import com.sy.gametime.pojo.Language;
import com.sy.gametime.util.MybatisUtil;

public class LanguageDaoImp implements LanguageDao {
	@Override
	public List<Language> getLanguage() {
		List<Language> language = null;
		SqlSession session = MybatisUtil.currentSession();
		try {
			LanguageDao languageDao = session.getMapper(LanguageDao.class);
			language= languageDao.getLanguage();
        } finally {
        	MybatisUtil.closeSession();
        }
		return language;
	}
}
