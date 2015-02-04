package cn.rm.network.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.rm.network.dao.PingDataDao;
import cn.rm.network.entity.PingData;
import cn.rm.network.service.helper.StringUtil;

public class PingDataService {

	@Autowired
	private PingDataDao pingDataDao;
	
	public List<PingData> queryPingData(PingData pingData, Integer pageNum, Integer pageSize,
			String startDate, String endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		Integer start = (pageNum-1)*pageSize;
		Integer end = pageNum*pageSize;
		params.put("start", start);
		params.put("end", end);
		if(!StringUtil.isNullOrEmpty(startDate)){
			params.put("startDate", startDate);
		}
		if(!StringUtil.isNullOrEmpty(endDate)){
			params.put("endDate", endDate);
		}
		if(!StringUtil.isNullOrEmpty(pingData.getUname())){
			params.put("uname", pingData.getUname());
		}
		List<PingData> list = pingDataDao.findListByParams(params);
		
		return list;
	}

	
}
