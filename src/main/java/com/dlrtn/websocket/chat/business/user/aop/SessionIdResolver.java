package com.dlrtn.websocket.chat.business.user.aop;

import com.dlrtn.websocket.chat.business.user.model.UserSessionConstants;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import com.dlrtn.websocket.chat.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SessionIdResolver implements HandlerMethodArgumentResolver {

    private static final Class<? extends Annotation> TARGET_ANNOTATION_CLASS = SessionId.class;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TARGET_ANNOTATION_CLASS)
                && parameter.getParameterType() == String.class;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        SessionId sessionIdAnnotation = getSessionIdAnnotation(parameter);
        String sessionId = getSessionId(webRequest);

        if (sessionIdAnnotation.required() && sessionId == null) {
            throw new CommonException("Failed to get exists session id");
        }

        return sessionId;
    }

    private SessionId getSessionIdAnnotation(MethodParameter parameter) {
        return Arrays.stream(parameter.getParameterAnnotations())
                .filter(TARGET_ANNOTATION_CLASS::isInstance)
                .map(SessionId.class::cast)
                .findFirst()
                .orElseThrow(() -> new CommonException("Failed to found target annotation (Will not happen)"));
    }

    private String getSessionId(NativeWebRequest webRequest) {
        return Optional.of(webRequest)
                .map(request -> (HttpServletRequest) request.getNativeRequest())
                .map(req -> CookieUtils.getCookie(req, UserSessionConstants.SESSION_ID_COOKIE_NAME))
                .orElse(null);
    }

}
