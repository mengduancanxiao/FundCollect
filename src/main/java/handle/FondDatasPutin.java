package handle;

import java.util.Date;
import java.util.List;

import model.FundMain2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import util.ClientUtil;
import util.DateUtils;
import util.FundCollect;

@SuppressWarnings({ "unused" })
public class FondDatasPutin {

	private static Logger log = LogManager.getLogger(FondDatasPutin.class);
	private static final int SIZE = 1500;

	public static void main(String[] args) {
		insert();
//		search2();
//		scrollTest();
//		deleteIndex(ip2);
	}

	/**
	 * 通过ID查找数据
	 * @param ip
	 */
	public static void search(){
		long start = System.currentTimeMillis();
		TransportClient client = ClientUtil.getClient();
		//搜索数据
		QueryBuilder matchQuery = QueryBuilders.matchQuery("cicode", "2017");
		HighlightBuilder hiBuilder = new HighlightBuilder();
        hiBuilder.preTags("<h2>");
        hiBuilder.postTags("</h2>");
        hiBuilder.field("cicode");
        SearchResponse response = client.prepareSearch("konview").setQuery(matchQuery).highlighter(hiBuilder)
                .execute().actionGet();
        //获取查询结果集
        SearchHits searchHits = response.getHits();
        System.out.println("共搜到:" + searchHits.getTotalHits() + "条结果!");
        //遍历结果
        for(SearchHit hit : searchHits){
            System.out.println("String方式打印文档搜索内容:");
            System.out.println(hit.getSourceAsString());
            
            System.out.println("Map方式打印高亮内容");
            System.out.println(hit.getHighlightFields());

            System.out.println("遍历高亮集合，打印高亮片段:");
            Text[] text = hit.getHighlightFields().get("cicode").getFragments();
            for (Text str : text) {
                System.out.println(str.string());
            }
            System.out.println("----------------------------------------------------------------------------------------------------------");
        }
		//关闭client
		//client.close();
		long end = System.currentTimeMillis();
		System.out.println("查询结束，耗时：" + (end - start));
	}
	
	public static void search2(){
		long start = System.currentTimeMillis();
		TransportClient client = ClientUtil.getClient();
		//搜索数据
		QueryBuilder matchQuery = QueryBuilders.matchAllQuery();
		HighlightBuilder hiBuilder = new HighlightBuilder();
		hiBuilder.preTags("<h2>");
		hiBuilder.postTags("</h2>");
		hiBuilder.field("message");
		SearchResponse response = client.prepareSearch("bhm").setQuery(matchQuery).highlighter(hiBuilder)
				.setFrom(0).setSize(10).setExplain(true).execute().actionGet();
		//获取查询结果集
		SearchHits searchHits = response.getHits();
		System.out.println("共搜到:" + searchHits.getTotalHits() + "条结果!");
		//遍历结果
		for(SearchHit hit : searchHits){
			System.out.println("String方式打印文档搜索内容:");
			System.out.println(hit.getSourceAsString());
			
			System.out.println("Map方式打印高亮内容");
			System.out.println(hit.getHighlightFields());
			
			/*System.out.println("遍历高亮集合，打印高亮片段:");
			Text[] text = hit.getHighlightFields().get("message").getFragments();
			for (Text str : text) {
				System.out.println(str.string());
			}*/
			System.out.println("----------------------------------------------------------------------------------------------------------");
		}
		//关闭client
		//client.close();
		long end = System.currentTimeMillis();
		System.out.println("查询结束，耗时：" + (end - start));
	}

	/**
	 * 分页遍历数据
	 */
	public static void scrollTest() {
		long start = System.currentTimeMillis();
		int times = 2;
		int count = 0;
		TransportClient esClient = ClientUtil.getClient();
		SearchResponse searchResponse = esClient.prepareSearch("fund")
		//实际返回的数量为5*index的主分片格式
        .setSize(SIZE)
        //这个游标维持多长时间
        .setScroll(TimeValue.timeValueMinutes(8))
        //添加排序
        .addSort("yesterday", SortOrder.ASC)
        .execute().actionGet();
		//第一次查询，只返回数量和一个scrollId
		log.info("共搜到:{}{}", searchResponse.getHits().getTotalHits(), "条结果!");
		log.info("本次遍历:{}{}", searchResponse.getHits().hits().length, "条结果");
		//第一次运行没有结果
		log.info("第 1 次遍历数据------------------------------------------------------------");
		for (SearchHit hit : searchResponse.getHits()) {
			log.info(hit.getSourceAsString());
		}
		count+=SIZE;

		while(count < searchResponse.getHits().getTotalHits()){
			log.info("第 " + times + " 次遍历数据------------------------------------------------------------");
			//使用上次的scrollId继续访问  
			searchResponse = esClient.prepareSearchScroll(searchResponse.getScrollId())  
					.setScroll(TimeValue.timeValueMinutes(8))  
					.execute().actionGet();  
			log.info("本次遍历:" + searchResponse.getHits().hits().length + "条结果");
			for (SearchHit hit : searchResponse.getHits()) {  
				log.info(hit.getSourceAsString());
			}
			count +=SIZE;
			times++;
		}
		long end = System.currentTimeMillis();
		log.info("遍历{}条结果耗时：{}",searchResponse.getHits().hits().length, (end - start));
	}
	
	
	/**
	 * 批量插入信息
	 * @param ip
	 */
	public static void insert() {
		log.info("开始插入数据：" + DateUtils.date2string2(new Date()));
		long start = System.currentTimeMillis();
		//创建client
		TransportClient client = ClientUtil.getClient();
		
		String json = "";
		List<FundMain2> data = FundCollect.collect();
//		List<FundMain2> data = FundCollect.aaa();

		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (FundMain2 fundmain : data) {
			try {
				json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(fundmain);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			bulkRequest.add(client.prepareIndex("fund", "fund_main").setSource(json));
		}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			log.error("插入失败！");
		}
		long end = System.currentTimeMillis();
		log.info("插入数据耗时：" + (end - start));

		//关闭client
		client.close();
	}
	
	/**
	 * 删除索引数据对应ID数据
	 * @param ip
	 */
	public static void deleteIndex(){
		//创建client
		TransportClient client = ClientUtil.getClient();
		DeleteResponse response = client.prepareDelete("blog", "article", "3").execute().actionGet();  
		log.info(response.getResult().toString());

		//关闭client
		client.close();
	}

}
