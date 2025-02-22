package com.example.demo.cors;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    // Константы для заголовков CORS
    private static final String ALLOWED_ORIGIN = "http://localhost:5173";
    private static final String ALLOWED_HEADERS = "origin, content-type, accept, authorization";
    private static final String ALLOWED_METHODS = "GET, POST, PUT, DELETE, OPTIONS, HEAD";
    private static final String EXPOSED_HEADERS = "Set-Cookie";
    private static final String MAX_AGE = "1209600"; // 14 дней в секундах

    @Override
    public void filter(final ContainerRequestContext requestContext,
                       final ContainerResponseContext responseContext) throws IOException {
        // Добавляем заголовки CORS
        responseContext.getHeaders().add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
        responseContext.getHeaders().add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", ALLOWED_METHODS);
        responseContext.getHeaders().add("Access-Control-Max-Age", MAX_AGE);
        responseContext.getHeaders().add("Access-Control-Expose-Headers", EXPOSED_HEADERS);

        // Обработка предварительных запросов (OPTIONS)
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            responseContext.setStatus(200); // Ответ на OPTIONS-запрос
        }
    }
}