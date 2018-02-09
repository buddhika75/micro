/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.entity;

import com.sss.wc.enums.Agency;
import com.sss.wc.enums.BillCategory;
import com.sss.wc.enums.BillType;
import com.sss.wc.enums.PaymentMethod;
import com.sun.javafx.scene.control.SelectedCellsMap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author nilukagun
 */
@Entity
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    BillCategory billCategory;
    BillType billType;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date billDate;
    @Temporal(javax.persistence.TemporalType.TIME)
    Date billTime;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date billAt;
    @ManyToOne
    WebUser billedUser;
    Double billTotal;
    Double billDiscount;
    Double billNetTotal;

    Double billPurchaseValue;
    Double billSaleValue;
    Double billProfitValue;

    Long billTotalQuantity;
    @ManyToOne
    Institute fromInstitute;
    @ManyToOne
    Institute toInstitute;
    @Enumerated
    PaymentMethod paymentMethod;
    Double initialCashValue;
    Double initialChequeValue;
    Double initialCreditCardValue;
    Double initialCreditValue;
    Double initialBankTransferValue;
    Double totalCashValue;
    Double totalChequeValue;
    Double totalCreditCardValue;
    Double totalCreditValue;
    Double totalBankTransferValue;
    Boolean settled;
    Double settledValue;
    Double toSettleValue;
    Boolean cancelled;

    @OneToOne
    Bill cancelledBill;
    Boolean returned;
    @OneToMany(mappedBy = "billedBillForReturnedBills", cascade = CascadeType.ALL)
    List<Bill> returnedBills;
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    List<Payment> payments;

    @OneToOne(mappedBy = "cancelledBill", cascade = CascadeType.ALL)
    private Bill billedBillForCancelledBill;

    @ManyToOne(cascade = CascadeType.ALL)
    private Bill billedBillForReturnedBills;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<BillItem> billItems;

    @OneToOne
    Bill loadingBill;
    @OneToOne(mappedBy = "loadingBill")
    Bill unloadingBill;
    @ManyToOne
    Bill loadingBillForCustomerBills;
    @OneToMany(mappedBy = "loadingBillForCustomerBills")
    List<Bill> loadingBills;

    @ManyToOne
    WebUser salesRep;

    @ManyToOne
    Institute route;
    @ManyToOne
    Institute vehicle;
    @Enumerated(EnumType.STRING)
    Agency agency;

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    
    
    
    public Bill getBilledBillForReturnedBills() {
        return billedBillForReturnedBills;
    }

    public void setBilledBillForReturnedBills(Bill billedBillForReturnedBills) {
        this.billedBillForReturnedBills = billedBillForReturnedBills;
    }

    public Bill getLoadingBill() {
        return loadingBill;
    }

    public void setLoadingBill(Bill loadingBill) {
        this.loadingBill = loadingBill;
    }

    public Bill getUnloadingBill() {
        return unloadingBill;
    }

    public void setUnloadingBill(Bill unloadingBill) {
        this.unloadingBill = unloadingBill;
    }

    public Bill getLoadingBillForCustomerBills() {
        return loadingBillForCustomerBills;
    }

    public void setLoadingBillForCustomerBills(Bill loadingBillForCustomerBills) {
        this.loadingBillForCustomerBills = loadingBillForCustomerBills;
    }

    public List<Bill> getLoadingBills() {
        return loadingBills;
    }

    public void setLoadingBills(List<Bill> loadingBills) {
        this.loadingBills = loadingBills;
    }

    public WebUser getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(WebUser salesRep) {
        this.salesRep = salesRep;
    }

    public void calculateTotals() {
        switch (billCategory) {
            case Customer_Sale:
                calculateTotalsForCustomerBills();
                break;
            case Good_Receive:
                calculateTotalsForGoodReceiveBills();
                break;
            default:
                calculateTotalsForOtherBills();
        }
    }

    public void calculateTotalsForOtherBills() {
        System.out.println("calculateTotalForCustomerBills");
        double tot = 0.0;
        long c = 0;
        for (BillItem bi : getBillItems()) {
            tot += bi.getNetValue();
            c += bi.getQuentity() + bi.getFreeQuentity();
        }
        setBillNetTotal(tot);
        setBillTotal(tot);
        setBillDiscount(0.0);
        setBillTotalQuantity(c);
        double paidTotal = 0;
        double maxPaymentValue = 0.0;
        System.out.println("getPayments() = " + getPayments());
        for (Payment p : getPayments()) {
            switch (p.paymentMethod) {
                case Cash:
                case Credit_Card:
                    paidTotal += p.getPaymentValue();
                    break;
            }
            if (p.paymentValue > maxPaymentValue) {
                setPaymentMethod(p.getPaymentMethod());
            }
        }
        System.out.println("tot = " + tot);
        System.out.println("paidTotal = " + paidTotal);
        for (Payment p : getPayments()) {
            System.out.println("paymentMethod = " + p.getPaymentMethod());
            if (p.getPaymentMethod().equals(PaymentMethod.Credit)) {
                System.out.println("p = " + p);
                p.setPaymentValue(tot - paidTotal);
                System.out.println("p.getPaymentValue() = " + p.getPaymentValue());
            }
        }

    }

    public void calculateTotalsForGoodReceiveBills() {
        System.out.println("calculateTotalForGoodReceiveBills");
        double tot = 0.0;
        double totSale = 0.0;
        double totPurchase = 0.0;
        double totProfit = 0.0;
        long c = 0;
        for (BillItem bi : getBillItems()) {
            double q = 0.0;
            double pr = 0.0;
            double rr = 0.0;
            double rq = 0.0;
            double fq = 0.0;
            double nv = 0.0;
            if (bi.getQuentity() != null) {
                q = bi.getQuentity();
            }
            if (bi.getRate() != null) {
                pr = bi.getRate();
            }
            if (bi.getRetailRate() != null) {
                rr = bi.getRetailRate();
            }
            if (bi.getReturnQuentity() != null) {
                rq = bi.getReturnQuentity();
            }
            if (bi.getFreeQuentity() != null) {
                fq = bi.getFreeQuentity();
            }
            if (bi.getNetValue() != null) {
                nv = bi.getNetValue();
            }
            tot += nv;
            c += q + fq - rq;
            totSale += rr * (q + fq - rq);
            totPurchase += pr * (q - rq);
        }
        totProfit = totSale - totPurchase;

        billTotal = tot;
        billSaleValue = totSale;
        billPurchaseValue = totPurchase;

        if (getBillDiscount() != null) {
            billNetTotal = tot - getBillDiscount();
            billProfitValue = totProfit + getBillDiscount();
        } else {
            billNetTotal = tot;
            billProfitValue = totProfit;
        }

        billTotalQuantity = c;

        double nonCreditTotal = 0;
        double temSettledValue = 0.0;
        double maxPaymentValue = 0.0;
        System.out.println("getPayments() = " + getPayments());
        for (Payment p : getPayments()) {
            switch (p.paymentMethod) {
                case Cash:
                    if (p.getPaymentValue() != null) {
                        if (initialCashValue == null) {
                            initialCashValue = 0.0;
                        }
                        initialCashValue = initialCashValue + p.getPaymentValue();
                        temSettledValue += p.getPaymentValue();
                        nonCreditTotal += p.getPaymentValue();
                    }
                    break;
                case Credit_Card:
                    if (p.getPaymentValue() != null) {
                        if (initialCreditCardValue == null) {
                            initialCreditCardValue = 0.0;
                        }
                        initialCreditCardValue += p.paymentValue;
                        temSettledValue += p.getPaymentValue();
                        nonCreditTotal += p.getPaymentValue();
                    }
                    break;
                case Cheque:
                    if (p.getPaymentValue() != null) {
                        if (initialChequeValue == null) {
                            initialChequeValue = 0.0;
                        }
                        initialChequeValue += p.getPaymentValue();
                        nonCreditTotal += p.getPaymentValue();
                    }
                    break;
            }
            if (p.getPaymentValue() != null && p.paymentValue > maxPaymentValue) {
                setPaymentMethod(p.getPaymentMethod());
                maxPaymentValue = p.paymentValue;
            }
        }
        System.out.println("tot = " + tot);
        System.out.println("paidTotal = " + nonCreditTotal);
        for (Payment p : getPayments()) {
            System.out.println("paymentMethod = " + p.getPaymentMethod());
            if (p.getPaymentMethod().equals(PaymentMethod.Credit)) {
                System.out.println("p = " + p);
                p.paymentValue = tot - nonCreditTotal;
                System.out.println("p.getPaymentValue() = " + p.getPaymentValue());
            }
        }
        settledValue = temSettledValue;
        toSettleValue = Math.abs(billNetTotal) - Math.abs(settledValue);
        if (toSettleValue < 10) {
            settled = true;
        }

    }

    public void calculateTotalsForCustomerBills() {
        System.out.println("calculateTotalForCustomerBills");
        double tot = 0.0;
        long c = 0;
        for (BillItem bi : getBillItems()) {
            double temTot = 0.0;
            double temQty = 0.0;
            double temFreeQty = 0.0;
            double temReturnQty = 0.0;
            if (bi.getNetValue() != null) {
                temTot = bi.getNetValue();
            }
            if (bi.getFreeQuentity() != null) {
                temFreeQty = bi.getFreeQuentity();
            }
            if (bi.getQuentity() != null) {
                temQty = bi.getQuentity();
            }
            if (bi.getReturnQuentity() != null) {
                temReturnQty = bi.getReturnQuentity();
            }
            tot += temTot;
            c += temQty + temFreeQty - temReturnQty;
        }
        setBillNetTotal(tot);
        setBillTotal(tot);
        setBillTotalQuantity(c);
        double paidTotal = 0;
        double maxPaymentValue = 0.0;
        System.out.println("getPayments() = " + getPayments());
        for (Payment p : getPayments()) {
            switch (p.paymentMethod) {
                case Cash:
                case Credit_Card:
                    paidTotal += p.getPaymentValue();
                    break;
            }
            if (p.paymentValue > maxPaymentValue) {
                setPaymentMethod(p.getPaymentMethod());
            }
        }
        System.out.println("tot = " + tot);
        System.out.println("paidTotal = " + paidTotal);
        for (Payment p : getPayments()) {
            System.out.println("paymentMethod = " + p.getPaymentMethod());
            if (p.getPaymentMethod().equals(PaymentMethod.Credit)) {
                System.out.println("p = " + p);
                p.setPaymentValue(tot - paidTotal);
                System.out.println("p.getPaymentValue() = " + p.getPaymentValue());
            }
        }

    }

    public Long getBillTotalQuantity() {
        return billTotalQuantity;
    }

    public void setBillTotalQuantity(Long billTotalQuantity) {
        this.billTotalQuantity = billTotalQuantity;
    }

    public Institute getRoute() {
        return route;
    }

    public void setRoute(Institute route) {
        this.route = route;
    }

    public Institute getVehicle() {
        return vehicle;
    }

    public void setVehicle(Institute vehicle) {
        this.vehicle = vehicle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BillCategory getBillCategory() {
        return billCategory;
    }

    public void setBillCategory(BillCategory billCategory) {
        this.billCategory = billCategory;
    }

    public BillType getBillType() {
        return billType;
    }

    public void setBillType(BillType billType) {
        this.billType = billType;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public Date getBillAt() {
        return billAt;
    }

    public void setBillAt(Date billAt) {
        this.billAt = billAt;
    }

    public WebUser getBilledUser() {
        return billedUser;
    }

    public void setBilledUser(WebUser billedUser) {
        this.billedUser = billedUser;
    }

    public Double getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(Double billTotal) {
        this.billTotal = billTotal;
    }

    public Double getBillDiscount() {
        return billDiscount;
    }

    public void setBillDiscount(Double billDiscount) {
        this.billDiscount = billDiscount;
        calculateTotals();
    }

    public Double getBillNetTotal() {
//        calculateTotalsForCustomerBills();
        return billNetTotal;
    }

    public void setBillNetTotal(Double billNetTotal) {
        this.billNetTotal = billNetTotal;
    }

    public Institute getFromInstitute() {
        return fromInstitute;
    }

    public void setFromInstitute(Institute fromInstitute) {
        this.fromInstitute = fromInstitute;
    }

    public Institute getToInstitute() {
        return toInstitute;
    }

    public void setToInstitute(Institute toInstitute) {
        this.toInstitute = toInstitute;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getInitialCashValue() {
        return initialCashValue;
    }

    public void setInitialCashValue(Double initialCashValue) {
        this.initialCashValue = initialCashValue;
    }

    public Double getInitialChequeValue() {
        return initialChequeValue;
    }

    public void setInitialChequeValue(Double initialChequeValue) {
        this.initialChequeValue = initialChequeValue;
    }

    public Double getInitialCreditCardValue() {
        return initialCreditCardValue;
    }

    public void setInitialCreditCardValue(Double initialCreditCardValue) {
        this.initialCreditCardValue = initialCreditCardValue;
    }

    public Double getInitialCreditValue() {
        return initialCreditValue;
    }

    public void setInitialCreditValue(Double initialCreditValue) {
        this.initialCreditValue = initialCreditValue;
    }

    public Double getInitialBankTransferValue() {
        return initialBankTransferValue;
    }

    public void setInitialBankTransferValue(Double initialBankTransferValue) {
        this.initialBankTransferValue = initialBankTransferValue;
    }

    public Double getTotalCashValue() {
        return totalCashValue;
    }

    public void setTotalCashValue(Double totalCashValue) {
        this.totalCashValue = totalCashValue;
    }

    public Double getTotalChequeValue() {
        return totalChequeValue;
    }

    public void setTotalChequeValue(Double totalChequeValue) {
        this.totalChequeValue = totalChequeValue;
    }

    public Double getTotalCreditCardValue() {
        return totalCreditCardValue;
    }

    public void setTotalCreditCardValue(Double totalCreditCardValue) {
        this.totalCreditCardValue = totalCreditCardValue;
    }

    public Double getTotalCreditValue() {
        return totalCreditValue;
    }

    public void setTotalCreditValue(Double totalCreditValue) {
        this.totalCreditValue = totalCreditValue;
    }

    public Double getTotalBankTransferValue() {
        return totalBankTransferValue;
    }

    public void setTotalBankTransferValue(Double totalBankTransferValue) {
        this.totalBankTransferValue = totalBankTransferValue;
    }

    public Boolean getSettled() {
        return settled;
    }

    public void setSettled(Boolean settled) {
        this.settled = settled;
    }

    public Double getSettledValue() {
        return settledValue;
    }

    public void setSettledValue(Double settledValue) {
        this.settledValue = settledValue;
    }

    public Double getToSettleValue() {
        return toSettleValue;
    }

    public void setToSettleValue(Double toSettleValue) {
        this.toSettleValue = toSettleValue;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Bill getCancelledBill() {
        return cancelledBill;
    }

    public void setCancelledBill(Bill cancelledBill) {
        this.cancelledBill = cancelledBill;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    public List<Bill> getReturnedBills() {
        return returnedBills;
    }

    public void setReturnedBills(List<Bill> returnedBills) {
        this.returnedBills = returnedBills;
    }

    public List<Payment> getPayments() {
        if (payments == null) {
            payments = new ArrayList<Payment>();
        }
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public Bill getBilledBillForCancelledBill() {
        return billedBillForCancelledBill;
    }

    public void setBilledBillForCancelledBill(Bill billedBillForCancelledBill) {
        this.billedBillForCancelledBill = billedBillForCancelledBill;
    }

    public List<BillItem> getBillItems() {
        if (billItems == null) {
            billItems = new ArrayList<BillItem>();
        }
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }

    public Double getBillPurchaseValue() {
        return billPurchaseValue;
    }

    public void setBillPurchaseValue(Double billPurchaseValue) {
        this.billPurchaseValue = billPurchaseValue;
    }

    public Double getBillSaleValue() {
        return billSaleValue;
    }

    public void setBillSaleValue(Double billSaleValue) {
        this.billSaleValue = billSaleValue;
    }

    public Double getBillProfitValue() {
        return billProfitValue;
    }

    public void setBillProfitValue(Double billProfitValue) {
        this.billProfitValue = billProfitValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bill)) {
            return false;
        }
        Bill other = (Bill) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sss.wc.entity.Bill[ id=" + id + " ]";
    }

}
