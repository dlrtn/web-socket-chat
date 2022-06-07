package com.dlrtn.websocket.chat.business.user.aop;

import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.repository.UserSessionRepository;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SessionUserResolver implements HandlerMethodArgumentResolver {

    private static final Class<?> TARGET_ANNOTATION_CLASS = SessionUser.class;
    private final UserSessionRepository userSessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TARGET_ANNOTATION_CLASS)
                && parameter.getParameterType() == User.class;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        SessionUser sessionUserAnnotation = getSessionUserAnnotation(parameter);
        User sessionUser = getSessionUser(webRequest);

        if (ObjectUtils.isEmpty(sessionUserAnnotation.required())) {
            throw new CommonException(String.format("Failed to get exits session user, sessionId : %s", webRequest.getSessionId()));
        }

        return sessionUser;
    }

    private SessionUser getSessionUserAnnotation(MethodParameter parameter) {
        return Arrays.stream(parameter.getParameterAnnotations())
                .filter(TARGET_ANNOTATION_CLASS::isInstance)
                .map(TARGET_ANNOTATION_CLASS::cast)
                .findFirst()
                .orElseThrow(() -> new CommonException("Failed to found target annotation (Will not happen)"));
    }

    private User getSessionUser(NativeWebRequest webRequest) {
        return Optional.of(webRequest.getSessionId())
                .flatMap(userSessionRepository::findById)
                .orElseThrow(() -> new CommonException(String.format("Failed to found user, sessionId : %s", webRequest.getSessionId())))
                .getSessionUser();
    }

}
