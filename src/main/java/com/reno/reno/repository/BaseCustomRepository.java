package com.reno.reno.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.data.domain.Pageable;

public class BaseCustomRepository {
	protected EntityManagerFactory _entityManagerFactory;

	@PersistenceUnit
	public void setEntityManager(EntityManagerFactory entityManager) {
		_entityManagerFactory = entityManager;
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> executeSqlString(String sqlString, Class<T> cls) {
		List<T> resultList = new ArrayList<>();
		EntityManager em = this._entityManagerFactory.createEntityManager();
		try {
			resultList = em.createNativeQuery(sqlString, cls).getResultList();
		} finally {
			em.close();
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> exceutePageableSqlString(String sqlString, Pageable page) {
		List<Object[]> resultList = new ArrayList<>();
		EntityManager em = _entityManagerFactory.createEntityManager();
		try {
			resultList = em.createNativeQuery(sqlString).getResultList();
		} finally {
			em.close();
		}
		return resultList;
	}

	public Long executeCountSqlString(String sqlString) {
		EntityManager em = _entityManagerFactory.createEntityManager();
		try {
			Query query = em.createNativeQuery(sqlString);
			Object resultObj = query.getSingleResult();
			return convertToLongFromBigIntegerObject(resultObj);
		} catch (NoResultException e) {
			return 0L;
		} finally {
			em.close();
		}
	}

	protected Long convertToLongFromBigIntegerObject(Object data) {
		if (data instanceof BigInteger) {
			return ((BigInteger) data).longValue();
		}
		return null;
	}

	protected Long convertToLongFromIntegerObject(Object data) {
		if (data instanceof Integer) {
			return ((Integer) data).longValue();
		}
		return null;
	}

	protected Double extractDoubleSafty(Object data) {
		Double result = null;
		if (data != null) {
			result = (Double) data;
		}
		return result;
	}

	protected Integer extractIntegerSafty(Object data) {
		Integer result = null;
		if (data != null) {
			result = (Integer) data;
		}
		return result;
	}

	protected String extractStringSafty(Object data) {
		String result = null;
		if (data != null) {
			result = data.toString();
		}
		return result;
	}

	protected Date extractDateSafty(Object data) {
		Date result = null;
		if (data != null) {
			result = (Date) data;
		}
		return result;
	}

	protected <T> T extractDataSafty(Object data, Class<T> cls) {
		if (data == null) {
			return null;
		}
		if (!cls.isInstance(data)) {
			throw new IllegalArgumentException("extractDataSafty: Data is not of the expected type");
		}
		return cls.cast(data);
	}

	protected Boolean extractBooleanSafty(Object data) {
		Boolean result = null;
		if (data != null) {
			result = (Boolean) data;
		}
		return result;
	}
}
