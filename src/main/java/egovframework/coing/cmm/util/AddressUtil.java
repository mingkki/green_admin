package egovframework.coing.cmm.util;

import egovframework.coing.cmm.Globals;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class AddressUtil {
    
    public JSONObject getCoord(String searchAddr) throws UnsupportedEncodingException {
        
        String apikey = Globals.INTERFO_API_KEY;
        String searchType = "road";
        String epsg = "epsg:4326";
        
        StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
        sb.append("?service=address");
        sb.append("&request=getCoord");
        sb.append("&version=2.0");
        sb.append("&format=json");
        sb.append("&crs=").append(epsg);
        sb.append("&key=").append(apikey);
        sb.append("&type=").append(searchType);
        sb.append("&address=").append(URLEncoder.encode(searchAddr, String.valueOf(StandardCharsets.UTF_8)));
        
        try {
            URL url = new URL(sb.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            
            JSONParser jspa = new JSONParser();
            JSONObject jsob = (JSONObject) jspa.parse(reader);
            JSONObject jsrs = (JSONObject) jsob.get("response");
            JSONObject jsResult = (JSONObject) jsrs.get("result");
            JSONObject jspoitn = (JSONObject) jsResult.get("point");
            
            return jspoitn;
        } catch(IOException |
                ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
}