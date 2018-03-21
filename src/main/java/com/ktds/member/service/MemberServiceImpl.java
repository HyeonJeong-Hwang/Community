package com.ktds.member.service;

import com.ktds.community.dao.CommunityDao;
import com.ktds.member.dao.MemberDao;
import com.ktds.member.vo.MemberVO;

public class MemberServiceImpl implements MemberService{
	
	private MemberDao memberDao;
	private CommunityDao communityDao;

	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public boolean createMember(MemberVO memberVO) {
		return this.memberDao.insertMember(memberVO) > 0;
	}
	
	public CommunityDao getCommunityDao() {
		return communityDao;
	}

	public void setCommunityDao(CommunityDao communityDao) {
		this.communityDao = communityDao;
	}

	@Override
	public MemberVO readMember(MemberVO member) {
		return this.memberDao.selectMember(member);
	}

	@Override
	public boolean deleteMember(int id, String deleteFlag) {
		
		if(deleteFlag.equals("y")) {
			communityDao.deleteMyCommunities(id);
		}
		
		return this.memberDao.deleteMember(id) > 0;
	}




}
