/**
 * Trimmed.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 5:01:40 PM
 */

package com.wiselink.controllers.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author leo
 */
@Target({
    ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Trimmed {

}
