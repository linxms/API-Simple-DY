package org.example.apisimple_dy.entity;

import lombok.Data;

@Data
public class Browse {
    private Integer videoID;

    private Integer userID;

    private boolean isBrowsed;

    private String content;
}