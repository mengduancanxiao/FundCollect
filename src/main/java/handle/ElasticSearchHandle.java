package handle;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import model.FundMain2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.Transport;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import util.ClientUtil;
import util.DateUtils;
import util.StringUtils;

@SuppressWarnings({ "unused", "resource" })
public class ElasticSearchHandle {

	/**
	 * 创建一个新的索引:
	 * curl -XPUT "http://172.16.3.234:9200/blog"
	 * 
	 * 添加一条文档
	 * curl -XPUT "http://172.16.3.234:9200/blog/article/1" -d '{"title":"age","content":"18"}'
	   curl -XPUT "http://172.16.3.234:9200/blog/article/2" -d '{"field":"age","value":"18"}'
	 */
	private static Logger log = LogManager.getLogger(ElasticSearchHandle.class);
	private static final int SIZE = 1500;
	private static String ip1 = "172.16.3.234";
	private static String ip2 = "127.0.0.1";
	private static String ip3 = "172.16.11.113";

	public static void main(String[] args) {
//		generateIndex(ip3);
//		searchByID(ip2);
		searchByCondition();
//		deleteIndex(ip2);
	}
	
	public static TransportClient getClinet(String ip){
		TransportClient client = null;
		//设置集群名称
		Settings settings = Settings.builder().put("cluster.name", "my-application").build();
		try {
			//创建client
//			client = new PreBuiltTransportClient(settings)
			client = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), 9300));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}

	/**
	 * 通过ID查找数据
	 * @param ip
	 */
	public static void searchByID(String ip){
		TransportClient client = getClinet(ip);
		//搜索数据
		GetResponse response = null;
		for (int i = 0; i < 15; i++) {
			response = client.prepareGet("fund", "fund_main", (i + "")).execute().actionGet();
			//输出结果
			System.out.println(response.getSourceAsString());
		}
		//关闭client
		client.close();
	}

	/**
	 * 通过条件查找数据
	 * @param ip
	 */
	public static void searchByCondition(){
		long start = System.currentTimeMillis();
		Map<String, List<FundMain2>> map = new HashMap<String, List<FundMain2>>();
		TransportClient client = ClientUtil.getClient();

		String fundcode[] = new String[]{"519066","000457","110022","020026","001542","001410"};
		for (String s : fundcode) {
			//搜索数据
			QueryBuilder matchQuery = QueryBuilders.matchQuery("fundcode", s);
			HighlightBuilder hiBuilder = new HighlightBuilder();
			hiBuilder.preTags("<h2>");
			hiBuilder.postTags("</h2>");
			hiBuilder.field("fundcode");
			SearchResponse response = client.prepareSearch("fund").setQuery(matchQuery).highlighter(hiBuilder)
					.execute().actionGet();
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
	}

	/**
	 * 创建索引，并插入信息
	 * @param ip
	 */
	public static void generateIndex(String ip) {
		System.out.println("开始插入数据：" + DateUtils.date2string2(new Date()));
		long start = System.currentTimeMillis();
		//创建client
		TransportClient client = getClinet(ip);
		for (int i = 0; i < 5000; i++) {
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("user", "tom_" + i);
			json.put("age", i);
			json.put("inserttime", DateUtils.date2string(new Date()));
			json.put("message", "Hello World! Hello " + "Jack_" + i + ".");
			IndexResponse response = client.prepareIndex("bhm", "user", (i + "")).setSource(json).execute().actionGet();
			System.out.println("插入完成时间：" + DateUtils.date2string2(new Date()) + " json = " + json);
		}
		long end = System.currentTimeMillis();
		System.out.println("耗时：" + (end - start));
		//关闭client
		client.close();
	}

	/**
	 * 分页遍历数据
	 */
	public static void scrollQuery() {
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
				.addSort("id", SortOrder.ASC)
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
	 * 删除索引数据对应ID数据
	 * @param ip
	 */
	public static void deleteIndex(String ip){
		//创建client
		TransportClient client = getClinet(ip);
		DeleteResponse response = client.prepareDelete("blog", "article", "3").execute().actionGet();  
		System.out.println(response.getResult().toString());
	}
	

}
