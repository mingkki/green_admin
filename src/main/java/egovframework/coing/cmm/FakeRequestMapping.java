package egovframework.coing.cmm;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FakeRequestMapping {
    
    String value() default "";
    
}