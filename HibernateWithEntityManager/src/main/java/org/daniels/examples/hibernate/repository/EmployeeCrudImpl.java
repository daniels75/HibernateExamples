package org.daniels.examples.hibernate.repository;

import org.daniels.examples.hibernate.entities.Employee;
import org.daniels.examples.hibernate.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EmployeeCrudImpl implements EmployeeCrud {

    public EmployeeCrudImpl() {
    }

    public Employee saveEmployee(Employee employee) {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return employee;
    }

    public Employee updateEmployee(Employee employee) {
        EntityManager em = HibernateUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(employee);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return employee;
    }

    public List<Employee> findAllEmployee() {
        EntityManager em = HibernateUtil.createEntityManager();
        Query query = em.createNamedQuery(Employee.FIND_ALL);
        List<Employee> list = query.getResultList();
        em.close();
        return list;
    }

    @Override
    public void updateEmployee(Long id) {
        System.out.println("[>WRITE] START");
        EntityManager em = HibernateUtil.createEntityManager();
        em.getTransaction().begin();
        System.out.println("[>WRITE] Before lock PESSIMISTIC_WRITE");
        Employee employee = em.find(Employee.class, id, LockModeType.PESSIMISTIC_WRITE);
        System.out.println("[>WRITE] employee:" + employee);
        System.out.println("[>WRITE] After lock PESSIMISTIC_WRITE");
        try {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("[>WRITE] Waiting 3 second");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        employee.setFirstName("XY New Name");
        em.getTransaction().commit();
        System.out.println("[>WRITE] STOP");
    }

    @Override
    public void readEmployee(Long id) {
        System.out.println("[<READ] START");

        try {
            System.out.println("[>READ] Waiting 100 millisecond");
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        EntityManager em = HibernateUtil.createEntityManager();
        em.getTransaction().begin();
        System.out.println("[<READ] Before lock PESSIMISTIC_READ");
        Employee employee = em.find(Employee.class, id, LockModeType.PESSIMISTIC_READ);
        System.out.println("[<READ] employee: " + employee);
        System.out.println("[<READ] After lock PESSIMISTIC_READ");
        em.getTransaction().commit();
        System.out.println("[<READ]  STOP");
    }

}
