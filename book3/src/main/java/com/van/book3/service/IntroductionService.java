package com.van.book3.service;

import com.van.book3.common.ServerResponse;

/**
 * @author Van
 * @date 2020/4/4 - 18:23
 */
public interface IntroductionService {
    ServerResponse getIntroduction(String fileName);
}
