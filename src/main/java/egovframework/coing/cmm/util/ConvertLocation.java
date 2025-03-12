package egovframework.coing.cmm.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ConvertLocation {
    public static Map<String, String> convertAddrToLocation(String addr, String addrType, String convertApiKey) {
        String apiURL = "http://api.vworld.kr/req/address";
        
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("x", "");
        resultMap.put("y", "");
        if("".equals(addrType) || addrType == null) {
            return resultMap;
        }
        try {
            int responseCode = 0;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            String keyword = addr;
            String text_content = URLEncoder.encode(keyword.toString(), "utf-8");
            String type = "";
            if("R".equals(addrType)) {
                type = "road";
            } else if("J".equals(addrType)) {
                type = "parcel";
            }
            
            // post request
            String postParams = "service=address";
            postParams += "&request=getcoord";
            postParams += "&version=2.0";
            postParams += "&crs=EPSG:4326";
            postParams += "&address=" + text_content;
            postParams += "&arefine=true";
            postParams += "&simple=false";
            postParams += "&format=json";
            postParams += "&type=" + type;
            postParams += "&errorFormat=json";
            postParams += "&key=" + convertApiKey;
            
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            responseCode = con.getResponseCode();
            BufferedReader br;
            
            if(responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println("response : " + response);
            HashMap<String, Object> rs = new ObjectMapper().readValue(response.toString(), HashMap.class);
            rs = (HashMap<String, Object>) rs.get("response");
            rs = (HashMap<String, Object>) rs.get("result");
            rs = (HashMap<String, Object>) rs.get("point");
            resultMap.put("x", rs.get("x").toString());
            resultMap.put("y", rs.get("y").toString());
            br.close();
            con.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return resultMap;
    }
}