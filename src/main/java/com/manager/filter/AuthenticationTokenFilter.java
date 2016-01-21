package com.manager.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.manager.constant.ParameterConstant;
import com.manager.util.TokenUtil;

@Component
public class AuthenticationTokenFilter implements Filter {

	private static final String ignoreUrl = "/auth/login";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
	    HttpSession session = httpRequest.getSession(false);
	    
	    String uri = httpRequest.getRequestURI();
	    if(ignoreUrl.equals(uri)) {
	    	chain.doFilter(request, response);
	    }
		String tokenInClient = httpRequest.getHeader(ParameterConstant.TOKEN);
		if (tokenInClient != null) {
            if (tokenInClient != null && session.getAttribute(ParameterConstant.TOKEN) != null) {
            	String tokenInSession = (String) session.getAttribute(ParameterConstant.TOKEN);
            	if(TokenUtil.verify(tokenInSession, tokenInClient)) {
            		chain.doFilter(request, response);
            	}
            }
		} 
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.sendRedirect("/login.html");
		
	}

	@Override
	public void destroy() {
		// do nothing
	}

}
