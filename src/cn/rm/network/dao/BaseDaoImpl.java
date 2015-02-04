package cn.rm.network.dao;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rm.network.service.helper.PageResult;


public class BaseDaoImpl<T, PK extends Serializable> extends
		SqlSessionDaoSupport implements BaseDao<T, PK> {

	public static Logger logger = Logger.getLogger(BaseDao.class);

	@Autowired(required = true)
	@Resource(name = "sqlSessionFactory")
	public void setSuperSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	// 保存
	public T insert(T entity) { 
		try {
			getSqlSession().insert(this.getClassSimpleName(entity) + ".save",entity);
			return entity;
		} catch (RuntimeException re) {
			logger.error("insert " + entity.getClass().getName() + " failed :{}",re);
			return null;
		}
	}

	// 更新
	public boolean update(T entity) {
		boolean t = true;
		try {
			this.getSqlSession().update(this.getClassSimpleName(entity) + ".update", entity);
		} catch (RuntimeException re) {
			t = false;
			logger.error("update " + entity.getClass().getName() + " failed :{}", re);
		}
		return t;
	}

	// 删除
	public boolean delete(T entity) {
		boolean t = true;
		try {
			this.getSqlSession().delete(
					this.getClassSimpleName(entity) + ".delete", entity);
		} catch (RuntimeException re) {
			t = false;
			logger.error("delete " + entity.getClass().getName()
					+ " failed :{}", re);
		}
		return t;
	}
	
	// 删除
	public boolean delete(String className,Map<String,Object> paraMap) {
		boolean t = true;
		try {
			this.getSqlSession().delete(className + ".delete", paraMap);
		} catch (RuntimeException re) {
			t = false;
			logger.error("delete " + className+ " failed :{}", re);
		}
		return t;
	}

	// 根据id删除某个对象
	public boolean delete(Class<T> entityClass, PK id) {
		boolean t = true;
		try {
			this.getSqlSession().delete(entityClass.getSimpleName() +".delete", id);
		} catch (RuntimeException re) {
			t = false;
			logger.error("delete " + entityClass.getName() + "failed :{}", re);
		}
		return t;
	}

	// 根据id加载某个对象
	@SuppressWarnings("unchecked")
	public T findById(Class<T> entityClass, PK id) {
		try {
			return (T) this.getSqlSession().selectOne(entityClass.getSimpleName() + ".findById", id);
		} catch (RuntimeException re) {
			logger.error("findById " + entityClass.getName() + "failed :{}", re);
			return null;
		}
	}

	// 查找所有的对象
	@SuppressWarnings("unchecked")
	public List<T> findAll(Class<T> entityClass) {
		try {
			return this.getSqlSession().selectList(entityClass.getSimpleName() + ".findAll");
		} catch (RuntimeException re) {
			logger.error("findAll " + entityClass.getName() + "failed :{}", re);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public T findByParams(Class<T> entityClass, Map params) {
		try {
			return (T) this.getSqlSession().selectOne(entityClass.getSimpleName() + ".findByParams", params);
		} catch (RuntimeException re) {
			logger.error("findAll " + entityClass.getName() + "failed :{}", re);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findListByParams(Class<T> entityClass, Map params) {
		try {
			return getSqlSession().selectList(entityClass.getSimpleName() + ".findByParams", params);
		} catch (RuntimeException re) {
			logger.error("findAll " + entityClass.getName() + "failed :{}", re);
			return null;
		}
	}

	// 根据查询参数，当前页数，每页显示的数目得到分页列表
	public PageResult queryPage(Class<T> entityClass, Map param,
			int currentPage, int pageSize) {
		int start = (currentPage - 1) * pageSize;
		try {
			Integer total = (Integer) getSqlSession().selectOne(entityClass.getSimpleName() + ".getTotalCounts", param);
			Integer totalPage = (int) ((total + pageSize - 1) / pageSize);
			return new PageResult(currentPage, pageSize, total, totalPage,getSqlSession().selectList(entityClass.getSimpleName() + ".findByParams",param, new RowBounds(start, pageSize)));
		} catch (RuntimeException re) {
			logger.error("findList " + entityClass.getName() + "failed :{}", re);
			return null;
		}
	}

	// 重载 queryPage
	public PageResult queryPage(Class<T> entityClass, Map param,
			int currentPage, int pageSize, String sqlName4Total, String sqlName) {
		int start = (currentPage - 1) * pageSize;
		try {
			Integer total = (Integer) getSqlSession().selectOne(
					entityClass.getSimpleName() + "." + sqlName4Total, param);
			Integer totalPage = (int) ((total + pageSize - 1) / pageSize);
			return new PageResult(currentPage, pageSize, total, totalPage,
					getSqlSession().selectList(
							entityClass.getSimpleName() + "." + sqlName, param,
							new RowBounds(start, pageSize)));
		} catch (RuntimeException re) {
			logger.error("findList " + entityClass.getName() + "failed :{}", re);
			return null;
		}
	}

//	/**
//	 * 不带参数的存储过程
//	 * 
//	 * @param procName
//	 *            过程名称
//	 * @return
//	 */
//	public int callProc(final String procName) {
//		final String sql = "{call " + procName + "()}";
//		Integer result = 0;
//		try {
//			Connection con = getSqlSession().getConnection();
//			CallableStatement cstmt = con.prepareCall(sql);
//			result = (Integer) cstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return result.intValue();
//	}

//	/**
//	 * 带参数的存储过程
//	 * 
//	 * @param procName
//	 *            过程名称
//	 * @param params
//	 *            过程参数
//	 */
//	public void callProc(final String procName, List<ProcParam> params) {
//		Map result = new HashMap();
//		;
//
//		try {
//			String tmp = "";
//			if (params != null && params.size() > 0) {
//				for (int i = 0; i < params.size(); i++) {
//					tmp += "?,";
//				}
//				tmp = tmp.substring(0, tmp.length() - 1);
//			}
//
//			final String sql = "{call " + procName + "(" + tmp + ")}";
//
//			final List listTmp = params;
//
//			Connection con = getSqlSession().getConnection();
//			CallableStatement cstmt = con.prepareCall(sql);
//
//			if (listTmp != null && listTmp.size() > 0) {
//				for (int i = 0; i < listTmp.size(); i++) {
//					ProcParam pp = (ProcParam) listTmp.get(i);
//					switch (pp.getDirect()) {
//					case 1:
//						cstmt.setObject(i + 1, pp.getValue());
//						break;
//					case 2:
//						cstmt.registerOutParameter(i + 1, pp.getSqlType());
//					default:
//						break;
//					}
//
//				}
//			}
//
//			cstmt.executeUpdate();
//
//			if (listTmp != null && listTmp.size() > 0) {
//				for (int i = 0; i < listTmp.size(); i++) {
//					ProcParam pp = (ProcParam) listTmp.get(i);
//					switch (pp.getDirect()) {
//					case 1:
//						break;
//					case 2:
//						if (pp.getSqlType() == OracleTypes.CURSOR) {
//							result.put(pp.getKey(),
//									(ResultSet) cstmt.getObject(i + 1));
//						} else {
//							result.put(pp.getKey(), cstmt.getObject(i + 1));
//						}
//					default:
//						break;
//					}
//				}
//			}
//
//			if (result != null) {
//				for (int i = 0; i < params.size(); i++) {
//					ProcParam pp = (ProcParam) params.get(i);
//					switch (pp.getDirect()) {
//					case 1:
//						break;
//					case 2:
//						pp.setValue(result.get(pp.getKey()));
//					default:
//						break;
//					}
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	public Long getNewId(Class<T> entityClass) {
		Long total = null;
		try {
			total = (Long) getSqlSession().selectOne(
					entityClass.getSimpleName() + ".getNewID");
		} catch (RuntimeException re) {
			logger.error("findList " + entityClass.getName() + "failed :{}", re);
			return null;
		}
		return total;
	}

	/**
	 * 将持久类list转成map存储的list 若为数组，则为list中包含list，list里包含map，如果属性为set将转list
	 */
	public List mapList(List list) {
		List nlist = new ArrayList();
		for (Object o : list) {

			if (o.getClass().getName().trim().equals("[Ljava.lang.Object;")) {
				Object[] os = (Object[]) o;
				List tmpList = new ArrayList();
				for (int i = 0; i < os.length; i++) {
					Map map = new HashMap();
					map = object2Map(os[i]);
					tmpList.add(map);
				}
				nlist.add(tmpList);

			} else {
				Map map = new HashMap();
				map = object2Map(o);
				nlist.add(map);
			}

		}
		return nlist;
	}

	/**
	 * 对象存成map
	 * 
	 */
	public Map object2Map(Object o) {
		Map map = new HashMap();
		Method[] methods = o.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = (Method) methods[i];
			if (method.getName().startsWith("get")) {
				String prop = getProperites(method.getName());
				Object obj = null;
				try {
					obj = method.invoke(o, null);
					if (obj instanceof Set) {
						List list = new ArrayList();
						Set set = (Set) obj;
						for (Object o2 : set) {
							list.add(o2);
						}
						map.put(prop, list);
					} else {
						map.put(prop, obj);
					}

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

			}

		}

		return map;
	}

	/**
	 * 取属性
	 */
	private String getProperites(String getMethod) {
		String s = "";
		s = getMethod.substring(3, 4).toLowerCase() + getMethod.substring(4);

		return s;
	}

	/**
	 * 取得某个实例的方法
	 */
	private Method getMethod(Object obj, String startWith) {
		Method method = null;
		Method[] methods = obj.getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().startsWith(startWith)) {
				method = m;
				break;
			}
		}
		return method;
	}

	/**
	 * 将持久类list转成PO，已取出数据的相当于lazy=false存储的list 若为数组
	 */
	public List poList(List list) {
		List nlist = new ArrayList();
		for (Object o : list) {

			if (o.getClass().getName().trim().equals("[Ljava.lang.Object;")) {
				Object[] os = (Object[]) o;
				for (int i = 0; i < os.length; i++) {
					doGetMethod(os[i]);
				}
				nlist.add(os);

			} else {
				doGetMethod(o);
				nlist.add(o);
			}

		}
		return nlist;
	}
	
	public String getClassSimpleName(T entity){
		String classSimpleName = entity.getClass().getSimpleName();
		int splitIndex = classSimpleName.indexOf("$$");
		String className = 
			splitIndex == -1 ? classSimpleName : classSimpleName.substring(0, splitIndex);
		System.out.println("ClassName===>"+className);
		return className;
	}

	/**
	 * 调用自身get和set方法
	 */
	public void doGetMethod(Object o) {
		Method[] methods = o.getClass().getMethods();

		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				String prop = getProperites(method.getName());
				try {
					Method getmethod = getMethod(
							o,
							"get" + prop.substring(0, 1).toUpperCase()
									+ prop.substring(1));
					Object obj = getmethod.invoke(o, null);
					if (obj instanceof Set) {
						Set nset = new HashSet();
						Set set = (Set) obj;
						for (Object o2 : set) {
							nset.add(o2);
						}
						method.invoke(o, new Object[] { nset });
					} else {
						method.invoke(o, new Object[] { obj });
					}

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
}