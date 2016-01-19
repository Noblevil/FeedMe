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
		Session ss = HibernateSessionFactory.getSession();// ��������
		Serializable sz = null;
		try {
			tx = ss.beginTransaction();
			/* tx.begin();// �������� */
			sz = ss.save(o);
			tx.commit();

		} catch (Exception e) {
			if (null != tx) {
				tx.rollback();// ��������
				System.out.println("saveʧ�ܣ������쳣");
				e.printStackTrace();
			}

		} finally {
			HibernateSessionFactory.closeSession();// ʼ�չر�SS
		}
		return (Integer)sz;

	}

	@Override
	public void delete(T o) {
		Session ss = HibernateSessionFactory.getSession();
		try {
			tx = ss.beginTransaction();
			ss.delete(o);// ɾ����¼
			tx.commit();
		} catch (Exception e) {
			if (null != tx) {
				tx.rollback();// ��������
				System.out.println("deleteʧ�ܣ������쳣");
				e.printStackTrace();
			}
		} finally {
			HibernateSessionFactory.closeSession();
		}

	}

	@Override
	public void update(T o) {
		Session ss = HibernateSessionFactory.getSession();// ��������
		try {
			tx = ss.beginTransaction();
			/* tx.begin();// �������� */
			ss.update(o);
			tx.commit();
		} catch (Exception e) {
			if (null != tx) {
				tx.rollback();// ��������
				System.out.println("updateʧ�ܣ������쳣");
				e.printStackTrace();
			}

		} finally {
			HibernateSessionFactory.closeSession();// ʼ�չر�SS
		}

	}

	@Override
	public void saveOrUpdate(T o) {
		Session ss = HibernateSessionFactory.getSession();// ��������
		try {
			tx = ss.beginTransaction();
			/* tx.begin();// �������� */
			ss.saveOrUpdate(o);
			tx.commit();
		} catch (Exception e) {
			if (null != tx) {
				tx.rollback();// ��������
				System.out.println("saveOrUpdateʧ�ܣ������쳣");
				e.printStackTrace();
			}

		} finally {
			HibernateSessionFactory.closeSession();// ʼ�չر�SS
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
		Session session = HibernateSessionFactory.getSession();// ��������
		Query query = session.createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);// Ϊ��ѯ������ò��� ������һ��һ������
			}
		}
		return query.list();
	}

	@Override
	public List<T> find(String hql, List<Object> param) {
		Session session = HibernateSessionFactory.getSession();// ��������
		Query query = session.createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				query.setParameter(i, param.get(i));// Ϊ��ѯ������ò��� ������һ��һ������
													// ��ʵ�����
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
		Session session = HibernateSessionFactory.getSession();// ��������
		Query query = session.createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		query.setFirstResult((page - 1) * rows);
		query.setMaxResults(rows);// ����ҳ�Ĳ�ѯ
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
		Session session = HibernateSessionFactory.getSession();// ��������
		Query query = session.createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				query.setParameter(i, param.get(i));
			}
		}
		query.setFirstResult((page - 1) * rows);
		query.setMaxResults(rows);// ����ҳ�Ĳ�ѯ
		List list = query.list();
		return list;
	}

	@Override
	public T get(Class<T> c, Integer id) {
		Session session = HibernateSessionFactory.getSession();// ��������
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
		Session session = HibernateSessionFactory.getSession();// ��������
		return (Long) session.createQuery(hql).uniqueResult();
	}

	@Override
	public Long count(String hql, Object[] param) {
		Session session = HibernateSessionFactory.getSession();// ��������
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
		Session session = HibernateSessionFactory.getSession();// ��������
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
		Session session = HibernateSessionFactory.getSession();// ��������
		return session.createQuery(hql).executeUpdate();
	}

	@Override
	public Integer executeHql(String hql, Object[] param) {
		Session session = HibernateSessionFactory.getSession();// ��������
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
		Session session = HibernateSessionFactory.getSession();// ��������
		Query Query = session.createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				Query.setParameter(i, param.get(i));
			}
		}
		return Query.executeUpdate();
	}

}