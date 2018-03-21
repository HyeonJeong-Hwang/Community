package com.ktds.member.service;

import com.ktds.member.dao.MemberDao;
import com.ktds.member.vo.MemberVO;

public interface MemberService {
	
	public void setMemberDao(MemberDao memberDao);
	public boolean createMember(MemberVO member);
	public MemberVO readMember(MemberVO member);
	public boolean deleteMember(int id, String deleteFlag);

}
