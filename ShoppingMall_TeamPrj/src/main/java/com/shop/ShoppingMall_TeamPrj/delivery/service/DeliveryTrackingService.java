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
    // API Base URL (����)
    private static final String TRACKER_URL = "https://apis.tracker.delivery/carriers/";

    // ������� �ù�� �ڵ�� �̸� ����
    private HashMap<String, String> trackerMap = new HashMap<String, String>();
    
    public DeliveryTrackingService() {
        // ��� �ù�� ���� (��ó: https://tracker.delivery/guide �� ��� ����)
        trackerMap.put("de.dhl", "DHL");
        trackerMap.put("jp.sagawa", "Sagawa");
        trackerMap.put("jp.yamato", "Kuroneko Yamato");
        trackerMap.put("jp.yuubin", "Japan Post");
        trackerMap.put("kr.chunilps", "õ���ù�");
        trackerMap.put("kr.cjlogistics", "CJ�������");
        trackerMap.put("kr.cupost", "CU �������ù�");
        trackerMap.put("kr.cvsnet", "GS Postbox �ù�");
        trackerMap.put("kr.cway", "CWAY (Woori Express)");
        trackerMap.put("kr.daesin", "����ù�");
        trackerMap.put("kr.epost", "��ü�� �ù�");
        trackerMap.put("kr.hanips", "���ǻ���ù�");
        trackerMap.put("kr.hanjin", "�����ù�");
        trackerMap.put("kr.hdexp", "�յ��ù�");
        trackerMap.put("kr.homepick", "Ȩ��");
        trackerMap.put("kr.honamlogis", "�Ѽ�ȣ���ù�");
        trackerMap.put("kr.ilyanglogis", "�Ͼ������");
        trackerMap.put("kr.kdexp", "�浿�ù�");
        trackerMap.put("kr.kunyoung", "�ǿ��ù�");
        trackerMap.put("kr.logen", "�����ù�");
        trackerMap.put("kr.lotte", "�Ե��ù�");
        trackerMap.put("kr.slx", "SLX");
        trackerMap.put("kr.swgexp", "�����۷ι�ī��");
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
     * �ù�� API ��ȸ
     * url: TRACKER_URL + carrierId + "/tracks/" + trackId (�����ȣ���� "-" ����)
     */
    public JSONObject callLogAPI(String carrierId, String trackId) {
        HashMap<String, String> trackers = getTrackerName();
        String deliComName = trackers.get(carrierId);
        
        String responseBody = "";
        JSONObject retVal = new JSONObject();
        
        String apiURL = TRACKER_URL + carrierId + "/tracks/" + trackId.replaceAll("-", "");
        
        responseBody = GETMethod(apiURL);
        // ���� Ȯ��
        JSONObject statusJson = new JSONObject(responseBody);
        JSONObject carrierJson = new JSONObject();
        JSONArray progressJson = new JSONArray();
        
        String mess = statusJson.has("message") ? statusJson.get("message").toString() : "";
        
        // ������ ���� ��� ó��
        if (!mess.equals("")) {
            retVal.put("description", "�����ô� ���Բ��� ��ǰ �߼� �غ����Դϴ�.");
            retVal.put("deliveryWorker", "����");
            retVal.put("time", "����");
            retVal.put("location", "����");
            retVal.put("status", "�߼��غ�");
            retVal.put("carrierId", trackId);
            retVal.put("company", deliComName);
            
            JSONObject tempJson = new JSONObject(); 
            JSONObject tempJson2 = new JSONObject(); 
            
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            String datestr = format.format(Calendar.getInstance().getTime());
            
            tempJson.put("description", "");
            tempJson2.put("name", "�߼��غ�");
            tempJson.put("location", tempJson2);
            tempJson.put("time", datestr);
            
            tempJson2 = new JSONObject();
            tempJson2.put("id", "at_packing");
            tempJson2.put("text", "�߼��غ�");
            
            tempJson.put("status", tempJson2);
            progressJson.put(tempJson);
            
            retVal.put("allProgress", progressJson);
            
            return retVal;
        }
        
        // API ����� Ȯ��
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
    
    // ��ȣ�� ���� ���� (��۱�� �̸� �� ��ȭ��ȣ ����)
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
    
    // GET ȣ�� �޼���
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
                return "{\"message\":\"�����ô� ���Բ��� ��ǰ �߼� �غ����Դϴ�.\"}";
            }
        } catch (IOException e) {
            throw new RuntimeException("API ��û�� ���� ����", e);
        }
    }
    
    // API ���� �б� �޼���
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
            throw new RuntimeException("API ������ �дµ� �����߽��ϴ�.", e);
        } finally {
            if (lineReader != null) {
                try {
                    lineReader.close();
                } catch (IOException e) {
                    // ���� �α� �Ǵ� ����
                }
            }
        }
    }

}
