package com.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedisResponse {
    private String response;
    private String data;
    private String dataKey;
}
