package com.afshin.Entity;
/**
 * @Project order
 * @Author Afshin Parhizkari
 * @Date 2020 - 11 - 27
 * @Time 5:59 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import com.afshin.General.DateAdapter;
import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
@JsonFilter("OrderFilter")
@XmlRootElement(name = "OrderXML")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
    public Order() {}

    @Id
    @Column(name = "orderNumber")
    private Integer orderNumber;

    @Column(name = "orderDate")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date orderDate;

    @Column(name = "requiredDate")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date requiredDate;

    @Column(name = "shippedDate")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date shippedDate;

    @Column(name = "status")
    private String status;

    @Column(name = "comments")
    private String comments;

    @Column(name = "customerNumber")
    private Integer customerNumber;

    @OneToMany(mappedBy = "order")
    @XmlTransient
    private List<Orderdetail> orderdetails;

    @ManyToOne
    @JoinColumn(name = "customerNumber",referencedColumnName = "customerNumber",insertable = false,updatable = false)
    @XmlTransient
    private Customer customer;

    public Customer getCustomer() {return customer;}
    public void setCustomer(Customer customer) {this.customer = customer;}

    public Integer getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }
    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }
    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }
    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    public List<Orderdetail> getOrderdetails() {
        return orderdetails;
    }
    public void setOrderdetails(List<Orderdetail> orderdetails) {
        this.orderdetails = orderdetails;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                //", orderDate=" + GregorianDate.shamsiStr(GregorianDate.miladi2shamsi(orderDate)) +
                //", requiredDate=" + GregorianDate.shamsiStr(GregorianDate.miladi2shamsi(requiredDate)) +
                //", shippedDate=" +GregorianDate.shamsiStr(GregorianDate.miladi2shamsi(shippedDate))+
                ", status='" + status + '\'' +
                ", comments='" + comments + '\'' +
                ", customerNumber=" + customerNumber +
                //", orderdetails=" + orderdetails +
                '}';
    }
    @Transient
    public Set<String> getfilters(){
        Set<String> hash_Set = new HashSet<String>();
        hash_Set.add("orderNumber");hash_Set.add("orderDate");hash_Set.add("requiredDate");
        hash_Set.add("shippedDate");hash_Set.add("status");hash_Set.add("comments");
        hash_Set.add("customerNumber");
        return hash_Set;
    }
}
