package com.mendelchuk.spring.rest.dao;

import com.mendelchuk.spring.rest.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOImplementation implements EmployeeDAO{

    @Autowired
    private SessionFactory sessionFactory ;
    // для того , чтобы  DAO мог подключаться к БД по средствам Hibernate
    // DAO должен иметь доступ к sessionFactory

    @Override

    public List<Employee> getAllEmployees() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
//        List <Employee> allEmployees = session.createQuery("from Employee",Employee.class)
//                .getResultList();
        // одинаковое написание
        Query <Employee> query = session.createQuery("from Employee",Employee.class);
        List <Employee> allEmployees = query.getResultList();
        session.getTransaction().commit();
        return allEmployees;
    }

    @Override
    public void saveEmployee(Employee employee) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(employee);
        //если id  работника будет рабен 0 (вновь созданный работник), то будет  insert нового работника в БД
       // если id !=0, то будет произведён update  для работника, учитывая его id
    }

    @Override
    public Employee getEmployee(int id) {
        Session session = sessionFactory.getCurrentSession();
        Employee employee = session.get(Employee.class, id);
        return employee;
    }

    @Override
    public void deleteEmployee(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Employee> query = session.createQuery("delete from Employee where id=:employeeId");
        query.setParameter("employeeId",id);
        //employeeId будет заменятся на id работника
        query.executeUpdate();
    }
}
