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
import javax.xml.bind.DatatypeConverter; // JAXB API �ʿ�
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient; // Java 1.6 ȯ�濡 �´� Apache HttpClient ���
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

	// === īī�� �Ҽ� �α��� ���� ��� (ȯ�濡 �°� ����) ===
	private static final String KAKAO_CLIENT_ID = "d3841b71b544ce26f9b2628a716d6ee1";
	private static final String KAKAO_REDIRECT_URI = "http://localhost:8090/ShoppingMall_TeamPrj/member/kakaoCallback.do";

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberVO memberVO;

	// ���� ������ ("/" �� "/main.do")
	@RequestMapping(value = { "/", "/main.do" }, method = RequestMethod.GET)
	private ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] main() ȣ���");
		String viewName = (String) request.getAttribute("viewName");
		System.out.println("[DEBUG] viewName: " + viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/listMembers.do", method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] listMembers() ȣ���");
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
		System.out.println("[DEBUG] updateMember() ȣ���");
		int result = memberService.updateMember(memberVO);
		System.out.println("[DEBUG] update ���: " + result);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		if (result > 0) {
			mav.addObject("message", "ȸ�� ���� ���� ����");
		} else {
			mav.addObject("message", "ȸ�� ���� ���� ����");
		}
		return mav;
	}

	@RequestMapping(value = "/member/updateMemberForm.do", method = RequestMethod.GET)
	public ModelAndView showMemberManagement(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("[DEBUG] showMemberManagement() ȣ���");
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
		System.out.println("[DEBUG] addMember() ȣ���");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		int result = memberService.addMember(member);
		System.out.println("[DEBUG] addMember ���: " + result);
		HttpSession session = request.getSession();
		session.setAttribute("welcomeMessage", member.getUser_name() + "�� ������ ȯ���մϴ�. �ٽ� �α������ּ���.");
		return new ModelAndView("redirect:/main/main.do");
	}

	@Override
	@RequestMapping(value = "/member/removeMember.do", method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] removeMember() ȣ���, id: " + id);
		request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member, RedirectAttributes rAttr,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] login() ȣ���");
		ModelAndView mav = new ModelAndView();
		memberVO = memberService.login(member);

		if (memberVO == null) {
			System.out.println("[DEBUG] �α��� ����: memberVO�� null�Դϴ�.");
		} else {
			System.out.println("[DEBUG] �α��� ����: " + memberVO.getUser_id());
		}

		if (memberVO != null) {
			HttpSession session = request.getSession();
			session.setAttribute("memberInfo", memberVO);
			session.setAttribute("user_id", memberVO.getUser_id()); // user_id ����
			 session.setAttribute("role", memberVO.getRole());
			session.setAttribute("isLogOn", true);

			String action = (String) session.getAttribute("action");
			System.out.println("[DEBUG] action: " + action);
			session.removeAttribute("action");

			if (action != null) {
				mav.setViewName("redirect:" + action);
			} else {
				if ("ADMIN".equals(memberVO.getRole())) {
					System.out.println("[DEBUG] ������ �α��� detected");
					mav.setViewName("redirect:/admin/listProducts.do");
				} else {
					System.out.println("[DEBUG] �Ϲ� ����� �α��� detected");
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
	    System.out.println("[DEBUG] logout() ȣ���");
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();  // ���� ��ü�� ��ȿȭ�Ͽ� ��� �Ӽ� ����
	    }
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("redirect:/main/main.do");
	    return mav;
	}


	@RequestMapping(value = "/member/*Form.do", method = RequestMethod.GET)
	private ModelAndView form(@RequestParam(value = "result", required = false) String result,
			@RequestParam(value = "action", required = false) String action, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] form() ȣ���, result: " + result + ", action: " + action);
		String viewName = (String) request.getAttribute("viewName");
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result", result);
		mav.setViewName(viewName);
		return mav;
	}

	private String getViewName(HttpServletRequest request) throws Exception {
		System.out.println("[DEBUG] getViewName() ȣ���");
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
		System.out.println("[DEBUG] ���� viewName: " + viewName);
		return viewName;
	}

	// ==========================================================
	// īī�� �Ҽ� �α��� ��� �߰� �� ���� ��� üũ
	// ==========================================================

	// 1. īī�� ���� �������� �����̷�Ʈ
	@RequestMapping(value = "/member/kakaoLogin.do", method = RequestMethod.GET)
	public ModelAndView kakaoLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] kakaoLogin() ȣ���");
		String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize" + "?client_id=" + KAKAO_CLIENT_ID
				+ "&redirect_uri=" + URLEncoder.encode(KAKAO_REDIRECT_URI, "UTF-8") + "&response_type=code";
		System.out.println("[DEBUG] kakaoAuthUrl: " + kakaoAuthUrl);
		return new ModelAndView("redirect:" + kakaoAuthUrl);
	}

	// 2. īī�� �ݹ� ó��
	@RequestMapping(value = "/member/kakaoCallback.do", method = RequestMethod.GET)
	public ModelAndView kakaoCallback(@RequestParam("code") String code, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] kakaoCallback() ȣ���, code: " + code);
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
			System.out.println("[DEBUG] ���� ȸ�� �߰�");
			session.setAttribute("memberInfo", existingMember);
			session.setAttribute("isLogOn", true);
			if (existingMember.getPhone_number() == null || existingMember.getPhone_number().trim().isEmpty()
					|| existingMember.getAddress() == null || existingMember.getAddress().trim().isEmpty()
					|| existingMember.getDate_of_birth() == null) {
				session.setAttribute("isProfileComplete", false);
				System.out.println("[DEBUG] ������ ���� �ʿ�");
				return new ModelAndView("redirect:/member/supplementRegistrationForm.do");
			} else {
				session.setAttribute("isProfileComplete", true);
			}
		} else {
			System.out.println("[DEBUG] �ű� ȸ�� ����");
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

	// 3. ���� ��� ������ ������Ʈ�ϴ� �޼���
	@RequestMapping(value = "/member/updateSupplementInfo.do", method = RequestMethod.POST)
	public ModelAndView updateSupplementInfo(@ModelAttribute("member") MemberVO updatedMember,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] updateSupplementInfo() ȣ���");
		HttpSession session = request.getSession(false);
		if (session == null) {
			System.out.println("[DEBUG] ���� ���� - loginForm���� �����̷�Ʈ");
			return new ModelAndView("redirect:/member/loginForm.do");
		}

		MemberVO currentMember = (MemberVO) session.getAttribute("memberInfo");
		if (currentMember == null) {
			System.out.println("[DEBUG] memberInfo ���� - loginForm���� �����̷�Ʈ");
			return new ModelAndView("redirect:/member/loginForm.do");
		}

		String phone = updatedMember.getPhone_number();
		String address = updatedMember.getAddress();
		java.sql.Date dob = updatedMember.getDate_of_birth();
		System.out.println("[DEBUG] �Է¹��� phone: " + phone + ", address: " + address + ", dob: " + dob);

		if (phone == null || phone.trim().isEmpty() || address == null || address.trim().isEmpty() || dob == null) {
			session.setAttribute("isProfileComplete", false);
			System.out.println("[DEBUG] �Է°� �̺� - supplementRegistrationForm���� �����̷�Ʈ");
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
	// ID �� ��й�ȣ ã�� (�޴��� ����) ���� �޼��� �߰�
	// ==========================================================

	// 4. �޴��� ��ȣ �Է� �� SMS ���� �ڵ� ���� (sendSmsCode) - COOLSMS API HMAC ���� ��� ����
	// (CoolSMS v4 API�� �°�, ���� �޽��� ���� �� "message" ��ü ���)
	@RequestMapping(value = "/member/sendSmsCode.do", method = RequestMethod.POST)
	public ModelAndView sendSmsCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] sendSmsCode() ȣ���");
		String phone = request.getParameter("phone");
		System.out.println("[DEBUG] �Է¹��� phone: " + phone);
		String smsCode = String.format("%06d", (int) (Math.random() * 1000000));
		System.out.println("[DEBUG] ������ smsCode: " + smsCode);

		// COOLSMS API ���� ����
		String apiKey = "NCSFS9YEWRE4BX0K"; // �ڽ��� API Key�� ����
		String apiSecret = "KNANEAV2RYUXBDJP59PYA0JVVNZHKLGO"; // �ڽ��� API Secret�� ����
		String sender = "01064811904"; // ������ ��ϵ� �߽Ź�ȣ
		String message = "Your verification code is: " + smsCode;
		System.out.println("[DEBUG] ������ �޽���: " + message + " (����: " + message.length() + ")");

		// COOLSMS API ��������Ʈ URL (v4 API ���� �޽��� ����)
		String apiUrl = "https://api.coolsms.co.kr/messages/v4/send";

		// ���� UTC �ð��� ISO 8601 �������� ���� (��: 2019-07-01T00:41:48Z)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String dateTime = sdf.format(new Date());
		System.out.println("[DEBUG] dateTime: " + dateTime);

		// 16�ڸ� ���� Salt ����
		String salt = generateSalt(16);
		System.out.println("[DEBUG] ������ salt: " + salt);

		// Signature ����: HMAC-SHA256(apiSecret, dateTime + salt)
		String data = dateTime + salt;
		String signature = hmacSha256(apiSecret, data);
		System.out.println("[DEBUG] ������ signature: " + signature);

		// Authorization ��� ����
		String authorization = "HMAC-SHA256 apiKey=" + apiKey + ", date=" + dateTime + ", salt=" + salt + ", signature="
				+ signature;
		System.out.println("[DEBUG] Authorization ���: " + authorization);

		// JSON ��û ���� ���� (���� �޽��� ���ۿ�: "message" ��ü ���)
		JSONObject messageObj = new JSONObject();
		messageObj.put("from", sender); // �߽Ź�ȣ (���� ��ϵ� ��ȣ)
		messageObj.put("to", phone); // ���Ź�ȣ (���ڸ�, ������ȣ�� ���� country �ʵ� ���)
		messageObj.put("text", message); // �޽��� ����
		messageObj.put("type", "SMS"); // �޽��� Ÿ�� (�ܹ� ����: "SMS")

		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("message", messageObj);
		System.out.println("[DEBUG] ������ JSON ��û: " + jsonRequest.toString());

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
		System.out.println("[DEBUG] SMS API ���� �ڵ�: " + responseCode);
		if (responseCode != HttpURLConnection.HTTP_OK) {
			InputStreamReader errorStreamReader = new InputStreamReader(conn.getErrorStream(), "UTF-8");
			BufferedReader errorReader = new BufferedReader(errorStreamReader);
			StringBuilder errorSb = new StringBuilder();
			String errorLine;
			while ((errorLine = errorReader.readLine()) != null) {
				errorSb.append(errorLine);
			}
			errorReader.close();
			System.err.println("[DEBUG] ���� ���� �޽���: " + errorSb.toString());
			ModelAndView mav = new ModelAndView("member/smsVerification");
			mav.addObject("error", "SMS ���� ����: " + errorSb.toString());
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
		System.out.println("[DEBUG] SMS API ���� �޽���: " + responseMessage);
		JSONObject jsonResponse = new JSONObject(responseMessage);

		HttpSession session = request.getSession();
		session.setAttribute("smsCode", smsCode);
		session.setAttribute("smsPhone", phone);

		// ���� JSON�� statusCode�� "2000"�̸� �������� �����մϴ�.
		if ("2000".equals(jsonResponse.optString("statusCode"))) {
			System.out.println("[DEBUG] SMS ���� ����");
			return new ModelAndView("member/smsSent"); // ���� �Ϸ� ������ (smsSent.jsp�� member ���� ���� ����)
		} else {
			String errorMsg = jsonResponse.optString("error_message", "Unknown error");
			System.err.println("[DEBUG] SMS ���� ����: " + errorMsg);
			ModelAndView mav = new ModelAndView("member/smsVerification");
			mav.addObject("error", "SMS ���� ����: " + errorMsg);
			return mav;
		}
	}

	// 5. ����ڷκ��� SMS ���� �ڵ带 �Է¹޾� ���� (verifySmsCode)
	@RequestMapping(value = "/member/verifySmsCode.do", method = RequestMethod.POST)
	public ModelAndView verifySmsCode(@RequestParam("code") String code, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] verifySmsCode() ȣ���, �Էµ� code: " + code);
		HttpSession session = request.getSession();
		String storedCode = (String) session.getAttribute("smsCode");
		System.out.println("[DEBUG] ���ǿ� ����� smsCode: " + storedCode);
		if (storedCode != null && storedCode.equals(code)) {
			session.setAttribute("isPhoneVerified", true);
			System.out.println("[DEBUG] SMS ���� ����");
			return new ModelAndView("redirect:/member/findId.do");
		} else {
			System.out.println("[DEBUG] SMS ���� ����");
			ModelAndView mav = new ModelAndView("member/smsVerification");
			mav.addObject("error", "������ȣ�� ��ġ���� �ʽ��ϴ�.");
			return mav;
		}
	}

	// 6. �޴��� ��ȣ ���� ��, �ش� ��ȭ��ȣ�� ȸ�� �̸��� ã�� (findId)
	@RequestMapping(value = "/member/findId.do", method = RequestMethod.GET)
	public ModelAndView findId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] findId() ȣ���");
		HttpSession session = request.getSession();
		Boolean verified = (Boolean) session.getAttribute("isPhoneVerified");
		System.out.println("[DEBUG] isPhoneVerified: " + verified);
		if (verified == null || !verified) {
			System.out.println("[DEBUG] ��ȭ��ȣ ������ - phoneVerificationForm���� �����̷�Ʈ");
			return new ModelAndView("redirect:/member/phoneVerificationForm.do");
		}
		String phone = (String) session.getAttribute("smsPhone");
		System.out.println("[DEBUG] SMS ������ phone: " + phone);
		MemberVO member = memberService.findMemberByPhone(phone);
		// �� �̸��� �״�� "member/displayId"�� ����Ѵٰ� ����
		ModelAndView mav = new ModelAndView("member/displayId");
		if (member != null) {
			System.out.println("[DEBUG] ȸ�� ã��, email: " + member.getEmail());
			mav.addObject("email", member.getEmail());
		} else {
			System.out.println("[DEBUG] �ش� ��ȣ�� ��ϵ� ȸ�� ����");
			mav.addObject("error", "�ش� ��ȣ�� ��ϵ� ȸ���� �����ϴ�.");
		}
		return mav;
	}

	// 7. �޴��� ��ȣ ���� ��, ��й�ȣ �缳���� ���� ������ �̵� (resetPassword �� ����)
	@RequestMapping(value = "/member/resetPassword.do", method = RequestMethod.GET)
	public ModelAndView resetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[DEBUG] resetPassword(GET) ȣ���");
		HttpSession session = request.getSession();
		Boolean verified = (Boolean) session.getAttribute("isPhoneVerified");
		System.out.println("[DEBUG] isPhoneVerified: " + verified);
		if (verified == null || !verified) {
			System.out.println("[DEBUG] ��ȭ��ȣ ������ - phoneVerificationForm���� �����̷�Ʈ");
			return new ModelAndView("redirect:/member/phoneVerificationForm.do");
		}
		return new ModelAndView("member/resetPasswordForm");
	}

	// 8. �� ��й�ȣ�� �޾� ������Ʈ (resetPassword ó��)
	@RequestMapping(value = "/member/resetPassword.do", method = RequestMethod.POST)
	public ModelAndView processResetPassword(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("[DEBUG] processResetPassword() ȣ���");
		HttpSession session = request.getSession();
		Boolean verified = (Boolean) session.getAttribute("isPhoneVerified");
		System.out.println("[DEBUG] isPhoneVerified: " + verified);
		if (verified == null || !verified) {
			System.out.println("[DEBUG] ��ȭ��ȣ ������ - phoneVerificationForm���� �����̷�Ʈ");
			return new ModelAndView("redirect:/member/phoneVerificationForm.do");
		}
		String newPassword = request.getParameter("newPassword");
		System.out.println("[DEBUG] �� ��й�ȣ: " + newPassword);
		String phone = (String) session.getAttribute("smsPhone");
		System.out.println("[DEBUG] SMS ������ phone: " + phone);
		MemberVO member = memberService.findMemberByPhone(phone);
		if (member != null) {
			member.setPassword(newPassword); // ���� ��� ��й�ȣ ��ȣȭ �ʿ�
			memberService.updateMember(member);
			session.setAttribute("isPhoneVerified", false);
			System.out.println("[DEBUG] ��й�ȣ �缳�� �Ϸ�, �ٽ� �α��� �ʿ�");
			ModelAndView mav = new ModelAndView("redirect:/member/loginForm.do");
			mav.addObject("message", "��й�ȣ�� �缳���Ǿ����ϴ�. �ٽ� �α������ּ���.");
			return mav;
		} else {
			System.out.println("[DEBUG] ȸ�� ������ ã�� �� ����");
			ModelAndView mav = new ModelAndView("member/resetPasswordForm");
			mav.addObject("error", "ȸ�� ������ ã�� �� �����ϴ�.");
			return mav;
		}
	}

	// --- ���� �޼��� ---

	// ������ ������ ���� ���ĺ�+���� Salt ���ڿ� ���� (��: 16�ڸ�)
	private String generateSalt(int length) {
		System.out.println("[DEBUG] generateSalt() ȣ���, length: " + length);
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int idx = random.nextInt(chars.length());
			sb.append(chars.charAt(idx));
		}
		String salt = sb.toString();
		System.out.println("[DEBUG] ������ Salt: " + salt);
		return salt;
	}

	// HMAC-SHA256 �ؽø� �����ϴ� �޼���
	private String hmacSha256(String key, String data) throws Exception {
		System.out.println("[DEBUG] hmacSha256() ȣ���");
		Mac mac = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
		mac.init(secretKey);
		byte[] hashBytes = mac.doFinal(data.getBytes("UTF-8"));
		String hash = DatatypeConverter.printHexBinary(hashBytes).toLowerCase();
		System.out.println("[DEBUG] ������ HMAC-SHA256 �ؽ�: " + hash);
		return hash;
	}
}
