package cn.rm.network.dao;


import java.util.List;
import java.util.Map;

import cn.rm.network.service.helper.PageResult;


public interface BaseDao<T,PK> {
	public T insert(T entity);
	public boolean update(T entity);
	public boolean delete(T entity);
	public boolean delete(Class<T> entityClass, PK id);
	public T findById(Class<T> entityClass, PK id);
	public T findByParams(Class<T> entityClass,Map params);
	public List<T> findAll(Class<T> entityClass);
	public PageResult queryPage(Class<T> entityClass,Map params, int currentPage, int pageSize);
//	public int callProc(final String procName);
//	public void callProc(final String procName, List<ProcParam> params);
	public Long getNewId(Class<T> entityClass);
}
