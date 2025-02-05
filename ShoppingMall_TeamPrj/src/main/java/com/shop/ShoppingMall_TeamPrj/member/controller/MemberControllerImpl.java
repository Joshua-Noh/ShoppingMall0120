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
import javax.xml.bind.DatatypeConverter; // JAXB API 필요
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient; // Java 1.6 환경에 맞는 Apache HttpClient 사용
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
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
		System.out.println("[DEBUG] main() 호출됨");
		String viewName = (String) request.getAttribute("viewName");
		System.out.println("[DEBUG] viewName: " + viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/listMembers.do", method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] listMembers() 호출됨");
		request.setCharacterEncoding("utf-8");
		response.setContentType("html/text;charset=utf-8");
		String viewName = (String) request.getAttribute("viewName");
		System.out.println("[DEBUG] viewName: " + viewName);
		List membersList = memberService.listMembers();
		System.out.println("[DEBUG] membersList.size(): " + membersList.size());
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/updateMember.do", method = RequestMethod.POST)
	public ModelAndView updateMember(@ModelAttribute("member") MemberVO memberVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] updateMember() 호출됨");
		int result = memberService.updateMember(memberVO);
		System.out.println("[DEBUG] update 결과: " + result);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		if (result > 0) {
			mav.addObject("message", "회원 정보 수정 성공");
		} else {
			mav.addObject("message", "회원 정보 수정 실패");
		}
		return mav;
	}

	@RequestMapping(value = "/member/updateMemberForm.do", method = RequestMethod.GET)
	public ModelAndView showMemberManagement(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("[DEBUG] showMemberManagement() 호출됨");
		List<MemberVO> membersList = memberService.listMembers();
		System.out.println("[DEBUG] membersList.size(): " + membersList.size());
		ModelAndView mav = new ModelAndView("member/memberManagement");
		mav.addObject("membersList", membersList);
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/addMember.do", method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] addMember() 호출됨");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		int result = memberService.addMember(member);
		System.out.println("[DEBUG] addMember 결과: " + result);
		HttpSession session = request.getSession();
		session.setAttribute("welcomeMessage", member.getUser_name() + "님 가입을 환영합니다. 다시 로그인해주세요.");
		return new ModelAndView("redirect:/main/main.do");
	}

	@Override
	@RequestMapping(value = "/member/removeMember.do", method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] removeMember() 호출됨, id: " + id);
		request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member, RedirectAttributes rAttr,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] login() 호출됨");
		ModelAndView mav = new ModelAndView();
		memberVO = memberService.login(member);

		if (memberVO == null) {
			System.out.println("[DEBUG] 로그인 실패: memberVO가 null입니다.");
		} else {
			System.out.println("[DEBUG] 로그인 성공: " + memberVO.getUser_id());
		}

		if (memberVO != null) {
			HttpSession session = request.getSession();
			session.setAttribute("memberInfo", memberVO);
			session.setAttribute("user_id", memberVO.getUser_id()); // user_id 저장
			 session.setAttribute("role", memberVO.getRole());
			session.setAttribute("isLogOn", true);

			String action = (String) session.getAttribute("action");
			System.out.println("[DEBUG] action: " + action);
			session.removeAttribute("action");

			if (action != null) {
				mav.setViewName("redirect:" + action);
			} else {
				if ("ADMIN".equals(memberVO.getRole())) {
					System.out.println("[DEBUG] 관리자 로그인 detected");
					mav.setViewName("redirect:/admin/listProducts.do");
				} else {
					System.out.println("[DEBUG] 일반 사용자 로그인 detected");
					mav.setViewName("redirect:/main/main.do");
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
	    System.out.println("[DEBUG] logout() 호출됨");
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();  // 세션 전체를 무효화하여 모든 속성 제거
	    }
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("redirect:/main/main.do");
	    return mav;
	}


	@RequestMapping(value = "/member/*Form.do", method = RequestMethod.GET)
	private ModelAndView form(@RequestParam(value = "result", required = false) String result,
			@RequestParam(value = "action", required = false) String action, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] form() 호출됨, result: " + result + ", action: " + action);
		String viewName = (String) request.getAttribute("viewName");
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result", result);
		mav.setViewName(viewName);
		return mav;
	}

	private String getViewName(HttpServletRequest request) throws Exception {
		System.out.println("[DEBUG] getViewName() 호출됨");
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
		System.out.println("[DEBUG] 최종 viewName: " + viewName);
		return viewName;
	}

	// ==========================================================
	// 카카오 소셜 로그인 기능 추가 및 보충 등록 체크
	// ==========================================================

	// 1. 카카오 인증 페이지로 리다이렉트
	@RequestMapping(value = "/member/kakaoLogin.do", method = RequestMethod.GET)
	public ModelAndView kakaoLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] kakaoLogin() 호출됨");
		String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize" + "?client_id=" + KAKAO_CLIENT_ID
				+ "&redirect_uri=" + URLEncoder.encode(KAKAO_REDIRECT_URI, "UTF-8") + "&response_type=code";
		System.out.println("[DEBUG] kakaoAuthUrl: " + kakaoAuthUrl);
		return new ModelAndView("redirect:" + kakaoAuthUrl);
	}

	// 2. 카카오 콜백 처리
	@RequestMapping(value = "/member/kakaoCallback.do", method = RequestMethod.GET)
	public ModelAndView kakaoCallback(@RequestParam("code") String code, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] kakaoCallback() 호출됨, code: " + code);
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
		System.out.println("[DEBUG] tokenResponseBody: " + tokenResponseBody);
		JSONObject tokenJson = new JSONObject(tokenResponseBody);
		String accessToken = tokenJson.getString("access_token");
		System.out.println("[DEBUG] accessToken: " + accessToken);

		HttpGet get = new HttpGet("https://kapi.kakao.com/v2/user/me");
		get.setHeader("Authorization", "Bearer " + accessToken);
		HttpResponse userResponse = client.execute(get);
		String userResponseBody = EntityUtils.toString(userResponse.getEntity(), "UTF-8");
		System.out.println("[DEBUG] userResponseBody: " + userResponseBody);
		JSONObject userJson = new JSONObject(userResponseBody);
		long kakaoId = userJson.getLong("id");
		System.out.println("[DEBUG] kakaoId: " + kakaoId);
		JSONObject properties = userJson.getJSONObject("properties");
		String nickname = properties.optString("nickname");
		System.out.println("[DEBUG] nickname: " + nickname);

		String email = "kakao_" + kakaoId + "@example.com";
		System.out.println("[DEBUG] email: " + email);

		HttpSession session = request.getSession();
		MemberVO existingMember = memberService.getMemberByKakaoId(kakaoId);

		if (existingMember != null) {
			System.out.println("[DEBUG] 기존 회원 발견");
			session.setAttribute("memberInfo", existingMember);
			session.setAttribute("isLogOn", true);
			if (existingMember.getPhone_number() == null || existingMember.getPhone_number().trim().isEmpty()
					|| existingMember.getAddress() == null || existingMember.getAddress().trim().isEmpty()
					|| existingMember.getDate_of_birth() == null) {
				session.setAttribute("isProfileComplete", false);
				System.out.println("[DEBUG] 프로필 보충 필요");
				return new ModelAndView("redirect:/member/supplementRegistrationForm.do");
			} else {
				session.setAttribute("isProfileComplete", true);
			}
		} else {
			System.out.println("[DEBUG] 신규 회원 생성");
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
		System.out.println("[DEBUG] updateSupplementInfo() 호출됨");
		HttpSession session = request.getSession(false);
		if (session == null) {
			System.out.println("[DEBUG] 세션 없음 - loginForm으로 리다이렉트");
			return new ModelAndView("redirect:/member/loginForm.do");
		}

		MemberVO currentMember = (MemberVO) session.getAttribute("memberInfo");
		if (currentMember == null) {
			System.out.println("[DEBUG] memberInfo 없음 - loginForm으로 리다이렉트");
			return new ModelAndView("redirect:/member/loginForm.do");
		}

		String phone = updatedMember.getPhone_number();
		String address = updatedMember.getAddress();
		java.sql.Date dob = updatedMember.getDate_of_birth();
		System.out.println("[DEBUG] 입력받은 phone: " + phone + ", address: " + address + ", dob: " + dob);

		if (phone == null || phone.trim().isEmpty() || address == null || address.trim().isEmpty() || dob == null) {
			session.setAttribute("isProfileComplete", false);
			System.out.println("[DEBUG] 입력값 미비 - supplementRegistrationForm으로 리다이렉트");
			return new ModelAndView("redirect:/member/supplementRegistrationForm.do");
		}

		currentMember.setPhone_number(phone);
		currentMember.setAddress(address);
		currentMember.setDate_of_birth(dob);

		int updateResult = memberService.updateMember(currentMember);
		System.out.println("[DEBUG] updateSupplementInfo updateResult: " + updateResult);
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
	// (CoolSMS v4 API에 맞게, 단일 메시지 전송 시 "message" 객체 사용)
	@RequestMapping(value = "/member/sendSmsCode.do", method = RequestMethod.POST)
	public ModelAndView sendSmsCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] sendSmsCode() 호출됨");
		String phone = request.getParameter("phone");
		System.out.println("[DEBUG] 입력받은 phone: " + phone);
		String smsCode = String.format("%06d", (int) (Math.random() * 1000000));
		System.out.println("[DEBUG] 생성된 smsCode: " + smsCode);

		// COOLSMS API 관련 변수
		String apiKey = "NCSFS9YEWRE4BX0K"; // 자신의 API Key로 수정
		String apiSecret = "KNANEAV2RYUXBDJP59PYA0JVVNZHKLGO"; // 자신의 API Secret로 수정
		String sender = "01064811904"; // 사전에 등록된 발신번호
		String message = "Your verification code is: " + smsCode;
		System.out.println("[DEBUG] 전송할 메시지: " + message + " (길이: " + message.length() + ")");

		// COOLSMS API 엔드포인트 URL (v4 API 단일 메시지 전송)
		String apiUrl = "https://api.coolsms.co.kr/messages/v4/send";

		// 현재 UTC 시간을 ISO 8601 형식으로 생성 (예: 2019-07-01T00:41:48Z)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String dateTime = sdf.format(new Date());
		System.out.println("[DEBUG] dateTime: " + dateTime);

		// 16자리 랜덤 Salt 생성
		String salt = generateSalt(16);
		System.out.println("[DEBUG] 생성된 salt: " + salt);

		// Signature 생성: HMAC-SHA256(apiSecret, dateTime + salt)
		String data = dateTime + salt;
		String signature = hmacSha256(apiSecret, data);
		System.out.println("[DEBUG] 생성된 signature: " + signature);

		// Authorization 헤더 구성
		String authorization = "HMAC-SHA256 apiKey=" + apiKey + ", date=" + dateTime + ", salt=" + salt + ", signature="
				+ signature;
		System.out.println("[DEBUG] Authorization 헤더: " + authorization);

		// JSON 요청 본문 구성 (단일 메시지 전송용: "message" 객체 사용)
		JSONObject messageObj = new JSONObject();
		messageObj.put("from", sender); // 발신번호 (사전 등록된 번호)
		messageObj.put("to", phone); // 수신번호 (숫자만, 국가번호는 별도 country 필드 사용)
		messageObj.put("text", message); // 메시지 내용
		messageObj.put("type", "SMS"); // 메시지 타입 (단문 문자: "SMS")

		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("message", messageObj);
		System.out.println("[DEBUG] 전송할 JSON 요청: " + jsonRequest.toString());

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
		System.out.println("[DEBUG] SMS API 응답 코드: " + responseCode);
		if (responseCode != HttpURLConnection.HTTP_OK) {
			InputStreamReader errorStreamReader = new InputStreamReader(conn.getErrorStream(), "UTF-8");
			BufferedReader errorReader = new BufferedReader(errorStreamReader);
			StringBuilder errorSb = new StringBuilder();
			String errorLine;
			while ((errorLine = errorReader.readLine()) != null) {
				errorSb.append(errorLine);
			}
			errorReader.close();
			System.err.println("[DEBUG] 에러 응답 메시지: " + errorSb.toString());
			ModelAndView mav = new ModelAndView("member/smsVerification");
			mav.addObject("error", "SMS 전송 실패: " + errorSb.toString());
			return mav;
		}
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
		System.out.println("[DEBUG] SMS API 응답 메시지: " + responseMessage);
		JSONObject jsonResponse = new JSONObject(responseMessage);

		HttpSession session = request.getSession();
		session.setAttribute("smsCode", smsCode);
		session.setAttribute("smsPhone", phone);

		// 응답 JSON의 statusCode가 "2000"이면 성공으로 간주합니다.
		if ("2000".equals(jsonResponse.optString("statusCode"))) {
			System.out.println("[DEBUG] SMS 전송 성공");
			return new ModelAndView("member/smsSent"); // 전송 완료 페이지 (smsSent.jsp가 member 폴더 내에 있음)
		} else {
			String errorMsg = jsonResponse.optString("error_message", "Unknown error");
			System.err.println("[DEBUG] SMS 전송 실패: " + errorMsg);
			ModelAndView mav = new ModelAndView("member/smsVerification");
			mav.addObject("error", "SMS 전송 실패: " + errorMsg);
			return mav;
		}
	}

	// 5. 사용자로부터 SMS 인증 코드를 입력받아 검증 (verifySmsCode)
	@RequestMapping(value = "/member/verifySmsCode.do", method = RequestMethod.POST)
	public ModelAndView verifySmsCode(@RequestParam("code") String code, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] verifySmsCode() 호출됨, 입력된 code: " + code);
		HttpSession session = request.getSession();
		String storedCode = (String) session.getAttribute("smsCode");
		System.out.println("[DEBUG] 세션에 저장된 smsCode: " + storedCode);
		if (storedCode != null && storedCode.equals(code)) {
			session.setAttribute("isPhoneVerified", true);
			System.out.println("[DEBUG] SMS 인증 성공");
			return new ModelAndView("redirect:/member/findId.do");
		} else {
			System.out.println("[DEBUG] SMS 인증 실패");
			ModelAndView mav = new ModelAndView("member/smsVerification");
			mav.addObject("error", "인증번호가 일치하지 않습니다.");
			return mav;
		}
	}

	// 6. 휴대폰 번호 인증 후, 해당 전화번호로 회원 이메일 찾기 (findId)
	@RequestMapping(value = "/member/findId.do", method = RequestMethod.GET)
	public ModelAndView findId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] findId() 호출됨");
		HttpSession session = request.getSession();
		Boolean verified = (Boolean) session.getAttribute("isPhoneVerified");
		System.out.println("[DEBUG] isPhoneVerified: " + verified);
		if (verified == null || !verified) {
			System.out.println("[DEBUG] 전화번호 미인증 - phoneVerificationForm으로 리다이렉트");
			return new ModelAndView("redirect:/member/phoneVerificationForm.do");
		}
		String phone = (String) session.getAttribute("smsPhone");
		System.out.println("[DEBUG] SMS 인증된 phone: " + phone);
		MemberVO member = memberService.findMemberByPhone(phone);
		// 뷰 이름은 그대로 "member/displayId"로 사용한다고 가정
		ModelAndView mav = new ModelAndView("member/displayId");
		if (member != null) {
			System.out.println("[DEBUG] 회원 찾음, email: " + member.getEmail());
			mav.addObject("email", member.getEmail());
		} else {
			System.out.println("[DEBUG] 해당 번호로 등록된 회원 없음");
			mav.addObject("error", "해당 번호로 등록된 회원이 없습니다.");
		}
		return mav;
	}

	// 7. 휴대폰 번호 인증 후, 비밀번호 재설정을 위한 페이지 이동 (resetPassword 폼 제공)
	@RequestMapping(value = "/member/resetPassword.do", method = RequestMethod.GET)
	public ModelAndView resetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] resetPassword(GET) 호출됨");
		HttpSession session = request.getSession();
		Boolean verified = (Boolean) session.getAttribute("isPhoneVerified");
		System.out.println("[DEBUG] isPhoneVerified: " + verified);
		if (verified == null || !verified) {
			System.out.println("[DEBUG] 전화번호 미인증 - phoneVerificationForm으로 리다이렉트");
			return new ModelAndView("redirect:/member/phoneVerificationForm.do");
		}
		return new ModelAndView("member/resetPasswordForm");
	}

	// 8. 새 비밀번호를 받아 업데이트 (resetPassword 처리)
	@RequestMapping(value = "/member/resetPassword.do", method = RequestMethod.POST)
	public ModelAndView processResetPassword(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("[DEBUG] processResetPassword() 호출됨");
		HttpSession session = request.getSession();
		Boolean verified = (Boolean) session.getAttribute("isPhoneVerified");
		System.out.println("[DEBUG] isPhoneVerified: " + verified);
		if (verified == null || !verified) {
			System.out.println("[DEBUG] 전화번호 미인증 - phoneVerificationForm으로 리다이렉트");
			return new ModelAndView("redirect:/member/phoneVerificationForm.do");
		}
		String newPassword = request.getParameter("newPassword");
		System.out.println("[DEBUG] 새 비밀번호: " + newPassword);
		String phone = (String) session.getAttribute("smsPhone");
		System.out.println("[DEBUG] SMS 인증된 phone: " + phone);
		MemberVO member = memberService.findMemberByPhone(phone);
		if (member != null) {
			member.setPassword(newPassword); // 실제 운영시 비밀번호 암호화 필요
			memberService.updateMember(member);
			session.setAttribute("isPhoneVerified", false);
			System.out.println("[DEBUG] 비밀번호 재설정 완료, 다시 로그인 필요");
			ModelAndView mav = new ModelAndView("redirect:/member/loginForm.do");
			mav.addObject("message", "비밀번호가 재설정되었습니다. 다시 로그인해주세요.");
			return mav;
		} else {
			System.out.println("[DEBUG] 회원 정보를 찾을 수 없음");
			ModelAndView mav = new ModelAndView("member/resetPasswordForm");
			mav.addObject("error", "회원 정보를 찾을 수 없습니다.");
			return mav;
		}
	}

	// --- 헬퍼 메서드 ---

	// 지정된 길이의 랜덤 알파벳+숫자 Salt 문자열 생성 (예: 16자리)
	private String generateSalt(int length) {
		System.out.println("[DEBUG] generateSalt() 호출됨, length: " + length);
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int idx = random.nextInt(chars.length());
			sb.append(chars.charAt(idx));
		}
		String salt = sb.toString();
		System.out.println("[DEBUG] 생성된 Salt: " + salt);
		return salt;
	}

	// HMAC-SHA256 해시를 생성하는 메서드
	private String hmacSha256(String key, String data) throws Exception {
		System.out.println("[DEBUG] hmacSha256() 호출됨");
		Mac mac = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
		mac.init(secretKey);
		byte[] hashBytes = mac.doFinal(data.getBytes("UTF-8"));
		String hash = DatatypeConverter.printHexBinary(hashBytes).toLowerCase();
		System.out.println("[DEBUG] 생성된 HMAC-SHA256 해시: " + hash);
		return hash;
	}
}
