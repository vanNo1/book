package com.van.book3.serviceimpl;

import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.dao.BannerMapper;
import com.van.book3.dao.BookMapper;
import com.van.book3.entity.Banner;
import com.van.book3.entity.Book;
import com.van.book3.service.BannerService;
import com.van.book3.utils.RandomUtil;
import com.van.book3.vo.BannerVO2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Van
 * @date 2020/4/3 - 22:37
 */
@Service
public class BannerServiceImpl implements BannerService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private BannerMapper bannerMapper;
    public BannerVO2 assembleBannserVO2(Book book, Banner banner){
        BannerVO2 bannerVO2=new BannerVO2();
        bannerVO2.setId(banner.getId());
        bannerVO2.setImg(banner.getImg());
        bannerVO2.setFileName(book.getFileName());
        bannerVO2.setCover(book.getCover());
        bannerVO2.setTitle(book.getTitle());
        bannerVO2.setAuthor(book.getAuthor());
        bannerVO2.setPublisher(book.getPublisher());
        bannerVO2.setCategory(book.getCategory());
        bannerVO2.setCategoryText(book.getCategoryText());
        bannerVO2.setLanguage(book.getLanguage());
        bannerVO2.setRootFile(book.getRootFile());
        bannerVO2.setOriginalName(book.getOriginalName());
        bannerVO2.setFilePath(book.getFilePath());
        bannerVO2.setUnzipPath(book.getUnzipPath());
        bannerVO2.setCoverPath(book.getCoverPath());
        return bannerVO2;
    }
    public ServerResponse<List<BannerVO2>> generateBanner(){
        //select all the banners
        List<Banner>banners=bannerMapper.selectByMap(null);
        int count=banners.size();
        Set<Integer> books = RandomUtil.getRandomSet(Const.MAXBOOKS, 1, count);
        List<Book>bookList=new ArrayList<>();
        //select  books which quantity is same as banners's
        for (Integer bookId : books) {
            Book book = bookMapper.selectById(bookId);
            bookList.add(book);
        }
        //assemble them to BannerVO2
        List<BannerVO2> bannerVO2List=new ArrayList<>();
        for (int i=0;i<count;i++){
            bannerVO2List.add(assembleBannserVO2(bookList.get(i),banners.get(i)));
        }
        return ServerResponse.success("获取成功",bannerVO2List);
    }
}
