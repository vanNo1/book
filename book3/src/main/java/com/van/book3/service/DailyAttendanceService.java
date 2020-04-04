package com.van.book3.service;

import com.van.book3.common.ServerResponse;

/**
 * @author Van
 * @date 2020/4/4 - 14:38
 */
public interface DailyAttendanceService {
    ServerResponse attendance(String openId);
}
