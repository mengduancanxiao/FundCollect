package com.spring.dao;

import com.contact.Contact;
import model.FundMain2;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import util.ClientUtil;
import util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryELDatas {
    /**
     * 通过条件查找数据
     * @param ip
     */
    public Map<String, List<FundMain2>> searchByCondition(){
        System.out.println("开始查询数据");
        long start = System.currentTimeMillis();
        Map<String, List<FundMain2>> map = new HashMap<String, List<FundMain2>>();
        TransportClient client = ClientUtil.getClient();

        String fundcode[] = Contact.fundcode;
        for (String s : fundcode) {
            //搜索数据
            QueryBuilder matchQuery = QueryBuilders.matchQuery("fundcode", s);
            HighlightBuilder hiBuilder = new HighlightBuilder();
            hiBuilder.preTags("<h2>");
            hiBuilder.postTags("</h2>");
            hiBuilder.field("fundcode");
            SearchResponse response = client.prepareSearch("fund").setQuery(matchQuery).addSort("jzrq", SortOrder.ASC)
                    .highlighter(hiBuilder).execute().actionGet();
            //获取查询结果集
            SearchHits searchHits = response.getHits();
            List<FundMain2> list = new ArrayList<FundMain2>();
            //遍历结果
            for(SearchHit hit : searchHits){
                FundMain2 fund = StringUtils.str2FundMain2(hit.getSourceAsString());
                list.add(fund);
            }

            map.put(s, list);
        }

        //关闭client
        client.close();
        long end = System.currentTimeMillis();
        System.out.println("查询结束，耗时：" + (end - start));

        return map;
    }
}
