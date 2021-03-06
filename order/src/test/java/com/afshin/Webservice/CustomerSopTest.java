package com.afshin.Webservice;
/**
 * @Project order
 * @Author Afshin Parhizkari
 * @Date 3/18/21
 * @Time 4:29 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:  without any authentication
                1. Add @XmlRootElement,@XmlAccessorType annotation to Entity for mapping obj to XML and vice versa
                2. Develop Soap service
                3. Test service via Postman
                4. Build Project and Deploy it via Tomee
                5. Add codehaus.mojo library in POM Plugin
                3. Create(wsimport) Stub by Maven build
                4. Develop client side(test)
 */
import com.afshin.Webservice.stub.customer.Customer;
import com.afshin.Webservice.stub.customer.CustomerArray;
import com.afshin.Webservice.stub.customer.CustomerInt;
import com.afshin.Webservice.stub.customer.CustomerSrv;
import org.junit.Test;
import javax.xml.ws.WebServiceClient;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@WebServiceClient(name = "CustomerSrvClient",
        wsdlLocation = "http://localhost:8080/order/soap/customer")
public class CustomerSopTest {
    String soapServicePath="http://localhost:8080/order/soap/customer";
    URL url = new URL(soapServicePath);
    CustomerSrv CSrv = new CustomerSrv(url);
    CustomerInt CInt = CSrv.getCustomerIntPort();
    Integer objID=497;

    public CustomerSopTest() throws MalformedURLException {}
    public String toString(Customer customer) {
        return "Customer{" +
                "customerNumber=" + customer.getCustomerNumber() +
                ", customerName='" + customer.getCustomerName() + '\'' +
                ", contactLastName='" + customer.getContactLastName() + '\'' +
                ", contactFirstName='" + customer.getContactFirstName() + '\'' +
                ", phone='" + customer.getPhone() + '\'' +
                ", addressLine1='" + customer.getAddressLine1() + '\'' +
                ", addressLine2='" + customer.getAddressLine2() + '\'' +
                ", city='" + customer.getCity() + '\'' +
                ", state='" + customer.getState() + '\'' +
                ", postalCode='" + customer.getPostalCode() + '\'' +
                ", country='" + customer.getCountry() + '\'' +
                ", salesRepEmployeeNumber=" + customer.getSalesRepEmployeeNumber() +
                ", creditLimit=" + customer.getCreditLimit() +
                '}';
    }

    @Test
    public void findTest() throws MalformedURLException {
        Customer customer= CInt.find(objID);
        System.out.println(toString(customer));
    }
    @Test
    public void allTest() throws MalformedURLException {
        CustomerArray customers= CInt.all();
        List<Customer> customerList=customers.getItem();
        for(Customer customer:customerList)
            System.out.println(toString(customer));
    }
    @Test
    public void deleteTest() throws MalformedURLException {
        String returnStatus = CInt.delete(objID);
        System.out.println(returnStatus);
    }
    @Test
    public void insertTest() throws MalformedURLException {
        Customer c=new Customer();
        c.setCustomerNumber(objID);
        c.setCustomerName("Afshin Parhizkari");
        c.setContactLastName("Kitty");
        c.setContactFirstName("Helman");
        c.setPhone("+989032430637");
        c.setAddressLine1("Iran Tehran 1");
        c.setAddressLine2(" c");
        c.setCity("Tehran");
        c.setState("Th");
        c.setPostalCode("123456789");
        c.setCountry("Iran");
        c.setSalesRepEmployeeNumber(1612);
        c.setCreditLimit(new BigDecimal(150000));
        String returnStatus = CInt.insert(c);
        System.out.println(returnStatus);
        Customer customer= CInt.find(objID);
        System.out.println(toString(customer));
    }
    @Test
    public void updateTest() throws MalformedURLException {
        Customer c=new Customer();
        c.setCustomerNumber(objID);
        c.setCustomerName("Helman Kitty");
        c.setContactLastName("Parhizkari");
        c.setContactFirstName("Afshin");
        c.setPhone("+9030000000");
        c.setAddressLine1("Persian Qazvin 1");
        c.setAddressLine2(" Street and Building");
        c.setCity("Qazvin");
        c.setState("Qa");
        c.setPostalCode("987654321");
        c.setCountry("Persian");
        c.setSalesRepEmployeeNumber(1188);
        c.setCreditLimit(new BigDecimal(100000));
        String returnStatus = CInt.update(c);
        System.out.println(returnStatus);
        System.out.println(toString(CInt.find(objID)));
    }
}