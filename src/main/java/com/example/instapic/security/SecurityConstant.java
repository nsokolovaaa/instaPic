package com.example.instapic.security;

public class SecurityConstant {
    public static final String SIGN_UP_URLS = "/api/auth/**";
    public static final String SECRET = "SecretKeyGenJWT";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authentication";
    public static final String CONTENT_TYPE = "/application/json";
    public static final long EXPIRATION_PLAN = 80000_0000;



}
