package com.staxter.task2.resolver;


import com.staxter.task2.exception.InvalidTokenException;
import com.staxter.task2.repository.User;
import com.staxter.task2.repository.UserRepository;
import com.staxter.task2.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
@Slf4j
@RequiredArgsConstructor
public class UserResolver implements HandlerMethodArgumentResolver {

    private final SecurityService securityService;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(User.class);
    }

    @Override
    public User resolveArgument(MethodParameter methodParameter,
                                ModelAndViewContainer modelAndViewContainer,
                                NativeWebRequest nativeWebRequest,
                                WebDataBinderFactory webDataBinderFactory) {
        String accessToken = getAccessToken(nativeWebRequest);
        String userName = securityService.parseToken(accessToken);
        return userRepository.findUserByUsername(userName).orElseThrow(InvalidTokenException::new);
    }

    private String getAccessToken(NativeWebRequest nativeWebRequest) {
        String accessToken = nativeWebRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasLength(accessToken)) {
            throw new InvalidTokenException();
        }
        return accessToken.replaceFirst("Bearer ", "");
    }
}
