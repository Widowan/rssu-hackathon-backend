package com.hypnotoad.configurations;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class ErrorController extends DefaultErrorAttributes {
    @Override
    public Map<String,Object> getErrorAttributes(
            WebRequest webRequest,
            ErrorAttributeOptions options
    ) {
        var errorAttributes = super.getErrorAttributes(webRequest, options);
        return Map.of("ok", false, "reason", errorAttributes.get("error"));
    }
}
