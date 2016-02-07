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

import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manager.bean.Response;
import com.manager.constant.ParameterConstant;
import com.manager.util.TokenUtil;

@Component
public class AuthenticationTokenFilter implements Filter {

	private Logger logger = Logger.getLogger(getClass());
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
		} else {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(HttpStatus.UNAUTHORIZED_401);
			httpResponse.setContentType("application/json");
			logger.warn("Unauthorized ip from:" + httpRequest.getRemoteAddr());
			String responseJson = 
					new ObjectMapper().writeValueAsString(new Response("Access Denied."));
			httpResponse.getWriter().write(responseJson);
		}
		
	}

	@Override
	public void destroy() {
		// do nothing
	}

}
