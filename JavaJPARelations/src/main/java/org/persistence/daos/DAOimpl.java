package org.persistence.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.persistence.HibernateConfig;
import org.persistence.entities.Address;
import org.persistence.entities.Employee;

public class DAOimpl implements IDAO<Employee> {
    EntityManagerFactory emf;

    public DAOimpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void create(Employee emp) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Address address = emp.getAddress();

            if (address != null) {
                String street = address.getStreet();
                TypedQuery<Address> query = em.createQuery("SELECT a FROM Address a WHERE a.street = :street", Address.class);
                query.setParameter("street", street);
                try {
                    Address found = query.getSingleResult();
                    emp.setAddress(found);
                } catch(NoResultException e){
                    em.persist(address);
                }
            }
            em.persist(emp);
            em.getTransaction().commit();
        }
    }
    public void update(Employee emp) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Address address = emp.getAddress();

            if (address != null) {
                String street = address.getStreet();
                TypedQuery<Address> query = em.createQuery("SELECT a FROM Address a WHERE a.street = :street", Address.class);
                query.setParameter("street", street);
                try {
                    Address found = query.getSingleResult();

                } catch(NoResultException e){
                    em.persist(emp);
                }
            }
            em.merge(emp);
            em.getTransaction().commit();
        }
    }
    @Override
    public Employee getByID(Long id) {
        try(EntityManager em = emf.createEntityManager()) {
            return em.find(Employee.class, id);
        }
    }
    public static void main(String[] args) {
        DAOimpl dao = new DAOimpl(HibernateConfig.getEntityManagerFactory());
        Employee e1 = Employee.builder()
                .name("Otto")
                .build();
        Address a1 = Address.builder()
                .street("Holger Danskes vej 87")
                .build();

        //a1.addEmployee(e1);
        //dao.create(e1);
        Employee foundEmp = dao.getByID(1l);
        foundEmp.setName("Xander");
        dao.update(foundEmp);
    }
}
