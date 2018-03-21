package com.ktds.community.service;

import java.util.ArrayList;
import java.util.List;

import com.ktds.community.dao.CommunityDao;
import com.ktds.community.vo.CommunityVO;

public class CommunityServiceImpl implements CommunityService{
	
	private CommunityDao communityDao;
	
	public void setCommunityDao(CommunityDao communityDao) {
		this.communityDao = communityDao;
	}

	@Override
	public List<CommunityVO> getAll() {
		return communityDao.selectAll();
	}
	
	
	public boolean createCommunity(CommunityVO communityVO) {
		
		String body = communityVO.getBody();
		body = body.replace("\n", "<br/>");
		body = filter(body);
		communityVO.setBody(body);
		int insertCount = communityDao.insertCommunity(communityVO);
		return insertCount > 0;
		
	}

	@Override
	public CommunityVO getOne(int id) {
		return communityDao.selectOne(id);
	}

	@Override
	public boolean increseRecommenderCount(int id) {
		if(communityDao.incrementRecommendCount(id) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean increseViewCount(int id) {
		if(communityDao.incrementViewCount(id) > 0) {
			return true;
		}
		return false;
	}
	
	public String filter(String str) {
		List<String> blackList = new ArrayList<String>();
		blackList.add("욕");
		blackList.add("씨");
		blackList.add("발");
		blackList.add("종간나세끼");
		
		String[] splitString = str.split(" ");
		
		for(String word : splitString) {
			for(String blackString : blackList) {
				if( blackString.contains(word)) {
					str = str.replace(blackString, "뿅뿅");
				}
			}
		}
		
		return str;
		
	}

	@Override
	public boolean deleteCommunity(int id) {
		return communityDao.deleteCommunity(id) > 0;
	}

	@Override
	public boolean updateCommunity(CommunityVO communityVO) {
		return communityDao.updateCommunity(communityVO) > 0;
	}

	@Override
	public int readMyCommunitiesCount(int id) {
		return communityDao.selectMyCommunitiesCount(id);
	}

	@Override
	public List<CommunityVO> readMyCommunities(int userId) {
		// TODO Auto-generated method stub
		return communityDao.selectMyCommunities(userId);
	}

	@Override
	public boolean deleteCommunities(List<Integer> ids, int userId) {
		// TODO Auto-generated method stub
		return communityDao.deleteCommunities(ids, userId) > 1;
	}

}
