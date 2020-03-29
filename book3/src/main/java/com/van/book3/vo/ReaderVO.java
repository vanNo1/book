package com.van.book3.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Van
 * @date 2020/3/17 - 16:11
 */
@Data
public class ReaderVO implements Serializable {
    private String avatarUrl;
    private String nickName;
    private LocalDateTime create_dt;

}
