package com.shop.ShoppingMall_TeamPrj.member.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;  // JAXB API 필요
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient; // Java 1.6 환경에 맞는 Apache HttpClient 사용
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.shop.ShoppingMall_TeamPrj.member.service.MemberService;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

@Controller("memberController")
public class MemberControllerImpl implements MemberController {

    // === 카카오 소셜 로그인 관련 상수 (환경에 맞게 수정) ===
    private static final String KAKAO_CLIENT_ID = "d3841b71b544ce26f9b2628a716d6ee1";
    private static final String KAKAO_REDIRECT_URI = "http://localhost:8090/ShoppingMall_TeamPrj/member/kakaoCallback.do";
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private MemberVO memberVO;
    
    // 메인 페이지 ("/" 및 "/main.do")
    @RequestMapping(value = { "/", "/main.do" }, method = RequestMethod.GET)
    private ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = (String) request.getAttribute("viewName");
        ModelAndView mav = new ModelAndView();
        mav.setViewName(viewName);
        return mav;
    }
    
    @Override
    @RequestMapping(value = "/member/listMembers.do", method = RequestMethod.GET)
    public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setContentType("html/text;charset=utf-8");
        String viewName = (String) request.getAttribute("viewName");
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
    
    @RequestMapping(value = "/member/updateMemberForm.do", method = RequestMethod.GET)
    public ModelAndView showMemberManagement(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<MemberVO> membersList = memberService.listMembers();
        ModelAndView mav = new ModelAndView("member/memberManagement");
        mav.addObject("membersList", membersList);
        return mav;
    }
    
    @Override
    @RequestMapping(value = "/member/addMember.do", method = RequestMethod.POST)
    public ModelAndView addMember(@ModelAttribute("member") MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        int result = memberService.addMember(member);
        HttpSession session = request.getSession();
        session.setAttribute("welcomeMessage", member.getUser_name() + "님 가입을 환영합니다. 다시 로그인해주세요.");
        return new ModelAndView("redirect:/main/main.do");
    }
    
    @Override
    @RequestMapping(value = "/member/removeMember.do", method = RequestMethod.GET)
    public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        memberService.removeMember(id);
        ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
        return mav;
    }
    
