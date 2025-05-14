package com.onedream.onenet.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class HeaderInterceptor implements Interceptor {
    private final HeaderInterceptor.HeaderProvider headerProvider;

    public HeaderInterceptor(HeaderInterceptor.HeaderProvider headerProvider) {
        this.headerProvider = headerProvider;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        Map<String, String> headers = headerProvider.getHeaders();
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return chain.proceed(builder.build());
    }

    public static interface HeaderProvider {
        Map<String, String> getHeaders();
    }
}