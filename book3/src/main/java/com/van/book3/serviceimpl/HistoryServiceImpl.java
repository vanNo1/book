package com.van.book3.serviceimpl;

import com.van.book3.dao.HistoryMapper;
import com.van.book3.entity.History;
import com.van.book3.service.HistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Van
 * @date 2020/3/27 - 10:59
 */
@Service
public class HistoryServiceImpl implements HistoryService {
    @Resource
    private HistoryMapper historyMapper;

    //return true insert success, return false insert fail
    public boolean insert(History history) {
        int count = historyMapper.insert(history);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}
