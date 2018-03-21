package com.ktds.community.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community.service.CommunityService;
import com.ktds.community.vo.CommunityVO;
import com.ktds.member.constants.Member;
import com.ktds.member.vo.MemberVO;
import com.ktds.util.DownloadUtil;

@Controller
public class CommunityController {
	
	private CommunityService communityService;
	
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	@RequestMapping("/")
	public ModelAndView viewListPage() {
		
		
		ModelAndView view = new ModelAndView();
		
		// /WEB-INF/view/community/list.jsp
		view.setViewName("community/list");
		
		List<CommunityVO> communityList = communityService.getAll();
		view.addObject("communityList", communityList);
		
		return view;
	}
	
	@RequestMapping(value = "/write", method=RequestMethod.GET)
	public String viewWritePage() {
		
		return "/community/write";
	}
	
	@RequestMapping(value = "/write", method=RequestMethod.POST)
	public ModelAndView doWrite(
			@ModelAttribute("writeForm") @Valid CommunityVO communityVO, Errors errors, 
			HttpServletRequest request) {
		
		if(errors.hasErrors()) {
			ModelAndView view = new ModelAndView();
			view.setViewName("community/write");
			view.addObject("communityVO", communityVO);
			return view;
		}
		
		String requestorIp = request.getRemoteAddr();
		communityVO.setRequestIp(requestorIp);
		
		communityVO.save();
		
		boolean isSuccess = communityService.createCommunity(communityVO);
		
		if( isSuccess ) {
			return new ModelAndView("redirect:/");
		}
		
		return new ModelAndView("redirect:/write");
	}
	
	@RequestMapping("/view/{id}")
	public ModelAndView viewViewPage(@PathVariable int id) {
		
		ModelAndView view = new ModelAndView();
		view.setViewName("community/view");
		
		CommunityVO community = communityService.getOne(id);
		view.addObject("community", community);
		
		return view;
	}
	
	@RequestMapping("/read/{id}")
	public String readPage(@PathVariable int id) {
		if(communityService.increseViewCount(id)) {
			return "redirect:/view/"+id;
		}
		return "redirect:/";
	}
	
	@RequestMapping("/recommend/{id}")
	public String recommendPage(@PathVariable int id) {
		if(communityService.increseRecommenderCount(id)) {
			return "redirect:/view/"+id;
		}
		
		return null;
	}
	
	@RequestMapping(value="/modify/{id}", method=RequestMethod.GET)
	public ModelAndView viewModifyPage(@PathVariable int id, HttpSession session) {
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		CommunityVO community = communityService.getOne(id);
		
		int userId = member.getId();
		
		if(userId != community.getUserId()) {
			return new ModelAndView("error/404");
		}
		
		ModelAndView view = new ModelAndView();
		view.setViewName("community/write");
		view.addObject("communityVO", community);
		view.addObject("mode", "modify");
		return view;
	}
	
	@RequestMapping(value="/modify/{id}", method=RequestMethod.POST)
	public String doModifyAction(@PathVariable int id, HttpSession session
			, @ModelAttribute("writeForm") @Valid CommunityVO communityVO, Errors errors
			, HttpServletRequest request) {
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		CommunityVO originalVO = communityService.getOne(id);
		
		if(member.getId() != originalVO.getUserId()) {
			return "error/404";
		}
		
		if(errors.hasErrors()) {
			return "redirect:/modify/"+id;
		}
		
		CommunityVO newCommunity = new CommunityVO();
		newCommunity.setId(originalVO.getId());
		newCommunity.setUserId(member.getId());
		
		boolean isModify = false;
		
		//1.IP변경확인
		String ip = request.getRemoteAddr();
		if( !ip.equals(originalVO.getRequestIp())) {
			newCommunity.setRequestIp(ip);
			isModify = true;
		}
		
		//2.제목변경 확인
		if( !originalVO.getTitle().equals(communityVO.getTitle())) {
			newCommunity.setTitle(communityVO.getTitle());
			isModify = true;
		}
		
		//3.내용 변경 확인
		if( !originalVO.getBody().equals(communityVO.getBody())) {
			newCommunity.setBody(communityVO.getBody());
			isModify = true;
		}
		
		//4.파일 변경 확인
		if(communityVO.getDisplayFilename().length() > 0) {
			File file = new File("d:/"+communityVO);
			file.delete();
			communityVO.setDisplayFilename("");
		}
		else {
			communityVO.setDisplayFilename(originalVO.getDisplayFilename());
		}
		communityVO.save();
		if( !originalVO.getDisplayFilename().equals(communityVO.getDisplayFilename())) {
			newCommunity.setDisplayFilename(communityVO.getDisplayFilename());
			isModify = true;
		}
		
		//5.변경이 없는지 확인
		if(isModify) {
			//6.UPDATE하는 서비스 코드 호출
			communityService.updateCommunity(communityVO);
		}
		
		return "redirect:/view/"+id;
	}
	
	@RequestMapping("/get/{id}")
	public void download(@PathVariable int id, 
			HttpServletRequest request, HttpServletResponse response) {
		
		CommunityVO community = communityService.getOne(id);
		String filename = community.getDisplayFilename();
		
		DownloadUtil download = new DownloadUtil("d:/"+filename);
		
		try {
			download.download(request, response, filename);
		}catch(UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	@RequestMapping("/delete/{id}")
	public String doDeleteAction(@PathVariable int id, HttpSession session) {
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		
		CommunityVO community = communityService.getOne(id);
		
		boolean isMyCommunity = member.getId() == community.getUserId();
		
		if(isMyCommunity && communityService.deleteCommunity(id)) {
			return "redirect:/";
			
		}
		return "/WEB-INF/view/error/404";
	}
	

}
