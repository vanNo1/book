package com.van.book3.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.dao.ShelfMapper;
import com.van.book3.entity.Book;
import com.van.book3.entity.Shelf;
import com.van.book3.entity.Sign;
import com.van.book3.entity.User;
import com.van.book3.service.BookService;
import com.van.book3.service.ShelfService;
import com.van.book3.vo.ShelfVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/16 - 13:05
 */
@Service
public class ShelfServiceImpl implements ShelfService {
    @Resource
    private ShelfMapper shelfMapper;
    @Resource
    private BookServiceImpl bookService;

    public ShelfVO assembleShelfVO(Book book, Shelf shelf, String openId) {
        ShelfVO shelfVO = new ShelfVO();
        shelfVO.setAuthor(book.getAuthor());
        shelfVO.setBookId(book.getId());
        shelfVO.setCategory(book.getCategory());
        shelfVO.setCategoryText(book.getCategoryText());
        shelfVO.setCover(book.getCover());
        shelfVO.setDate(book.getCreateTime());
        shelfVO.setFileName(book.getFileName());
        shelfVO.setId(shelf.getId());
        shelfVO.setLanguage(book.getLanguage());
        shelfVO.setOpenId(openId);
        shelfVO.setPublisher(book.getPublisher());
        shelfVO.setRootFile(book.getRootFile());
        shelfVO.setTitle(book.getTitle());
        return shelfVO;
    }

    public ServerResponse<List<ShelfVO>> get(String openId) {

        // get all shelfs of a user
        if (openId != null) {
            Map map = new HashMap();
            map.put("open_id", openId);
            List<Shelf> shelfList = shelfMapper.selectByMap(map);
            //if this user's shelf is empty
            if (shelfList.size() == 0) {
                return ServerResponse.error("书架为空");
            }
            //if this user's shelf is not empty
            List<ShelfVO> shelfVOList = new ArrayList<>();
            //assemble vo
            for (Shelf shelfItem : shelfList) {
                Book book = bookService.selectBookByFileName(shelfItem.getFileName());
                ShelfVO shelfVO = assembleShelfVO(book, shelfItem, openId);
                shelfVOList.add(shelfVO);
            }
            return ServerResponse.success("书架获取成功", shelfVOList);
        } else {

            return ServerResponse.error("用户未登录获取不到书架");
        }


    }

    public ServerResponse save(String fileName, String openId) {
        if (fileName == null || openId == null) {
            return ServerResponse.error("参数错误");
        }
        Map map = new HashMap();
        map.put("file_name", fileName);
        map.put("open_id", openId);
        List<Shelf> shelfList = shelfMapper.selectByMap(map);
        if (shelfList.size() > 0) {
            return ServerResponse.error("已经存在");
        }
        //insert to shelf
        Shelf shelf = new Shelf();
        shelf.setFileName(fileName);
        shelf.setOpenId(openId);
        shelfMapper.insert(shelf);
        return ServerResponse.success("加入书架成功", null);
    }

    public ServerResponse remove(String fileName, String openId) {
        if (fileName == null || openId == null) {
            return ServerResponse.error("参数错误");
        }
        Map map = new HashMap();
        map.put("file_name", fileName);
        map.put("open_id", openId);
        int count = shelfMapper.deleteByMap(map);
        if (count == 0) {
            return ServerResponse.error("删除失败");
        } else {
            return ServerResponse.success("删除成功", null);

        }
    }

    public Integer findPeopleNum(String fileName) {
        Map map = new HashMap();
        map.put("file_name", fileName);
        List<Shelf> shelfList = shelfMapper.selectByMap(map);
        if (shelfList.size() > 0) {
            return shelfList.size();
        } else {
            return null;
        }
    }

    public List<Shelf> getAllShelf(String fileName) {
        Page page = new Page(1, 10, false);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("file_name", fileName);
        IPage<Shelf> iPage = shelfMapper.selectPage(page, queryWrapper);
        List<Shelf> shelfList = iPage.getRecords();
        if (shelfList.size() == 0) {
            return null;
        } else {
            return shelfList;
        }
    }

    public int getShelfNumber(String openId) {
        Map map = new HashMap();
        map.put("open_id", openId);
        List<Shelf> shelfList = shelfMapper.selectByMap(map);
        return shelfList.size();
    }
}
