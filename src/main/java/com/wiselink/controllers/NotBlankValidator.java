/**
 * NotBlankValidator.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-7 上午10:47:49
 */
package com.wiselink.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.ParamValidator;
import net.paoding.rose.web.paramresolver.ParamMetaData;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.wiselink.base.ApiStatus;

/**
 * @author leo
 */
public class NotBlankValidator implements ParamValidator {
    @Override
    public boolean supports(ParamMetaData metaData) {
        return metaData.getAnnotation(NotBlank.class) != null;
    }

    @Override
    public Object validate(ParamMetaData metaData, Invocation inv, Object target, Errors errors) {
        String paramName = metaData.getParamName();
        String value = inv.getParameter(paramName);
        if (StringUtils.isBlank(value)) {
            return BaseController.failResult(ApiStatus.INVALID_PARAMETER);
        }
        return null;
    }
}
