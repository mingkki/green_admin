package egovframework.coing.cmm.web;

import egovframework.coing.cmm.util.AddressUtil;
import egovframework.coing.cmm.vo.LatLngVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/coord.do")
public class CoordRestController {
    
    private final AddressUtil addressUtil;
    
    @GetMapping(params = "act=getCoord")
    public ResponseEntity<?> getCoord(@RequestParam String address) throws Exception {
        LatLngVO vo = new LatLngVO();
        
        try {
            JSONObject coord = addressUtil.getCoord(address);
            vo.setLat(Double.valueOf(coord.get("y").toString()));
            vo.setLng(Double.valueOf(coord.get("x").toString()));
        } catch(Exception e) {
            vo.setLat(0.0);
            vo.setLng(0.0);
        }
        
        return ResponseEntity.ok(vo);
    }
    
}