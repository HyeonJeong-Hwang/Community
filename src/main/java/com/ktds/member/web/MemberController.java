package com.ktds.member.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community.service.CommunityService;
import com.ktds.member.constants.Member;
import com.ktds.member.service.MemberService;
import com.ktds.member.vo.MemberVO;

@Controller
public class MemberController {

	private MemberService memberService;
	private CommunityService communityService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewLoginPage(HttpSession session) {

		if (session.getAttribute(Member.USER) != null) {
			return "redirect:/";
		}
		return "member/login";
	}

	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String viewRegistPage() {
		return "member/regist";
	}

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public ModelAndView doRegistAction(@ModelAttribute("registFrom") @Valid MemberVO memberVO, Errors errors) {
		if (errors.hasErrors()) {
			return new ModelAndView("member/regist");
		}
		if (memberService.createMember(memberVO)) {
			return new ModelAndView("redirect:/login");
		}

		return new ModelAndView("redirect:/login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLoginAction(MemberVO memberVO, HttpServletRequest request) {
		HttpSession session = request.getSession();

		MemberVO loginMember = memberService.readMember(memberVO);

		if (loginMember != null) {
			session.setAttribute(Member.USER, loginMember);
			return "redirect:/";
		}

		return "redirect:/login";
	}

	@RequestMapping("/logout")
	public String doLogoutAction(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	@RequestMapping("/delete/process1")
	public String viewVerifyPage() {

		return "member/delete/process1";
	}

	@RequestMapping("/delete/process2") // process1이 보내준 password 받아와야 함
	public ModelAndView viewDeleteMyCommunitiesPage(
			@RequestParam(required = false, defaultValue = "") String password,
			HttpSession session) {
		if (password.length() == 0) {
			// password가 없다면
			return new ModelAndView("error/404");
		}
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		member.setPassword(password);

		MemberVO verifyMember = memberService.readMember(member);
		if (verifyMember == null) {
			return new ModelAndView("redirect:/delete/process1");
		}
		// 내가 작성한 게시글의 개수 가져오기
		int myCommunitiesCount
			= communityService.readMyCommunitiesCount(verifyMember.getId());

		ModelAndView view = new ModelAndView();
		view.setViewName("member/delete/process2");
		view.addObject("myCommunitiesCount", myCommunitiesCount);
		
		String uuid = UUID.randomUUID().toString();
		session.setAttribute("__TOKEN__", uuid);
		view.addObject("token", uuid);
		
		return view;
	}

	@RequestMapping("/account/delete/{deleteFlag}")
	public String doDeleteAction(HttpSession session, 
			@RequestParam(required=false, defaultValue="") String token,
			@PathVariable String deleteFlag) {
		
		String sessionToken = (String) session.getAttribute("__TOKEN__");
		if(sessionToken == null || !sessionToken.equals(token)) {
			return "error/404";
		}
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);

		if (member == null) {
			return "redirect:/login";
		}
		int id = member.getId();

		if (memberService.deleteMember(id, deleteFlag)) {
			session.invalidate();
		}
		return "member/delete/delete";
	}

}
