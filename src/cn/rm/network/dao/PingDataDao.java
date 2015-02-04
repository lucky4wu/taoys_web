package cn.rm.network.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import cn.rm.network.entity.PingData;

public class PingDataDao extends BaseDaoImpl<PingData, Integer>{

	private SqlSessionTemplate sqlReadSession;
	
	public void setSqlReadSession(SqlSessionTemplate sqlReadSession) {
		this.sqlReadSession = sqlReadSession;
	}
	
	public PingData findById(Integer id){
		try {
			return (PingData) sqlReadSession.selectOne(
					PingData.class.getSimpleName() + ".findById", id);
		} catch (RuntimeException re) {
			logger.error("findById " + PingData.class.getName() + "failed :{}", re);
			return null;
		}
	}
	
	/**
	 * @param params
	 * @return
	 */
//	 @SuppressWarnings("unchecked")
	public List<PingData> findListByParams(Map<String,Object> params) {
		try {
			List<PingData> selectList = sqlReadSession.selectList(
					PingData.class.getSimpleName() + ".findListByParams", params);
			return selectList;
		} catch (RuntimeException re) {
			logger.error("findListByParams " + PingData.class.getName() + "failed :{}", re);
			return null;
		}
	}
	
	public Integer findTotalCounts(Map<String,Object> params) {
		Integer total = 0;
		try {
			total = (Integer) sqlReadSession.selectOne(PingData.class.getSimpleName() + ".getTotalCounts", params);
		} catch (RuntimeException re) {
			logger.error("getTotalCounts " + PingData.class.getName() + "failed :{}", re);
			return null;
		}
		return total;
	}
}
