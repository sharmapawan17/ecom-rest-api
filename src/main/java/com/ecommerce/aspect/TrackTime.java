package com.ecommerce.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
public @interface TrackTime {
    boolean enabled() default true;
}