//    @Override
//    @RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
//    public ModelAndView login(@ModelAttribute("member") MemberVO member, RedirectAttributes rAttr, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        ModelAndView mav = new ModelAndView();
//        memberVO = memberService.login(member);
//        
//        if (memberVO == null) {
//            System.out.println("로그인 실패: memberVO가 null입니다.");
//        } else {
//            System.out.println("로그인 성공: " + memberVO.getUser_id());
//        }
//        
//        if (memberVO != null) {
//            HttpSession session = request.getSession();
//            session.setAttribute("memberInfo", memberVO);
//            session.setAttribute("isLogOn", true);
//            
//            String action = (String) session.getAttribute("action");
//            session.removeAttribute("action");
//            if (action != null) {
//                mav.setViewName("redirect:" + action);
//            } else {
//                mav.setViewName("redirect:/main/main.do");
//            }
//        } else {
//            rAttr.addAttribute("result", "loginFailed");
//            mav.setViewName("redirect:/member/loginForm.do");
//        }
//        
//        return mav;
//    }
//    
    
	
	   @Override
	    @RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	    public ModelAndView login(@ModelAttribute("member") MemberVO member,
	                              RedirectAttributes rAttr,
	                              HttpServletRequest request, HttpServletResponse response) throws Exception {
	        ModelAndView mav = new ModelAndView();
	        memberVO = memberService.login(member);

	        // 로그인 성공/실패 확인
	        if (memberVO == null) {
	            System.out.println("로그인 실패: memberVO가 null입니다.");
	        } else {
	            System.out.println("로그인 성공: " + memberVO.getUser_id());
	        }

	        if (memberVO != null) {
	            HttpSession session = request.getSession();
	            session.setAttribute("memberInfo", memberVO); // 로그인 정보를 세션에 저장
	            session.setAttribute("isLogOn", true); // 로그인 상태 저장

	            String action = (String) session.getAttribute("action");
	            session.removeAttribute("action");

	            if (action != null) {
	                mav.setViewName("redirect:" + action);
	            } else {
	                // 관리자 여부 확인
	                if ("ADMIN".equals(memberVO.getRole())) {
	                    mav.setViewName("redirect:/admin/listProducts.do"); // 관리자 페이지로 이동
	                } else {
	                    mav.setViewName("redirect:/main/main.do"); // 일반 사용자 마이페이지로 이동
	                }
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
        session.removeAttribute("memberInfo");
        session.removeAttribute("isLogOn");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/main/main.do");
        return mav;
    }
    
    @RequestMapping(value = "/member/*Form.do", method = RequestMethod.GET)
    private ModelAndView form(@RequestParam(value = "result", required = false) String result,
                              @RequestParam(value = "action", required = false) String action,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = (String) request.getAttribute("viewName");
        HttpSession session = request.getSession();
        session.setAttribute("action", action);
        ModelAndView mav = new ModelAndView();
        mav.addObject("result", result);
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
        if (contextPath != null && !contextPath.equals("")) {
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
    
    // ==========================================================
    // 카카오 소셜 로그인 기능 추가 및 보충 등록 체크
    // ==========================================================
    
    // 1. 카카오 인증 페이지로 리다이렉트
    @RequestMapping(value = "/member/kakaoLogin.do", method = RequestMethod.GET)
    public ModelAndView kakaoLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + KAKAO_CLIENT_ID
                + "&redirect_uri=" + URLEncoder.encode(KAKAO_REDIRECT_URI, "UTF-8")
                + "&response_type=code";
        return new ModelAndView("redirect:" + kakaoAuthUrl);
    }
    
    // 2. 카카오 콜백 처리
    @RequestMapping(value = "/member/kakaoCallback.do", method = RequestMethod.GET)
    public ModelAndView kakaoCallback(@RequestParam("code") String code,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("https://kauth.kakao.com/oauth/token");
        
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("client_id", KAKAO_CLIENT_ID));
        params.add(new BasicNameValuePair("redirect_uri", KAKAO_REDIRECT_URI));
        params.add(new BasicNameValuePair("code", code));
        post.setEntity(new org.apache.http.client.entity.UrlEncodedFormEntity(params, "UTF-8"));
        
        HttpResponse tokenResponse = client.execute(post);
        String tokenResponseBody = EntityUtils.toString(tokenResponse.getEntity(), "UTF-8");
        JSONObject tokenJson = new JSONObject(tokenResponseBody);
        String accessToken = tokenJson.getString("access_token");
        
        HttpGet get = new HttpGet("https://kapi.kakao.com/v2/user/me");
        get.setHeader("Authorization", "Bearer " + accessToken);
        HttpResponse userResponse = client.execute(get);
        String userResponseBody = EntityUtils.toString(userResponse.getEntity(), "UTF-8");
        JSONObject userJson = new JSONObject(userResponseBody);
        long kakaoId = userJson.getLong("id");
        JSONObject properties = userJson.getJSONObject("properties");
        String nickname = properties.optString("nickname");
        
        String email = "kakao_" + kakaoId + "@example.com";
        
        HttpSession session = request.getSession();
        MemberVO existingMember = memberService.getMemberByKakaoId(kakaoId);
        
        if (existingMember != null) {
            session.setAttribute("memberInfo", existingMember);
            session.setAttribute("isLogOn", true);
            if (existingMember.getPhone_number() == null || existingMember.getPhone_number().trim().isEmpty() ||
                existingMember.getAddress() == null || existingMember.getAddress().trim().isEmpty() ||
                existingMember.getDate_of_birth() == null) {
                session.setAttribute("isProfileComplete", false);
                return new ModelAndView("redirect:/member/supplementRegistrationForm.do");
            } else {
                session.setAttribute("isProfileComplete", true);
            }
        } else {
            MemberVO newMember = new MemberVO();
            newMember.setKakaoId(kakaoId);
            newMember.setUser_name(nickname);
            newMember.setJoinType("kakao");
            newMember.setPassword("SOCIAL");
            newMember.setEmail(email);
            
            memberService.addMember(newMember);
            
            session.setAttribute("memberInfo", newMember);
            session.setAttribute("isLogOn", true);
            session.setAttribute("isProfileComplete", false);
            return new ModelAndView("redirect:/member/supplementRegistrationForm.do");
        }
        
        return new ModelAndView("redirect:/main/main.do");
    }
    
    // 3. 보충 등록 정보를 업데이트하는 메서드
    @RequestMapping(value = "/member/updateSupplementInfo.do", method = RequestMethod.POST)
    public ModelAndView updateSupplementInfo(@ModelAttribute("member") MemberVO updatedMember,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        MemberVO currentMember = (MemberVO) session.getAttribute("memberInfo");
        if (currentMember == null) {
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        String phone = updatedMember.getPhone_number();
        String address = updatedMember.getAddress();
        java.sql.Date dob = updatedMember.getDate_of_birth();
        
        if (phone == null || phone.trim().isEmpty() ||
            address == null || address.trim().isEmpty() ||
            dob == null) {
            session.setAttribute("isProfileComplete", false);
            return new ModelAndView("redirect:/member/supplementRegistrationForm.do");
        }
        
        currentMember.setPhone_number(phone);
        currentMember.setAddress(address);
        currentMember.setDate_of_birth(dob);
        
        int updateResult = memberService.updateMember(currentMember);
        if (updateResult > 0) {
            session.setAttribute("memberInfo", currentMember);
            session.setAttribute("isProfileComplete", true);
        } else {
            return new ModelAndView("redirect:/member/supplementRegistrationForm.do");
        }
        
        return new ModelAndView("redirect:/main/main.do");
    }
    
    // ==========================================================
    // ID 및 비밀번호 찾기 (휴대폰 인증) 관련 메서드 추가
    // ==========================================================
    
    // 4. 휴대폰 번호 입력 후 SMS 인증 코드 전송 (sendSmsCode) - COOLSMS API HMAC 인증 방식 적용
    @RequestMapping(value = "/member/sendSmsCode.do", method = RequestMethod.POST)
    public ModelAndView sendSmsCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String phone = request.getParameter("phone");
        String smsCode = String.format("%06d", (int)(Math.random() * 1000000));
        
        // COOLSMS API 관련 변수
        String apiKey = "NCSFS9YEWRE4BX0K";
        String apiSecret = "KNANEAV2RYUXBDJP59PYA0JVVNZHKLGO";
        String sender = "01064811904";
        String message = "Your verification code is: " + smsCode;
        
        // COOLSMS API 엔드포인트 URL (문서 참고)
        String apiUrl = "https://api.coolsms.co.kr/messages/v4/send";
        
        // 현재 UTC 시간을 ISO 8601 형식으로 생성 (예: 2019-07-01T00:41:48Z)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateTime = sdf.format(new Date());
        
        // 16자리 랜덤 Salt 생성 (알파벳 대소문자와 숫자)
        String salt = generateSalt(16);
        
        // Signature 생성: HMAC-SHA256(apiSecret, dateTime + salt)
        String data = dateTime + salt;
        String signature = hmacSha256(apiSecret, data);
        
        // Authorization 헤더 구성
        String authorization = "HMAC-SHA256 apiKey=" + apiKey + ", date=" + dateTime + ", salt=" + salt + ", signature=" + signature;
        
        // JSON 요청 본문 구성
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("to", phone);
        jsonRequest.put("from", sender);
        jsonRequest.put("msg", message);
        jsonRequest.put("type", "SMS");
        
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", authorization);
        
        OutputStream os = conn.getOutputStream();
        os.write(jsonRequest.toString().getBytes("UTF-8"));
        os.flush();
        os.close();
        
        int responseCode = conn.getResponseCode();
        StringBuilder responseSb = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseSb.append(inputLine);
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        String responseMessage = responseSb.toString();
        JSONObject jsonResponse = new JSONObject(responseMessage);
        
        HttpSession session = request.getSession();
        session.setAttribute("smsCode", smsCode);
        session.setAttribute("smsPhone", phone);
        
        if (jsonResponse.optBoolean("success", false)) {
            return new ModelAndView("smsSent"); // smsSent.jsp: 전송 완료 메시지 페이지
        } else {
            ModelAndView mav = new ModelAndView("smsVerification");
            mav.addObject("error", "SMS 전송 실패: " + jsonResponse.optString("error_message", "Unknown error"));
            return mav;
        }
    }
    
    // 5. 사용자로부터 SMS 인증 코드를 입력받아 검증 (verifySmsCode)
    @RequestMapping(value = "/member/verifySmsCode.do", method = RequestMethod.POST)
    public ModelAndView verifySmsCode(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String storedCode = (String) session.getAttribute("smsCode");
        if (storedCode != null && storedCode.equals(code)) {
            session.setAttribute("isPhoneVerified", true);
            return new ModelAndView("redirect:/member/findId.do");
        } else {
            ModelAndView mav = new ModelAndView("smsVerification");
            mav.addObject("error", "인증번호가 일치하지 않습니다.");
            return mav;
        }
    }
    
    // 6. 휴대폰 번호 인증 후, 해당 전화번호로 회원 ID 찾기 (findId)
    @RequestMapping(value = "/member/findId.do", method = RequestMethod.GET)
    public ModelAndView findId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Boolean verified = (Boolean) session.getAttribute("isPhoneVerified");
        if (verified == null || !verified) {
            return new ModelAndView("redirect:/member/phoneVerificationForm.do");
        }
        String phone = (String) session.getAttribute("smsPhone");
        MemberVO member = memberService.findMemberByPhone(phone);
        ModelAndView mav = new ModelAndView("displayId");
        if (member != null) {
            mav.addObject("userId", member.getUser_id());
        } else {
            mav.addObject("error", "해당 번호로 등록된 회원이 없습니다.");
        }
        return mav;
    }
    
    // 7. 휴대폰 번호 인증 후, 비밀번호 재설정을 위한 페이지 이동 (resetPassword 폼 제공)
    @RequestMapping(value = "/member/resetPassword.do", method = RequestMethod.GET)
    public ModelAndView resetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Boolean verified = (Boolean) session.getAttribute("isPhoneVerified");
        if (verified == null || !verified) {
            return new ModelAndView("redirect:/member/phoneVerificationForm.do");
        }
        return new ModelAndView("resetPasswordForm");
    }
    
    // 8. 새 비밀번호를 받아 업데이트 (resetPassword 처리)
    @RequestMapping(value = "/member/resetPassword.do", method = RequestMethod.POST)
    public ModelAndView processResetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Boolean verified = (Boolean) session.getAttribute("isPhoneVerified");
        if (verified == null || !verified) {
            return new ModelAndView("redirect:/member/phoneVerificationForm.do");
        }
        String newPassword = request.getParameter("newPassword");
        String phone = (String) session.getAttribute("smsPhone");
        MemberVO member = memberService.findMemberByPhone(phone);
        if (member != null) {
            member.setPassword(newPassword); // 실제로는 비밀번호 암호화 필요
            memberService.updateMember(member);
            session.setAttribute("isPhoneVerified", false);
            ModelAndView mav = new ModelAndView("redirect:/member/loginForm.do");
            mav.addObject("message", "비밀번호가 재설정되었습니다. 다시 로그인해주세요.");
            return mav;
        } else {
            ModelAndView mav = new ModelAndView("resetPasswordForm");
            mav.addObject("error", "회원 정보를 찾을 수 없습니다.");
            return mav;
        }
    }
    
    // --- 헬퍼 메서드 ---
    
    // 지정된 길이의 랜덤 알파벳+숫자 Salt 문자열 생성 (예: 16자리)
    private String generateSalt(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            int idx = random.nextInt(chars.length());
            sb.append(chars.charAt(idx));
        }
        return sb.toString();
    }
    
    // HMAC-SHA256 해시를 생성하는 메서드
    private String hmacSha256(String key, String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        mac.init(secretKey);
        byte[] hashBytes = mac.doFinal(data.getBytes("UTF-8"));
        return DatatypeConverter.printHexBinary(hashBytes).toLowerCase();
    }
}
