package com.shop.ShoppingMall_TeamPrj.delivery.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class DeliveryTrackingService {
    // API Base URL (예시)
    private static final String TRACKER_URL = "https://apis.tracker.delivery/carriers/";

    // 배송추적 택배사 코드와 이름 매핑
    private HashMap<String, String> trackerMap = new HashMap<String, String>();
    
    public DeliveryTrackingService() {
        // 모든 택배사 매핑 (출처: https://tracker.delivery/guide 의 목록 참고)
        trackerMap.put("de.dhl", "DHL");
        trackerMap.put("jp.sagawa", "Sagawa");
        trackerMap.put("jp.yamato", "Kuroneko Yamato");
        trackerMap.put("jp.yuubin", "Japan Post");
        trackerMap.put("kr.chunilps", "천일택배");
        trackerMap.put("kr.cjlogistics", "CJ대한통운");
        trackerMap.put("kr.cupost", "CU 편의점택배");
        trackerMap.put("kr.cvsnet", "GS Postbox 택배");
        trackerMap.put("kr.cway", "CWAY (Woori Express)");
        trackerMap.put("kr.daesin", "대신택배");
        trackerMap.put("kr.epost", "우체국 택배");
        trackerMap.put("kr.hanips", "한의사랑택배");
        trackerMap.put("kr.hanjin", "한진택배");
        trackerMap.put("kr.hdexp", "합동택배");
        trackerMap.put("kr.homepick", "홈픽");
        trackerMap.put("kr.honamlogis", "한서호남택배");
        trackerMap.put("kr.ilyanglogis", "일양로지스");
        trackerMap.put("kr.kdexp", "경동택배");
        trackerMap.put("kr.kunyoung", "건영택배");
        trackerMap.put("kr.logen", "로젠택배");
        trackerMap.put("kr.lotte", "롯데택배");
        trackerMap.put("kr.slx", "SLX");
        trackerMap.put("kr.swgexp", "성원글로벌카고");
        trackerMap.put("nl.tnt", "TNT");
        trackerMap.put("un.upu.ems", "EMS");
        trackerMap.put("us.fedex", "Fedex");
        trackerMap.put("us.ups", "UPS");
        trackerMap.put("us.usps", "USPS");
    }
    
    public HashMap<String, String> getTrackerName() {
        return trackerMap;
    }
    
    /*
     * 택배사 API 조회
     * url: TRACKER_URL + carrierId + "/tracks/" + trackId (송장번호에서 "-" 제거)
     */
    public JSONObject callLogAPI(String carrierId, String trackId) {
        HashMap<String, String> trackers = getTrackerName();
        String deliComName = trackers.get(carrierId);
        
        String responseBody = "";
        JSONObject retVal = new JSONObject();
        
        String apiURL = TRACKER_URL + carrierId + "/tracks/" + trackId.replaceAll("-", "");
        
        responseBody = GETMethod(apiURL);
        // 응답 확인
        JSONObject statusJson = new JSONObject(responseBody);
        JSONObject carrierJson = new JSONObject();
        JSONArray progressJson = new JSONArray();
        
        String mess = statusJson.has("message") ? statusJson.get("message").toString() : "";
        
        // 내용이 없는 경우 처리
        if (!mess.equals("")) {
            retVal.put("description", "보내시는 고객님께서 상품 발송 준비중입니다.");
            retVal.put("deliveryWorker", "미정");
            retVal.put("time", "미정");
            retVal.put("location", "미정");
            retVal.put("status", "발송준비");
            retVal.put("carrierId", trackId);
            retVal.put("company", deliComName);
            
            JSONObject tempJson = new JSONObject(); 
            JSONObject tempJson2 = new JSONObject(); 
            
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            String datestr = format.format(Calendar.getInstance().getTime());
            
            tempJson.put("description", "");
            tempJson2.put("name", "발송준비");
            tempJson.put("location", tempJson2);
            tempJson.put("time", datestr);
            
            tempJson2 = new JSONObject();
            tempJson2.put("id", "at_packing");
            tempJson2.put("text", "발송준비");
            
            tempJson.put("status", tempJson2);
            progressJson.put(tempJson);
            
            retVal.put("allProgress", progressJson);
            
            return retVal;
        }
        
        // API 결과값 확인
        carrierJson = new JSONObject(statusJson.get("carrier").toString());
        progressJson = new JSONArray(statusJson.get("progresses").toString());
        statusJson = new JSONObject(statusJson.get("state").toString());
        
        for (int i = 0; i < progressJson.length(); i++) {
            JSONObject initJson = progressJson.getJSONObject(i);
            JSONObject stat = initJson.getJSONObject("status");
            
            if (statusJson.get("id").equals(stat.get("id"))) {
                String[] delBracketText = new String[2];
                try {
                    delBracketText = deleteBracket(initJson.getString("description"));
                    stat = initJson.getJSONObject("location");
                    retVal.put("description", delBracketText[0] == null ? "" : delBracketText[0]);
                    retVal.put("deliveryWorker", delBracketText[1] == null ? "" : delBracketText[1]);
                    retVal.put("time", initJson.getString("time"));
                    retVal.put("location", stat.getString("name"));
                    retVal.put("company", deliComName);
                    retVal.put("allProgress", progressJson);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        retVal.put("status", statusJson.getString("text"));
        retVal.put("carrierId", carrierJson.getString("id"));
        return retVal;
    }
    
    // 괄호내 정보 제거 (배송기사 이름 및 전화번호 제거)
    static java.util.regex.Pattern PATTERN_BRACKET = java.util.regex.Pattern.compile("\\([^\\(\\)]+\\)");
    public static String[] deleteBracket(String text) throws Exception {
        java.util.regex.Matcher matcher = PATTERN_BRACKET.matcher(text);
        
        String removeText = text;
        String pureText = text;
        String VOID = "";
        
        matcher = PATTERN_BRACKET.matcher(pureText);
        while (matcher.find()) {
            int startIndex = matcher.start();
            int endIndex = matcher.end();
            
            removeText = pureText.substring(startIndex, endIndex);
            pureText = pureText.replace(removeText, VOID);
            matcher = PATTERN_BRACKET.matcher(pureText);
        }
        
        String[] values = new String[2];
        values[0] = pureText;
        values[1] = removeText;
        
        return values;
    }
    
    // GET 호출 메서드
    private static String GETMethod(String apiUrl) {
        System.out.println(apiUrl);
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            
            int code = connection.getResponseCode();
            if (code == 200) {
                return readBodyAPI(connection.getInputStream());
            } else {
                return "{\"message\":\"보내시는 고객님께서 상품 발송 준비중입니다.\"}";
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        }
    }
    
    // API 응답 읽기 메서드
    private static String readBodyAPI(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);
        BufferedReader lineReader = null;
        try {
            lineReader = new BufferedReader(streamReader);
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        } finally {
            if (lineReader != null) {
                try {
                    lineReader.close();
                } catch (IOException e) {
                    // 에러 로깅 또는 무시
                }
            }
        }
    }

}
