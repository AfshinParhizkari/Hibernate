<%@ page import="com.afshin.Entity.Payment" %>
<%@ page import="java.util.List" %>
<%@ page import="com.afshin.General.GregorianDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  @Project     order
  @Author      Afshin Parhizkari
  @Date        2020 - 12 - 15
  @Time        11:50 PM
  Created by   IntelliJ IDEA
  Email:       Afshin.Parhizkari@gmail.com
  Description:
--%>
<html>
<head>
    <title>Payment List</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/api/Dispatcher" method="get">
    <input type="button" value="Home" onclick="location.href='${pageContext.request.contextPath}/index.jsp';">
    <input type="hidden" name="entity" value="PaymentMerge">
    <input type="submit" value="Add">
</form>
<form action="${pageContext.request.contextPath}/api/Dispatcher" method="get">
    <input type="hidden" name="entity" value="PaymentRep">
    <input type="submit" value="ReportPage">
</form><form action="PaymentAct" method="post">
    Customer Number: <input type="number" name="custnum">
    Check Number: <input type="text" name="checknum">
    <input type="hidden" name="crud" value="read">
    <input type="submit" value="ShowPayment">
</form>
<%
    String msg = (String) request.getAttribute("message");
    if(msg!=null)
        out.print(msg);
%>
<table border="1px">
    <tr>
        <td>customerNumber</td>
        <td>checkNumber</td>
        <td>paymentDate</td>
        <td>amount</td>
        <td>edit</td>
        <td>delete</td>
    </tr>
    <%
        List<Payment> paymentList = (List<Payment>) request.getAttribute("payments");
        if (paymentList != null ){
            for (Payment payment : paymentList){
                if (payment != null){
    %>
    <tr>
        <td><%=payment.getCustomerNumber()%></td>
        <td><%=payment.getCheckNumber()%></td>
        <td><%=GregorianDate.shamsiStr(GregorianDate.miladi2shamsi(payment.getPaymentDate()))%></td>
        <td><%=payment.getAmount()%></td>
        <td><a href="${pageContext.request.contextPath}/api/PaymentAct?custnum=<%=payment.getCustomerNumber()%>&checknum=<%=payment.getCheckNumber()%>&crud=edit"> edit </a></td>
        <td><a href="${pageContext.request.contextPath}/api/PaymentAct?custnum=<%=payment.getCustomerNumber()%>&checknum=<%=payment.getCheckNumber()%>&crud=delete"> delete </a></td>
    </tr>
    <%}}}%>
</table>
</body>
</html>