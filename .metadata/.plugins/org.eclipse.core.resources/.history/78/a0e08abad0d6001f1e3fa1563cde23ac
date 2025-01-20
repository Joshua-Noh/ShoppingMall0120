package com.shop.ShoppingMall_TeamPrj.main.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.ShoppingMall_TeamPrj.main.member.service.MemberService;
import com.shop.ShoppingMall_TeamPrj.main.member.vo.MemberVO;



@Controller("memberController")
//@EnableAspectJAutoProxy
public class MemberControllerImpl   implements MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberVO memberVO ;
	
	@RequestMapping(value = { "/","/main.do"}, method = RequestMethod.GET)
	private ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	@Override
	@RequestMapping(value="/member/listMembers.do" ,method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("html/text;charset=utf-8");
		String viewName = (String)request.getAttribute("viewName");
		List membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		return mav;
	}
    @Override
    @RequestMapping(value = "/member/updateMember.do", method = RequestMethod.POST)
    public ModelAndView updateMember(@ModelAttribute("member") MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int result = memberService.updateMember(memberVO);
        ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
        if (result > 0) {
			mav.addObject("message", "회원 정보 수정 성공");
		} else {
			mav.addObject("message", "회원 정보 수정 실패");
		}
        return mav;
    }
    @RequestMapping(value = "/member/management", method = RequestMethod.GET)
    public ModelAndView showMemberManagement(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<MemberVO> membersList = memberService.listMembers(); // 회원 목록 가져오기
        ModelAndView mav = new ModelAndView("member/memberManagement");
        mav.addObject("membersList", membersList);
        return mav;
    }

    @Override
    @RequestMapping(value="/member/addMember.do", method=RequestMethod.POST)
    public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        int result = memberService.addMember(member);

        HttpSession session = request.getSession();
        session.setAttribute("welcomeMessage", member.getUser_name() + "님 가입을 환영합니다. 다시 로그인해주세요.");
        
        return new ModelAndView("redirect:/main/main.do");
    }


	
	@Override
	@RequestMapping(value="/member/removeMember.do", method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, 
	                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
	    // UTF-8 인코딩 설정 (이미 요청에 지정되어 있으면 생략 가능)
	    request.setCharacterEncoding("utf-8");
	    
	    // 서비스 호출하여 회원 삭제
	    memberService.removeMember(id);

	    // 삭제 후 회원 목록 페이지로 리다이렉트
	    ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
	    return mav;
	}

	/*
	@RequestMapping(value = { "/member/loginForm.do", "/member/memberForm.do" }, method =  RequestMethod.GET)
	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	*/
	
	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member,
	                          RedirectAttributes rAttr,
	                          HttpServletRequest request, HttpServletResponse response) throws Exception {
	    ModelAndView mav = new ModelAndView();
	    memberVO = memberService.login(member);

	    if (memberVO != null) {
	        HttpSession session = request.getSession();
	        session.setAttribute("member", memberVO);
	        session.setAttribute("isLogOn", true);

	        String action = (String) session.getAttribute("action");
	        session.removeAttribute("action");

	        if (action != null) {
	            mav.setViewName("redirect:" + action);
	        } else {
	            mav.setViewName("redirect:/main/main.do"); // 기본 경로를 메인 페이지로 설정
	        }

	    } else {
	        rAttr.addAttribute("result", "loginFailed");
	        mav.setViewName("redirect:/member/loginForm.do");
	    }

	    return mav;
	}

	@Override
	@RequestMapping(value = "/member/logout.do", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    HttpSession session = request.getSession();
	    // 세션에서 로그인 관련 정보 제거
	    session.removeAttribute("member");
	    session.removeAttribute("isLogOn");

	    // 메인 페이지로 리다이렉트
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("redirect:/main/main.do");
	    return mav;
	}


	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	private ModelAndView form(@RequestParam(value= "result", required=false) String result,
							  @RequestParam(value= "action", required=false) String action,
						       HttpServletRequest request, 
						       HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		HttpSession session = request.getSession();
		session.setAttribute("action", action);  
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName(viewName);
		return mav;
	}
	

	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String viewName = uri.substring(begin, end);
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
		}
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
		}
		return viewName;
	}




}
