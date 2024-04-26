package codesquad.springcafe.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import static codesquad.springcafe.controller.LoginController.LOGIN_SESSION_NAME;

public class UserProfileAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       String userIdInPath = request.getRequestURI().split("/")[3];
       HttpSession session = request.getSession(false);
        if(!userIdInPath.equals(session.getAttribute(LOGIN_SESSION_NAME))){
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;
    }
}
