package com.uuhnaut69.mall.payload.response;

import lombok.Data;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";

    public JwtResponse(String token) {
        this.token = token;
    }

}
