package org.ithedu.application.config;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * sql 日志拦截器
 * 
 * @author zjn
 *
 */
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class MyInterceptor implements Interceptor {
	private String dialect = ""; // 数据库方言
	private String pageSqlId = ""; // mapper.xml中需要拦截的ID(正则匹配)

	private static final Logger LOGGER = LoggerFactory.getLogger(MyInterceptor.class);

	public Object intercept(Invocation invocation) throws Throwable {

		if (invocation.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
			StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(statementHandler, "delegate");
			BoundSql boundSql = delegate.getBoundSql();

			LOGGER.debug("执行sql :  " + boundSql.getSql());
			if(boundSql.getParameterObject() != null) {
				LOGGER.debug("sql参数  :  " + boundSql.getParameterObject().toString());
			}
		}

		return invocation.proceed();
	}

	/**
	 * 拦截器对应的封装原始对象的方法
	 */
	public Object plugin(Object arg0) {
		// TODO Auto-generated method stub
		if (arg0 instanceof StatementHandler) {
			return Plugin.wrap(arg0, this);
		} else {
			return arg0;
		}
	}

	/**
	 * 设置注册拦截器时设定的属性
	 */
	public void setProperties(Properties p) {

	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getPageSqlId() {
		return pageSqlId;
	}

	public void setPageSqlId(String pageSqlId) {
		this.pageSqlId = pageSqlId;
	}

	/**
	 * 利用反射进行操作的一个工具类
	 *
	 */
	private static class ReflectUtil {
		/**
		 * 利用反射获取指定对象的指定属性
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @return 目标属性的值
		 */
		public static Object getFieldValue(Object obj, String fieldName) {
			Object result = null;
			Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				field.setAccessible(true);
				try {
					result = field.get(obj);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		/**
		 * 利用反射获取指定对象里面的指定属性
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @return 目标字段
		 */
		private static Field getField(Object obj, String fieldName) {
			Field field = null;
			for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
				try {
					field = clazz.getDeclaredField(fieldName);
					break;
				} catch (NoSuchFieldException e) {
					// 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
				}
			}
			return field;
		}

	}

}
