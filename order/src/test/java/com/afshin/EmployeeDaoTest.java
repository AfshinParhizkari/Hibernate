package com.afshin;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
public class EmployeeDaoTest {
    EmployeeDao dao=new EmployeeDao();
    //ExecuteQuery
    @Test
    public void findall() {
        List<Employee> List =dao.findall();
        for(Employee tmp:List)
        {System.out.println(tmp);}
    }
    @Test
    public void findbyid() {
        Employee tmp =dao.findbyid(1143);
        System.out.println(tmp);
    }
    @Test
    public void joinedQuey() {
        List<Employee> List =dao.joinedQuey();
        for(Employee tmp:List)
        {System.out.println(tmp);}
    }
    @Test
    public void aggregation() {
        List<?> list = dao.aggregation();
        for (int i = 0; i < list.size(); i++) {
            Object[] row = (Object[]) list.get(i);
            System.out.println(row[0] + ", " + row[1]);
        }
    }
    @Test
    public void parameterized() {
        List<?> list = dao.parameterized(1143);
        for (int i = 0; i < list.size(); i++) {
            Object[] row = (Object[]) list.get(i);
            System.out.println(row[0] + "-> " + row[1]+" " + row[2]);
        }
    }

    //ExecuteUpdate
    @Test
    public void insertTest(){
        Employee e=new Employee();
        e.setEmployeeNumber(1705);
        e.setLastName("Parhizkari");
        e.setFirstName("Afshin");
        e.setEmail("Afshin.Parhizkari@gmail.com");
        e.setExtension("x2542");
        e.setOfficeCode("1");
        e.setReportsTo(1002);
        e.setJobTitle("Java Dev");
        dao.insert(e);
    }
    @Test
    public void updateTest(){
        Employee e=dao.findbyid(1705);
        e.setEmployeeNumber(1705);
        e.setLastName("Maraghi");
        e.setFirstName("Hadi");
        e.setEmail("Afshin.Parhizkari@unknown.com");
        e.setExtension("x2543");
        e.setOfficeCode("7");
        e.setReportsTo(1501);
        e.setJobTitle("Python Dev");
        dao.update(e);
    }
    @Test
    public void deleteTest(){
        Employee e=dao.findbyid(1705);
        dao.delete(e);
    }
}