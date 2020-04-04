package com.van.book3.serviceimpl;

import com.van.book3.common.ServerResponse;
import com.van.book3.dao.DailyAttendanceMapper;
import com.van.book3.entity.DailyAttendance;
import com.van.book3.service.DailyAttendanceService;
import com.van.book3.vo.AttendanceVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/4/4 - 14:39
 */
@Service
public class DailyAttendanceServiceImpl implements DailyAttendanceService {
    @Resource
    private DailyAttendanceMapper dailyAttendanceMapper;
    public ServerResponse attendance(String openId){
        Map map=new HashMap();
        map.put("open_id",openId);
        List<DailyAttendance>dailyAttendances=dailyAttendanceMapper.selectByMap(map);
        // if the user is his first day to attendance
        if(dailyAttendances.size()==0){
            DailyAttendance dailyAttendance=new DailyAttendance();
            dailyAttendance.setOpenId(openId);
            dailyAttendance.setSum(1);
            dailyAttendanceMapper.insert(dailyAttendance);
            AttendanceVO attendanceVO=new AttendanceVO();
            attendanceVO.setDay(dailyAttendance.getSum());
            return ServerResponse.success("打卡成功",attendanceVO);
        }
        //if the user is not his first time to attendance
        DailyAttendance dailyAttendance=dailyAttendances.get(0);
        //calculate the day between last time to now
        Duration duration= Duration.between(dailyAttendance.getUpdateTime(), LocalDateTime.now());
        long days = duration.toDays();
        if (days==0){
            AttendanceVO attendanceVO=new AttendanceVO();
            attendanceVO.setDay(dailyAttendance.getSum());
            return ServerResponse.success("今日已打过卡咯",attendanceVO);
        }
        //if the user is not attendance for a day then clear his sum
        if (days!=1){
            dailyAttendance.setSum(1);
            AttendanceVO attendanceVO=new AttendanceVO();
            attendanceVO.setDay(dailyAttendance.getSum());
            dailyAttendanceMapper.updateById(dailyAttendance);
            return ServerResponse.success("你没有连续打卡喔",attendanceVO);
        }
        //if user is continue attendance
        dailyAttendance.setSum(dailyAttendance.getSum()+1);
        dailyAttendanceMapper.updateById(dailyAttendance);
        AttendanceVO attendanceVO=new AttendanceVO();
        attendanceVO.setDay(dailyAttendance.getSum());
        return ServerResponse.success("坚持打卡成功，good！",attendanceVO);
    }
}
