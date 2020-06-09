package security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Gère les autorisations CORS
 *
 */
public class CORSFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;

		((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods",
				"POST, PUT, GET, DELETE, OPTIONS");
		((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers",
				"x-requested-with, Content-Type");
		((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Credentials", "true");

		HttpServletResponse resp = (HttpServletResponse) servletResponse;

		if (request.getMethod().equals("OPTIONS")) {
			resp.setStatus(HttpServletResponse.SC_ACCEPTED);
			return;
		}

		chain.doFilter(request, servletResponse);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

}