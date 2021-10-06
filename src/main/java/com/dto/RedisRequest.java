package com.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedisRequest {
    private String dataKey;
    private String data;
    private Integer expire;
}
