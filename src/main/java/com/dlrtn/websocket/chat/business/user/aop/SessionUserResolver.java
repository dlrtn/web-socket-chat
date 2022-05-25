package com.dlrtn.websocket.chat.business.user.aop;

import com.dlrtn.websocket.chat.business.user.model.UserSessionConstants;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.repository.InMemorySessionRepository;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import com.dlrtn.websocket.chat.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SessionUserResolver implements HandlerMethodArgumentResolver {

    private final InMemorySessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SessionUser.class)
                && parameter.getParameterType() == User.class;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        return Optional.of(webRequest)
                .map(request -> (HttpServletRequest) request.getNativeRequest())
                .map(req -> CookieUtils.getCookie(req, UserSessionConstants.SESSION_ID_COOKIE_NAME))
                .filter(sessionRepository::exists)
                .map(sessionRepository::get)
                .orElseThrow(() ->
                        new CommonException("Failed to authenticate with session id", HttpStatus.UNAUTHORIZED));
    }

}
