package com.van.book3.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Van
 * @date 2020/4/4 - 19:29
 */
@Data
public class ReviewVO {
    private String avatarUrl;
    private String fileName;
    private Integer rank;
    private String title;
    private String summary;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
