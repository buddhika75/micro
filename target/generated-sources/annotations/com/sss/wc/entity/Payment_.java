package com.sss.wc.entity;

import com.sss.wc.entity.Bill;
import com.sss.wc.entity.BillItem;
import com.sss.wc.entity.Institute;
import com.sss.wc.enums.PaymentMethod;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-10T02:21:40")
@StaticMetamodel(Payment.class)
public class Payment_ { 

    public static volatile SingularAttribute<Payment, Institute> toInstitute;
    public static volatile SingularAttribute<Payment, String> referenceNo;
    public static volatile SingularAttribute<Payment, String> chequeNo;
    public static volatile SingularAttribute<Payment, Bill> bill;
    public static volatile SingularAttribute<Payment, Institute> fromInstitute;
    public static volatile SingularAttribute<Payment, Date> realizedDate;
    public static volatile SingularAttribute<Payment, Boolean> completed;
    public static volatile SingularAttribute<Payment, String> branch;
    public static volatile SingularAttribute<Payment, Date> chequeDate;
    public static volatile SingularAttribute<Payment, Boolean> receiving;
    public static volatile SingularAttribute<Payment, Institute> bank;
    public static volatile SingularAttribute<Payment, Double> paymentValue;
    public static volatile SingularAttribute<Payment, Boolean> paying;
    public static volatile SingularAttribute<Payment, Boolean> chequeRealized;
    public static volatile SingularAttribute<Payment, PaymentMethod> paymentMethod;
    public static volatile SingularAttribute<Payment, BillItem> billItem;
    public static volatile SingularAttribute<Payment, Long> id;
    public static volatile SingularAttribute<Payment, String> cardNumber;

}