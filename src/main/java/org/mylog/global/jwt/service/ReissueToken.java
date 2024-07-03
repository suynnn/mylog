package org.mylog.global.jwt.service;

import jakarta.servlet.http.HttpServletResponse;

public interface ReissueToken {

    public String reissueAccessToken(HttpServletResponse response, String refreshToken);
}
