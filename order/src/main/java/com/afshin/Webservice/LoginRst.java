package com.afshin.Webservice;
/**
 * @Project order
 * @Author Afshin Parhizkari
 * @Date 3/3/21
 * @Time 1:39 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import com.afshin.General.Logback;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.util.encoders.Base64;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Path("/login")
public class LoginRst {
    Security sec=new Security();
    final long amountToAdd=10l;

    //  http://localhost:8080/order/rest/login/check
    @GET
    @Path("/check")
    public Response echo(@Context HttpHeaders headers) {
        try {
            String token = headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0).substring("Bearer ".length()).trim();
            if (sec.tokenAuthCheck(token)) {
                Logback.logger.error("{}.{}| token is valid", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());
                return Response.status(Response.Status.OK).entity("token is valid").build();
            } else {
                Logback.logger.error("{}.{}| token is Expired", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());
                return Response.status(Response.Status.UNAUTHORIZED).entity("token is Expired").build();
            }
        } catch (Exception e) {
            String UUID = java.util.UUID.randomUUID().toString();
            Logback.logger.error("{}.{}|UUID:{} - Exception: {}", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), UUID, e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.EXPECTATION_FAILED).entity("Your Trace number is" + UUID + e.toString()).build();
        }
    }

    //  http://localhost:8080/order/rest/login/token
    @GET
    @Path("/token")
    public Response getToken(@Context HttpHeaders headers){
        //Get encoded username and password
        String encodUsrPwd=headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0).replaceFirst("Basic ", "");
        if(!sec.basicAuthCheck(encodUsrPwd))
            return Response.status(Response.Status.UNAUTHORIZED).entity("User or password is wrong").build();
        try {
            String credential = new String(Base64.decode(encodUsrPwd));
            String login = credential.substring(0,credential.indexOf(":"));

            //Calendar cal = Calendar.getInstance();cal.setTime(new Date());cal.add(Calendar.MINUTE, +2);
            //.setExpiration(cal.getTime())
            String jwtToken = Jwts.builder().setSubject(login).setId(UUID.randomUUID().toString())
                    .setIssuedAt(Date.from(Instant.now()))
                    .setExpiration(Date.from(Instant.now().plus(amountToAdd, ChronoUnit.MINUTES)))
                    .setIssuer("https://github.com/AfshinParhizkari/Hibernate")
                    .signWith(SignatureAlgorithm.HS512,"sharekeyisafshin").compact();
            //Header : type of the token CryptographicAlgorithm (alg:HS512) , JWT
            //payload :  setSubject(login),setIssuedAt(Date).setExpiration(ExprDate)
            //signature : signWith(SignatureAlgorithm.HS512,"private/secret Key=sharekeyisafshin")
            Logback.logger.info("{}.{}|Try: Return Token to Invoker",this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
            return Response.status(Response.Status.OK).header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken).build();
        }catch (Exception e) {
            String UUID= java.util.UUID.randomUUID().toString();
            Logback.logger.error("{}.{}|UUID:{} - Exception: {}", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(),UUID, e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.EXPECTATION_FAILED).entity("Your Trace number is"+UUID+e.toString()).build();
        }
    }
}