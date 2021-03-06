package uet.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import uet.model.Role;
import uet.model.User;
import uet.repository.UserRepository;
import uet.stereotype.NoAuthentication;
import uet.stereotype.RequiredRoles;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lent on 4/20/2016.
 */
@Component
public class TokenAuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserRepository userRepository;

    public TokenAuthenticationInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("auth-token");
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethodAnnotation(NoAuthentication.class) != null) {
                return true;
            } else if (token == null) {
                throw new Exception("UNAUTHORIZED");
            }
            User user = userRepository.findByToken(token);
            String role = user.getRole();
            RequiredRoles requiredRoles = handlerMethod.getMethodAnnotation(RequiredRoles.class);
            if (requiredRoles != null) {
                for (Role requiredRole : requiredRoles.value()) {
                    if (String.valueOf(requiredRole).equals(role)) {
                        return true;
                    }
                }
                throw new NullPointerException("Not matching");
            }
        }
        return true;
    }
}
