/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devmedia.consultorioee.service.repository;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author vsaueia
 */
abstract class BasicRepository implements Serializable {

    private final EntityManager entityManager;

    protected static final char LIKE_CHAR = '%';

    public BasicRepository(EntityManager entityManger) {
        this.entityManager = entityManger;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected <T> List<T> getPureList(Class<T> classToCast, String query, Object... values) {
        Query localQuery = createQuery(query, values);
        return localQuery.getResultList();
    }

    protected <T> T getPurePojo(Class<T> classToCast, String query, Object... values) {
        Query localQuery = createQuery(query, values);
        return (T) localQuery.getSingleResult();
    }

    protected int executeCommand(String query, Object... values) {
        Query localQuery = createQuery(query, values);
        return localQuery.executeUpdate();
    }

    private Query createQuery(String query, Object... values) {
        Query localQuery = getEntityManager().createQuery(query);
        for (int i = 0; i < values.length; i++) {
            localQuery.setParameter(i, values[i]);
        }
        return localQuery;
    }

    public static <T> T uniqueResultOrNull(final TypedQuery<T> query)
            throws NonUniqueResultException, NullPointerException {
        return uniqueResultOrElse(query, null);
    }

    public static <T> T uniqueResultOrElse(final TypedQuery<T> query, final T defaultValue)
            throws NonUniqueResultException, NullPointerException {
        return executeUnique(query, defaultValue);
    }

    private static <T> T executeUnique(final Query query, final T defaultValue)
            throws NonUniqueResultException, NullPointerException {

        @SuppressWarnings("unchecked")
        final List<T> resultList = query.setMaxResults(2).getResultList();

        final int size = resultList.size();

        if (size > 1) {
            throw new NonUniqueResultException("Result returns more than one element");
        }

        return size == 0 ? defaultValue : resultList.get(0);
    }

    public <T> T getEntity(Class<T> clazz, Serializable pk) {
        return getEntityManager().find(clazz, pk);
    }

    public <T> T updateEntity(Class<T> clazz, T entity) {
        return getEntityManager().merge(entity);
    }

    @Transactional
    public <T> void removeEntity(Class<T> clazz, T entity) {
        T merged = updateEntity(clazz, entity);
        getEntityManager().remove(merged);
    }

    public <T> T addEntity(Class<T> clazz, T entity) {
        getEntityManager().persist(entity);
        return (T) entity;
    }
}
