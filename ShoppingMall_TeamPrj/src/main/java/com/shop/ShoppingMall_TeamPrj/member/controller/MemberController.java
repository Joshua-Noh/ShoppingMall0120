package com.shop.ShoppingMall_TeamPrj.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

public interface MemberController {
    // 기존 메서드들...
    public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView addMember(@ModelAttribute("info") MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView updateMember(@ModelAttribute("member") MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView login(@ModelAttribute("member") MemberVO member, RedirectAttributes rAttr, HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    // 소셜 로그인 메서드들
    public ModelAndView kakaoLogin(HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView kakaoCallback(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    // ID 및 비밀번호 찾기(휴대폰 인증) 관련 메서드 추가 예시
    public ModelAndView sendSmsCode(HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView verifySmsCode(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView findId(HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView resetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
