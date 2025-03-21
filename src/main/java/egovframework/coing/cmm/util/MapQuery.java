package egovframework.coing.cmm.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class MapQuery {
    public static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch(UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
    
    public static String urlEncodeUTF8(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<?, ?> entry : map.entrySet()) {
            if(entry.getValue() == null) {
                continue;
            }
            if(sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s", urlEncodeUTF8(entry.getKey().toString()), urlEncodeUTF8(entry.getValue().toString())));
        }
        return sb.toString();
    }
}