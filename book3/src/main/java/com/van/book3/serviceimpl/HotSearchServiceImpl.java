package com.van.book3.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.van.book3.dao.HotSearchMapper;
import com.van.book3.entity.HotSearch;
import com.van.book3.service.HotSearchService;
import com.van.book3.vo.HotSearchVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/18 - 14:53
 */
@Service
public class HotSearchServiceImpl implements HotSearchService {
    @Resource
    private HotSearchMapper hotSearchMapper;

    public int insert(String keyword, String openId) {
        HotSearch hotSearch = new HotSearch();
        hotSearch.setKeyword(keyword);
        hotSearch.setOpenId(openId);
        return hotSearchMapper.insert(hotSearch);
    }

    public HotSearchVO getHotSearchVO() {
        QueryWrapper<HotSearch> queryWrapper = new QueryWrapper();
        queryWrapper.select("distinct keyword,count(keyword)as count").groupBy("keyword").orderByDesc("count");
        List<HotSearch> hotSearchList = hotSearchMapper.selectList(queryWrapper);
        //get number one keyword
        String keyword = hotSearchList.get(0).getKeyword();
        //.....................
        Map map = new HashMap();
        map.put("keyword", keyword);
        List<HotSearch> hotSearchList2 = hotSearchMapper.selectByMap(map);
        //get number
        int number = hotSearchList2.size();
        //get vo
        HotSearchVO hotSearchVO = new HotSearchVO();
        hotSearchVO.setCount(number);
        hotSearchVO.setKeyWord(keyword);
        return hotSearchVO;
    }
}
