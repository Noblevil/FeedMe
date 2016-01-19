package com.feedme.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.feedme.dao.BaseDao;
import com.feedme.util.HibernateSessionFactory;

public class BaseDaoImpl<T> implements BaseDao<T> {

	Transaction tx = null;

	@Override
	public Integer save(T o) {
		Session ss = HibernateSessionFactory.getSession();// 建立连接
		Serializable sz = null;
		try {
			tx = ss.beginTransaction();
			/* tx.begin();// 开启事务 */
			sz = ss.save(o);
			tx.commit();

		} catch (Exception e) {
			if (null != tx) {
				tx.rollback();// 撤销事务
				System.out.println("save失败，出现异常");
				e.printStackTrace();
			}

		} finally {
			HibernateSessionFactory.closeSession();// 始终关闭SS
		}
		return (Integer)sz;

	}

	@Override
	public void delete(T o) {
		Session ss = HibernateSessionFactory.getSession();
		try {
			tx = ss.beginTransaction();
			ss.delete(o);// 删除记录
			tx.commit();
		} catch (Exception e) {
			if (null != tx) {
				tx.rollback();// 撤销事务
				System.out.println("delete失败，出现异常");
				e.printStackTrace();
			}
		} finally {
			HibernateSessionFactory.closeSession();
		}

	}

	@Override
	public void update(T o) {
		Session ss = HibernateSessionFactory.getSession();// 建立连接
		try {
			tx = ss.beginTransaction();
			/* tx.begin();// 开启事务 */
			ss.update(o);
			tx.commit();
		} catch (Exception e) {
			if (null != tx) {
				tx.rollback();// 撤销事务
				System.out.println("update失败，出现异常");
				e.printStackTrace();
			}

		} finally {
			HibernateSessionFactory.closeSession();// 始终关闭SS
		}

	}

	@Override
	public void saveOrUpdate(T o) {
		Session ss = HibernateSessionFactory.getSession();// 建立连接
		try {
			tx = ss.beginTransaction();
			/* tx.begin();// 开启事务 */
			ss.saveOrUpdate(o);
			tx.commit();
		} catch (Exception e) {
			if (null != tx) {
				tx.rollback();// 撤销事务
				System.out.println("saveOrUpdate失败，出现异常");
				e.printStackTrace();
			}

		} finally {
			HibernateSessionFactory.closeSession();// 始终关闭SS
		}

	}

	@Override
	public List<T> find(String hql) {
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		List list = query.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	@Override
	public List<T> find(String hql, Object[] param) {
		Session session = HibernateSessionFactory.getSession();// 建立连接
		Query query = session.createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);// 为查询语句设置参数 参数是一个一个对象
			}
		}
		return query.list();
	}

	@Override
	public List<T> find(String hql, List<Object> param) {
		Session session = HibernateSessionFactory.getSession();// 建立连接
		Query query = session.createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				query.setParameter(i, param.get(i));// 为查询语句设置参数 参数是一个一个对象
													// 其实差不多了
			}
		}
		return query.list();
	}

	@Override
	public List<T> find(String hql, Object[] param, Integer page, Integer rows) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Session session = HibernateSessionFactory.getSession();// 建立连接
		Query query = session.createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		query.setFirstResult((page - 1) * rows);
		query.setMaxResults(rows);// 带分页的查询
		List list = query.list();
		return list;
	}

	@Override
	public List<T> find(String hql, List<Object> param, Integer page, Integer rows) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Session session = HibernateSessionFactory.getSession();// 建立连接
		Query query = session.createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				query.setParameter(i, param.get(i));
			}
		}
		query.setFirstResult((page - 1) * rows);
		query.setMaxResults(rows);// 带分页的查询
		List list = query.list();
		return list;
	}

	@Override
	public T get(Class<T> c, Integer id) {
		Session session = HibernateSessionFactory.getSession();// 建立连接
		return (T) session.get(c, id);

	}

	@Override
	public T get(String hql, Object[] param) {
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	@Override
	public T get(String hql, List<Object> param) {
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Long count(String hql) {
		Session session = HibernateSessionFactory.getSession();// 建立连接
		return (Long) session.createQuery(hql).uniqueResult();
	}

	@Override
	public Long count(String hql, Object[] param) {
		Session session = HibernateSessionFactory.getSession();// 建立连接
		Query query = session.createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		return (Long) query.uniqueResult();
	}

	@Override
	public Long count(String hql, List<Object> param) {
		Session session = HibernateSessionFactory.getSession();// 建立连接
		Query query = session.createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				query.setParameter(i, param.get(i));
			}
		}
		return (Long) query.uniqueResult();
	}

	@Override
	public Integer executeHql(String hql) {
		Session session = HibernateSessionFactory.getSession();// 建立连接
		return session.createQuery(hql).executeUpdate();
	}

	@Override
	public Integer executeHql(String hql, Object[] param) {
		Session session = HibernateSessionFactory.getSession();// 建立连接
		Query Query = session.createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				Query.setParameter(i, param[i]);
			}
		}
		return Query.executeUpdate();
	}

	@Override
	public Integer executeHql(String hql, List<Object> param) {
		Session session = HibernateSessionFactory.getSession();// 建立连接
		Query Query = session.createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				Query.setParameter(i, param.get(i));
			}
		}
		return Query.executeUpdate();
	}

}