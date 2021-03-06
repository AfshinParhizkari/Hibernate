package com.afshin.Controller;

import com.afshin.Dao.CustomerDao;
import com.afshin.Entity.Customer;
import com.afshin.General.Logback;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Project order
 * @Author Afshin Parhizkari
 * @Date 2020 - 12 - 31
 * @Time 6:20 PM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
@WebServlet(name = "CustomerAct", urlPatterns = {"/api/CustomerAct"})
public class CustomerCon extends HttpServlet {
    CustomerDao dao = new CustomerDao();
    List<Customer> customerList = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!SecurityAPI.isLogin(req)) {req.getRequestDispatcher("/index.jsp").forward(req, resp); return;}
            customerList.clear();
            String action = req.getParameter("crud");
            if (action.equals("read")) {
                String customerNum = req.getParameter("custnum");
                if (customerNum == null || customerNum.isEmpty()) customerList = dao.findall();
                else customerList.add(dao.findbyid(Integer.parseInt(customerNum)));
                if(customerList.isEmpty() || customerList.get(0)==null)
                    req.setAttribute("message", "There is no record");
                else
                    req.setAttribute("message", "record(s) is fetched");
            }
            if (action.equals("add")) {
                Customer customer = new Customer();
                customer.setCustomerNumber(Integer.parseInt(req.getParameter("custnum")));
                customer.setCustomerName(req.getParameter("custname"));
                customer.setContactLastName(req.getParameter("conlname"));
                customer.setContactFirstName(req.getParameter("confname"));
                customer.setPhone(req.getParameter("phone"));
                customer.setAddressLine1(req.getParameter("addl1"));
                customer.setAddressLine2(req.getParameter("addl2"));
                customer.setCity(req.getParameter("city"));
                customer.setState(req.getParameter("state"));
                customer.setPostalCode(req.getParameter("poscode"));
                customer.setCountry(req.getParameter("count"));
                customer.setSalesRepEmployeeNumber(Integer.parseInt(req.getParameter("srempnum")));
                customer.setCreditLimit(new BigDecimal(req.getParameter("credlim")));
                Integer status = dao.insert(customer);
                if(status==customer.getCustomerNumber()) req.setAttribute("message", "record is Added");
                else req.setAttribute("message", "record is not Added");
                customerList.add(dao.findbyid(status));
            }
            if (action.equals("update")) {
                Customer customer = dao.findbyid(Integer.parseInt(req.getParameter("custnum")));
                customer.setCustomerName(req.getParameter("custname"));
                customer.setContactLastName(req.getParameter("conlname"));
                customer.setContactFirstName(req.getParameter("confname"));
                customer.setPhone(req.getParameter("phone"));
                customer.setAddressLine1(req.getParameter("addl1"));
                customer.setAddressLine2(req.getParameter("addl2"));
                customer.setCity(req.getParameter("city"));
                customer.setState(req.getParameter("state"));
                customer.setPostalCode(req.getParameter("poscode"));
                customer.setCountry(req.getParameter("count"));
                customer.setSalesRepEmployeeNumber(Integer.parseInt(req.getParameter("srempnum")));
                customer.setCreditLimit(new BigDecimal(req.getParameter("credlim")));
                Integer status = dao.update(customer);
                if(status==customer.getCustomerNumber()) req.setAttribute("message", "record is Updated");
                else req.setAttribute("message", "record is not Updated");
                customerList.add(dao.findbyid(status));
            }
            req.setAttribute("customers", customerList);
            req.getRequestDispatcher("/WEB-INF/views/Customer.jsp").forward(req, resp);
        } catch (Exception e) {
            String UUID= java.util.UUID.randomUUID().toString();
            Logback.logger.error("{}.{}|UUID:{} - Exception: {}", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(),UUID, e.getMessage());
            e.printStackTrace();
            req.setAttribute("ErrorKey", UUID);
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!SecurityAPI.isLogin(req)) {req.getRequestDispatcher("/index.jsp").forward(req, resp); return;}
            customerList.clear();
            String action = req.getParameter("crud");
            if (action.equals("delete")) {
                Integer status = dao.delete(dao.findbyid(Integer.parseInt(req.getParameter("custnum"))));
                if(status==1) {
                    req.setAttribute("message", "record is deleted");
                    req.getRequestDispatcher("/WEB-INF/views/Customer.jsp").forward(req, resp);
                }else{
                    req.setAttribute("message", "record is not deleted");
                    req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
                }
            }
            if (action.equals("edit")) {
                Customer customer = dao.findbyid(Integer.parseInt(req.getParameter("custnum")));
                req.setAttribute("customer", customer);
                req.getRequestDispatcher("/WEB-INF/views/CustomerMerge.jsp").forward(req, resp);
            }
            if (action.equals("report")) {
                String path = req.getSession().getServletContext().getRealPath("/WEB-INF/reports/Customer.jrxml");
                try {
                    //compile .jrxml(Human Understanding) file to .jasper(Machine understanding)
                    JasperReport jreport = JasperCompileManager.compileReport(path);
                    // Move data to Jasper collection data source
                    JRBeanCollectionDataSource jcs = new JRBeanCollectionDataSource(dao.findall());
                    //fill reports
                    JasperPrint jprint = JasperFillManager.fillReport(jreport, null, jcs);
                    // Viewing the report
                    JasperViewer.viewReport(jprint, false);
                } catch (JRException e) {
                    e.printStackTrace();
                }
                req.getRequestDispatcher("/WEB-INF/views/Customer.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            String UUID= java.util.UUID.randomUUID().toString();
            Logback.logger.error("{}.{}|UUID:{} - Exception: {}", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(),UUID, e.getMessage());
            e.printStackTrace();
            req.setAttribute("ErrorKey", UUID);
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}