package com.afshin.Controller;
/**
 * @Project order
 * @Author Afshin Parhizkari
 * @Date 2020 - 12 - 05
 * @Time 1:10 PM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import com.afshin.Dao.EmployeeDao;
import com.afshin.Entity.Employee;
import com.afshin.General.Logback;
import com.afshin.Dao.JRsqlFunc;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "EmployeeAct",urlPatterns = {"/api/EmployeeAct"})
public class EmployeeCon extends HttpServlet {
    EmployeeDao dao =new EmployeeDao();
    List<Employee> employeeList =new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      try {
          if (!SecurityAPI.isLogin(req)) {req.getRequestDispatcher("/index.jsp").forward(req, resp); return;}
          employeeList.clear();
          String action = req.getParameter("crud");
          if (action.equals("read")) {
              Logback.logger.trace("{}.{}|read: Enter to IF!",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
              String employeeid = req.getParameter("empNum");
              if (employeeid.isEmpty()) employeeList = dao.findall();
              else employeeList.add(dao.findbyid(Integer.parseInt(employeeid)));
              if(employeeList.isEmpty() || employeeList.get(0)==null)
                  req.setAttribute("message", "There is no record");
              else
                  req.setAttribute("message", "record(s) is fetched");
              Logback.logger.trace("{}.{}|read: Exit from IF!",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
          }
          if (action.equals("add")) {
              Logback.logger.trace("{}.{}|add: Enter to IF!",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
              Employee employee = new Employee();
              employee.setEmployeeNumber(Integer.parseInt(req.getParameter("empnum")));
              employee.setLastName(req.getParameter("lname"));
              employee.setFirstName(req.getParameter("fname"));
              employee.setExtension(req.getParameter("exten"));
              employee.setEmail(req.getParameter("email"));
              employee.setOfficeCode(req.getParameter("offcode"));
              employee.setReportsTo(Integer.parseInt(req.getParameter("repto")));
              employee.setJobTitle(req.getParameter("jobtit"));
              Integer status = dao.insert(employee);
              if(status==employee.getEmployeeNumber()) req.setAttribute("message", "record is Added");
              else req.setAttribute("message", "record is not Added");
              employeeList.add(dao.findbyid(status));
              Logback.logger.trace("{}.{}|add: Exit from IF!", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());
          }
          if (action.equals("update")) {
              Logback.logger.trace("{}.{}|update: Enter to IF!",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
              Employee employee = new Employee();
              employee.setEmployeeNumber(Integer.parseInt(req.getParameter("empnum")));
              employee.setLastName(req.getParameter("lname"));
              employee.setFirstName(req.getParameter("fname"));
              employee.setExtension(req.getParameter("exten"));
              employee.setEmail(req.getParameter("email"));
              employee.setOfficeCode(req.getParameter("offcode"));
              employee.setReportsTo(Integer.parseInt(req.getParameter("repto")));
              employee.setJobTitle(req.getParameter("jobtit"));
              Integer status = dao.update(employee);
              if(status==employee.getEmployeeNumber()) req.setAttribute("message", "record is Updated");
              else req.setAttribute("message", "record is not Updated");
              employeeList.add(dao.findbyid(status));
              Logback.logger.trace("{}.{}|update: Exit to IF!",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
          }
          req.setAttribute("employees", employeeList);
          req.getRequestDispatcher("/WEB-INF/views/Employee.jsp").forward(req, resp);
      }catch (Exception e){
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
            employeeList.clear();
            String action = req.getParameter("crud");
            if (action.equals("delete")) {
                Logback.logger.trace("{}.{}|delete: Enter to IF!",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
                Employee employee = dao.findbyid(Integer.parseInt(req.getParameter("employeenum")));
                Integer status = dao.delete(employee);
                if(status==1) {
                    req.setAttribute("message", "record is deleted");
                    Logback.logger.trace("{}.{}|delete: Successfully Exit from IF!", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());
                    req.getRequestDispatcher("/WEB-INF/views/Employee.jsp").forward(req, resp);
                }else{
                    req.setAttribute("message", "record is not deleted");
                    Logback.logger.trace("{}.{}|delete: Not Successfully Exit from IF!", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());
                    req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
                }
            }
            if (action.equals("edit")) {
                Logback.logger.trace("{}.{}|edit: Enter to IF!",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
                Employee employee = dao.findbyid(Integer.parseInt(req.getParameter("employeenum")));
                req.setAttribute("employee", employee);
                Logback.logger.trace("{}.{}|edit: Exit from IF!",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
                req.getRequestDispatcher("/WEB-INF/views/EmployeeMerge.jsp").forward(req, resp);
            }
            if (action.equals("mngof")) {
                Logback.logger.trace("{}.{}|manageFrom: Enter to IF!",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
                employeeList = dao.parameterized(Integer.parseInt(req.getParameter("manageof")));
                req.setAttribute("employees", employeeList);
                Logback.logger.trace("{}.{}|manageFrom: Exit from IF!",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
                req.getRequestDispatcher("/WEB-INF/views/Employee.jsp").forward(req, resp);
            }
            if (action.equals("mngby")) {
                Logback.logger.trace("{}.{}|manageBy: Enter to IF!",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
                Employee employee = dao.findbyid(Integer.parseInt(req.getParameter("manageby")));
                employeeList.add(employee);
                req.setAttribute("employees", employeeList);
                Logback.logger.trace("{}.{}|manageBy: Exit from IF!",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
                req.getRequestDispatcher("/WEB-INF/views/Employee.jsp").forward(req, resp);
            }
            if (action.equals("report")) {
                Logback.logger.trace("{}.{}|report: Enter to IF!",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
                String path = req.getSession().getServletContext().getRealPath("/WEB-INF/reports/Employee.jrxml");
                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("emp_num", Integer.parseInt(req.getParameter("empNum")));
                JRsqlFunc.viewReport(path, parameters, "web");
                Logback.logger.trace("{}.{}|report: Exit from IF!",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
                req.getRequestDispatcher("/WEB-INF/views/Employee.jsp").forward(req, resp);
            }
        }catch (Exception e){
            String UUID= java.util.UUID.randomUUID().toString();
            Logback.logger.error("{}.{}|UUID:{} - Exception: {}", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(),UUID, e.getMessage());
            e.printStackTrace();
            req.setAttribute("ErrorKey", UUID);
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}