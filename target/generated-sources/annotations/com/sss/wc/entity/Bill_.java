package com.sss.wc.entity;

import com.sss.wc.entity.Bill;
import com.sss.wc.entity.BillItem;
import com.sss.wc.entity.Institute;
import com.sss.wc.entity.Payment;
import com.sss.wc.entity.WebUser;
import com.sss.wc.enums.Agency;
import com.sss.wc.enums.BillCategory;
import com.sss.wc.enums.BillType;
import com.sss.wc.enums.PaymentMethod;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-17T08:09:23")
@StaticMetamodel(Bill.class)
public class Bill_ { 

    public static volatile SingularAttribute<Bill, WebUser> billedUser;
    public static volatile SingularAttribute<Bill, Institute> toInstitute;
    public static volatile SingularAttribute<Bill, Double> billSaleValue;
    public static volatile SingularAttribute<Bill, WebUser> salesRep;
    public static volatile SingularAttribute<Bill, Double> billPurchaseValue;
    public static volatile SingularAttribute<Bill, Double> totalCreditCardValue;
    public static volatile SingularAttribute<Bill, Bill> loadingBill;
    public static volatile ListAttribute<Bill, Payment> payments;
    public static volatile SingularAttribute<Bill, Long> meterReading;
    public static volatile SingularAttribute<Bill, Double> settledValue;
    public static volatile ListAttribute<Bill, BillItem> billItems;
    public static volatile SingularAttribute<Bill, Bill> billedBillForCancelledBill;
    public static volatile SingularAttribute<Bill, Bill> loadingBillForCustomerBills;
    public static volatile SingularAttribute<Bill, Double> billProfitValue;
    public static volatile SingularAttribute<Bill, Double> totalChequeValue;
    public static volatile SingularAttribute<Bill, Institute> vehicle;
    public static volatile ListAttribute<Bill, Bill> returnedBills;
    public static volatile SingularAttribute<Bill, Double> toSettleValue;
    public static volatile SingularAttribute<Bill, Long> id;
    public static volatile SingularAttribute<Bill, Bill> billedBillForReturnedBills;
    public static volatile SingularAttribute<Bill, Double> initialChequeValue;
    public static volatile SingularAttribute<Bill, Double> totalCashValue;
    public static volatile SingularAttribute<Bill, Double> initialCashValue;
    public static volatile SingularAttribute<Bill, BillCategory> billCategory;
    public static volatile SingularAttribute<Bill, Double> billDiscount;
    public static volatile SingularAttribute<Bill, Double> initialCreditValue;
    public static volatile SingularAttribute<Bill, Agency> agency;
    public static volatile SingularAttribute<Bill, Boolean> settled;
    public static volatile SingularAttribute<Bill, BillType> billType;
    public static volatile SingularAttribute<Bill, Date> billDate;
    public static volatile SingularAttribute<Bill, Institute> fromInstitute;
    public static volatile SingularAttribute<Bill, Double> initialCreditCardValue;
    public static volatile SingularAttribute<Bill, Double> billNetTotal;
    public static volatile SingularAttribute<Bill, Date> billAt;
    public static volatile SingularAttribute<Bill, Long> billTotalQuantity;
    public static volatile SingularAttribute<Bill, Date> billTime;
    public static volatile SingularAttribute<Bill, Double> billTotal;
    public static volatile SingularAttribute<Bill, Institute> route;
    public static volatile SingularAttribute<Bill, Double> totalCreditValue;
    public static volatile SingularAttribute<Bill, Double> totalBankTransferValue;
    public static volatile SingularAttribute<Bill, Double> initialBankTransferValue;
    public static volatile SingularAttribute<Bill, Bill> unloadingBill;
    public static volatile SingularAttribute<Bill, Bill> cancelledBill;
    public static volatile SingularAttribute<Bill, PaymentMethod> paymentMethod;
    public static volatile SingularAttribute<Bill, Boolean> cancelled;
    public static volatile ListAttribute<Bill, Bill> loadingBills;
    public static volatile SingularAttribute<Bill, Boolean> returned;

}