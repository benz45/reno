package com.reno.reno.repository;

import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Query;

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
	public List<Object[]> exceutePageableSqlString(String sqlString) {
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
			if (resultObj instanceof Long value) {
				return value;
			}
			return convertToLongFromBigInteger(resultObj);
		} catch (NoResultException e) {
			return 0L;
		} finally {
			em.close();
		}
	}

	protected Long convertToLongFromBigInteger(Object data) {
		if (data instanceof Long integer) {
			return integer.longValue();
		}
		return null;
	}

	protected Long convertToLongFromIntegerObject(Object data) {
		if (data instanceof Integer integer) {
			return integer.longValue();
		}
		return null;
	}

	protected Double convertToDouble(Object data) {
		if (data instanceof Double double1) {
			return double1;
		}
		return null;
	}

	protected Integer convertToInteger(Object data) {
		if (data instanceof Integer integer) {
			return integer;
		}
		return null;
	}

	protected String convertToString(Object data) {
		if (data instanceof String) {
			return data.toString();
		}
		return null;
	}

	protected Date convertToDate(Object data) {
		if (data instanceof Instant date) {
			return Date.from(date);
		}
		return null;
	}

	protected Boolean convertToBoolean(Object data) {
		if (data instanceof Boolean boolean1) {
			return boolean1;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected <T> T convertToUniqueType(Object data) {
		if (data instanceof BigInteger integer) {
			return (T) Long.valueOf(integer.longValue());
		}
		if (data instanceof Long | data instanceof Integer | data instanceof Double | data instanceof String
				| data instanceof Date | data instanceof Boolean) {
			return (T) data;
		}
		return null;
	}

	protected <T> T convertToModel(Object data, Class<T> cls) {
		if (data == null)
			return null;
		if (!cls.isInstance(data)) {
			throw new IllegalArgumentException("extractDataSafty: Data is not of the expected type");
		}
		return cls.cast(data);
	}
}
