package com.van.book3.serviceimpl;

import com.van.book3.common.CodeMsg;
import com.van.book3.common.ServerResponse;
import com.van.book3.dao.ContentMapper;
import com.van.book3.entity.Contents;
import com.van.book3.exception.GlobalException;
import com.van.book3.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/18 - 10:15
 */
@Service
@Slf4j
public class ContentServiceImpl implements ContentService {
    @Resource
    private ContentMapper contentMapper;

    public ServerResponse content(String fileName) {
        Map map = new HashMap();
        map.put("file_name", fileName);
        List<Contents> contentsList = contentMapper.selectByMap(map);
        if (contentsList.size() == 0) {
            throw new GlobalException(CodeMsg.BOOK_NOT_EXIST);
        }
        return ServerResponse.success("查询成功", contentsList);
    }
}
