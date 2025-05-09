package egovframework.coing.cmm.util;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class LoginStore {
    
    private static volatile LoginStore loginStore;
    private Map<String, HttpSession> loginSessions;
    
    // private으로 설정
    // 생성자를 호출하지 못하도록 막는다.
    private LoginStore() {
        loginSessions = new HashMap<String, HttpSession>();
    }
    
    public static synchronized LoginStore getInstance() {
        
        if(loginStore == null) {
            loginStore = new LoginStore();
        }
        
        return loginStore;
    }
    
    public void add(String memberId, HttpSession session) {
        loginSessions.put(memberId, session);
    }
    
    public HttpSession get(String memberId) {
        return loginSessions.get(memberId);
    }
    
    public void logout(String memberId) {
        if(loginSessions.containsKey(memberId)) {
            try {
                loginSessions.get(memberId).invalidate();
            } catch(RuntimeException re) {
            }
            loginSessions.remove(memberId);
        }
    }
    
}