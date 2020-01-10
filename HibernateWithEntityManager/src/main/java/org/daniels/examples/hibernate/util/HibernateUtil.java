package org.daniels.examples.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

    private static final String HIBERNATE_ANNOTATION_CFG_XML = "hibernate-annotation.cfg.xml";
    
	// Annotation based configuration
    private static SessionFactory sessionAnnotationFactory;

    private static EntityManager entityManager;

    private static SessionFactory buildSessionAnnotationFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure(HIBERNATE_ANNOTATION_CFG_XML);
            System.out.println("Hibernate Annotation Configuration loaded");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();
            System.out.println("Hibernate Annotation serviceRegistry created");

            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


    public static SessionFactory getSessionAnnotationFactory() {
        if (sessionAnnotationFactory == null) {
            sessionAnnotationFactory = buildSessionAnnotationFactory();
        }
        return sessionAnnotationFactory;
    }

    public static EntityManager createEntityManager() {
        if(entityManager == null){
            EntityManagerFactory emFactory =
                    Persistence.createEntityManagerFactory("sample");
            entityManager =  emFactory.createEntityManager();
            return entityManager;
        }
        return entityManager;
    }

    public static void closeEntityManager() {
        if (entityManager != null) {
            entityManager.close();
        }
    }
}
