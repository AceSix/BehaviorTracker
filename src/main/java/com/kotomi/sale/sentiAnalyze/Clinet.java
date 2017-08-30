package com.kotomi.sale.sentiAnalyze;

import com.kotomi.sale.model.ShopComment;
import com.kotomi.sale.service.ShopCommentService;
import com.kotomi.sale.util.CastUtil;
import com.kotomi.sale.util.CsvUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/5/26
 * @Modified By:
 */
public class Clinet {
    public static  void main(String[] args) {

        ShopCommentService shopCommentService=new ShopCommentService();
        SentenceSentiAnalysis sentenceSentiAnalysis = new SentenceSentiAnalysis();
        List<ShopComment> comments=shopCommentService.getShopCommentList();
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(ShopComment comment:comments) {
            String str=comment.getContent();
            List<String>  wordList =sentenceSentiAnalysis.cutSenToWord(str);
            for(String word :wordList){
                if(!map.containsKey(word)){
                    map.put(word,1);
                }
                else{
                    Integer count=map.get(word);
                    map.put(word,++count);
                }
            }
        }


        /*output the word frequence*/
        String filePath="";
        String fileName="wordFreq.csv";
        String[] colNames={"word","nature","fre"};
        File wordFreq=CsvUtil.createFileAndColName(filePath,fileName,colNames);
        CsvUtil.appendAllData(wordFreq, CastUtil.castListByMap(map));



    }

}
