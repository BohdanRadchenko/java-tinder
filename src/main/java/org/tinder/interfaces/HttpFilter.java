package org.tinder.interfaces;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.BiFunction;

public interface HttpFilter extends Filter {
    @Override
    default void init(FilterConfig filterConfig) {
    }

    private boolean isHttp(ServletRequest request, ServletResponse response) {
        return request instanceof HttpServletRequest
                && response instanceof HttpServletResponse;
    }

    void doHttpFilter(HttpServletRequest request,
                      HttpServletResponse response,
                      FilterChain chain
    ) throws IOException, ServletException;

    @Override
    default void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        if (isHttp(request, response)) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            doHttpFilter(req, res, chain);
        } else {
            chain.doFilter(request, response);
        }

    }

    static HttpFilter create(
            BiFunction<HttpServletRequest, HttpServletResponse, Boolean> accepted,
            BiConsumerEx<HttpServletRequest, HttpServletResponse> failed) {
        return (request, response, chain) -> {
            if (accepted.apply(request, response)) {
                chain.doFilter(request, response);
            } else {
                failed.accept(request, response);
            }
        };
    }

    @Override
    default void destroy() {
    }
}
