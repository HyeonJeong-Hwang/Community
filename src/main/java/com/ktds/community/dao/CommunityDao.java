package com.ktds.community.dao;

import java.util.List;

import com.ktds.community.vo.CommunityVO;

public interface CommunityDao {
	
	public List<CommunityVO> selectAll();
	
	public CommunityVO selectOne(int id);
	
	public int selectMyCommunitiesCount(int id);
	
	public List<CommunityVO> selectMyCommunities(int id);
	
	public int insertCommunity(CommunityVO communityVO);
	
	public int incrementViewCount(int id);
	
	public int incrementRecommendCount(int id);
	
	public int deleteCommunity(int id);
	
	public int deleteMyCommunities(int userId);
	
	public int deleteCommunities(List<Integer> ids, int userId);
	
	public int updateCommunity(CommunityVO communityVO);

}
