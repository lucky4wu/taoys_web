package cn.rm.network.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.rm.network.entity.PingData;
import cn.rm.network.service.PingDataService;
import cn.rm.network.service.helper.StringUtil;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/pingData")
public class PingDataAction {

	private static Logger logger = Logger.getLogger(PingDataAction.class);
	
	@Autowired
	private PingDataService pingDataService;
	
	@RequestMapping("/getData")
	@ResponseBody
	public String getData(String uname, Integer pageNum, Integer pageSize,
			String startDate, String endDate){
		PingData pingData = new PingData();
		pingData.setUname(uname);
		if(pageNum == null || StringUtil.isNullOrEmpty(pageNum.toString())){
			pageNum = 1;
		}
		if(pageSize == null || StringUtil.isNullOrEmpty(pageSize.toString())){
			pageSize = 10;
		}
		List<PingData> pingDataList = pingDataService.queryPingData(pingData, pageNum, pageSize, startDate, endDate);
		String result = JSON.toJSONString(pingDataList);
		return result;
	}
	
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("pingData/list");
		return mav;
	}
}
