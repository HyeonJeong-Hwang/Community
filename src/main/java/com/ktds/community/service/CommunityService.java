package com.ktds.community.service;

import java.util.List;

import com.ktds.community.vo.CommunityVO;

public interface CommunityService {

	public List<CommunityVO> getAll();

	public boolean createCommunity(CommunityVO communityVO);

	public CommunityVO getOne(int id);
	
	public int readMyCommunitiesCount(int id);
	
	public List<CommunityVO> readMyCommunities(int userId);

	public boolean increseViewCount(int id);

	public boolean increseRecommenderCount(int id);

	public boolean deleteCommunity(int id);
	
	public boolean deleteCommunities(List<Integer>idis, int id);

	public boolean updateCommunity(CommunityVO communityVO);

}
