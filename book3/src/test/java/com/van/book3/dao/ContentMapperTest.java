package com.van.book3.dao;

import com.van.book3.entity.Contents;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Van
 * @date 2020/3/20 - 11:13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ContentMapperTest {
    @Resource
    private ContentMapper contentMapper;
    @Test
    public void test(){
        Map map=new HashMap();
        System.out.println(contentMapper.selectByMap(map));
    }
    @Test
    public void revise(){
        String domain="http://store.yangxiansheng.top";
        Map map=new HashMap();
        List<Contents> contentList=contentMapper.selectByMap(map);
        for (Contents contents : contentList) {
           String [] test=contents.getText().split("/");
            ArrayList text2=new ArrayList();
            for (int i=test.length-1;i>test.length-5;i--){
                text2.add(test[i]);
            }
            Collections.reverse(text2);//epub2,2014_Book_Self-ReportedPopulationHealthA,OEBPS,ACoverHTML.html
            StringBuilder newtext=new StringBuilder(domain);
            for (int j=0;j<text2.size();j++){
                newtext.append("/").append(text2.get(j));
            }
           contents.setText(newtext.toString());
            contentMapper.updateById(contents);
        }

    }

}