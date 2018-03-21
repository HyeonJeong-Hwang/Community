package com.ktds.community.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.community.vo.CommunityVO;

import oracle.net.aso.p;

public class CommunityDaoImplForOracle extends SqlSessionDaoSupport implements CommunityDao{
	
	/*
	 * SqlSessionDaoSupport
	 * sqlSessionTemplate bean 객체를 가지고 있음.
	 */
	
	@Override
	public List<CommunityVO> selectAll() {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("CommunityDao.selectAll");
	}

	@Override
	public CommunityVO selectOne(int id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("CommunityDao.selectOne",id);
	}

	@Override
	public int insertCommunity(CommunityVO communityVO) {
		// TODO Auto-generated method stub
		return getSqlSession().insert("CommunityDao.insertCommunity", communityVO);
	}

	@Override
	public int incrementViewCount(int id) {
		// TODO Auto-generated method stub
		return getSqlSession().update("CommunityDao.incrementViewCount", id);
	}

	@Override
	public int incrementRecommendCount(int id) {
		// TODO Auto-generated method stub
		return getSqlSession().update("CommunityDao.incrementRecommendCount", id);
	}

	@Override
	public int deleteCommunity(int id) {
		return getSqlSession().delete("CommunityDao.deleteCommunity", id);
	}

	@Override
	public int deleteMyCommunities(int userId) {
		return getSqlSession().delete("CommunityDao.deleteMyCommunities", userId);
	}

	@Override
	public int updateCommunity(CommunityVO communityVO) {
		// TODO Auto-generated method stub
		return getSqlSession().update("CommunityDao.updateCommunity", communityVO);
	}

	@Override
	public int selectMyCommunitiesCount(int id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("CommunityDao.selectMyCommunitiesCount", id);
	}

	@Override
	public List<CommunityVO> selectMyCommunities(int id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("CommunityDao.selectMyCommunities", id);
	}

	@Override
	public int deleteCommunities(List<Integer> ids, int userId) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("ids", ids);
		params.put("userId", userId);
		return getSqlSession().delete("CommunityDao.deleteCommunities", params);
	}
	
	
	
	
	

}
