package com.van.book3.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Van
 * @date 2020/4/3 - 22:36
 */
@Data
public class Banner {
    @TableId
    private int id;
    private String img;
}
