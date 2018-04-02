package com.spring.controller;

import javax.servlet.http.HttpServletRequest;

import com.spring.dao.QueryELDatas;
import model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class IndexController {

	@RequestMapping(params = "list", method = RequestMethod.POST)
	@ResponseBody
	public String list(HttpServletRequest request){
		System.out.println("------------------------------------");
		return "test";
	}
	
	@RequestMapping(params = "listget", method = RequestMethod.GET)
	@ResponseBody
	public String listget(HttpServletRequest request) { 
		System.out.println("------------------------------------");
		return "test";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		//请求的URI：
		//http://localhost:8080/FundCollect/index/delete/index.do
		System.out.println("id ==== " + id);
		return id;
	}
	
	@RequestMapping(value = "/list2")
	public ModelAndView list2(HttpServletRequest request) {
		return new ModelAndView("index");
	}
	
	@RequestMapping("/index")
	public String toindex(HttpServletRequest request) {
		return "index";
	}
	
	@RequestMapping(value = "/index2")
	public String index2(HttpServletRequest request) {
		return "index";
	}
	
	@RequestMapping(value = "/queryAll", method = RequestMethod.POST, produces="text/html;charset=UTF-8;", headers="Content-Type=text/plain")
	@ResponseBody
	public String queryAll() {
		QueryELDatas query = new QueryELDatas();
		Map<String, List<FundMain2>> map = query.searchByCondition();
		Option option = new Option();
		List<String> xAxis = new ArrayList<String>();
		List<String> legendName = new ArrayList<String>();
		List<SeriesSubtype> series = new ArrayList<SeriesSubtype>();

		option.title("净值走势","纯属虚构");
		option.tooltip().trigger("axis");

		Feature feature = new Feature();
		feature.magicType().show(true).type(null);
		feature.saveAsImage().show(true);
		option.toolbox().show(true).feature(feature);

		for (Map.Entry<String,List<FundMain2>> entry : map.entrySet()) {
			List<FundMain2> list = entry.getValue();

			legendName.add(list.get(0).getName());
			SeriesSubtype seriesSubtype = new SeriesSubtype().name(list.get(0).getName()).type("line").smooth(false);

			List<String> dwjzdata = new ArrayList<String>();
			for (FundMain2 fundMain2 : list) {
				if(xAxis.size() < list.size()){
					xAxis.add(fundMain2.getJzrq());
			}
				dwjzdata.add(fundMain2.getDwjz());
			}
			seriesSubtype.data(dwjzdata.toArray());
			series.add(seriesSubtype);
		}
		option.legend().data(legendName.toArray());
		option.xAxis().type("category").boundaryGap(false).data(xAxis.toArray());
		option.yAxis().min("1.3").type("value");
		option.series(series);

		String json = StringUtils.option2json(option);
		System.out.println(json);
		return json;
	}
	
	
}