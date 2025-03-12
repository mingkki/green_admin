package egovframework.coing.cmm.util;

import egovframework.coing.cmm.EnumModel;
import egovframework.coing.cmm.EnumValue;

import java.util.ArrayList;
import java.util.List;

public class EnumUtil {
    
    public static List<EnumValue> getList(Class<? extends EnumModel> e) {
        List<EnumValue> enumValues = new ArrayList<>();
        for(EnumModel enumType : e.getEnumConstants()) {
            enumValues.add(new EnumValue(enumType));
        }
        return enumValues;
    }
    
    public static EnumValue getValue(Class<? extends EnumModel> e, String key) {
        for(EnumModel enumType : e.getEnumConstants()) {
            if(enumType.getKey().equals(key)) {
                return new EnumValue(enumType);
            }
        }
        
        return null;
    }
    
}