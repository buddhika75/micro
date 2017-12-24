/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.entity;

import com.sss.wc.enums.PaymentMethod;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author nilukagun
 */
@Entity
public class Payment implements Serializable {

    @OneToOne(mappedBy = "payment")
    private BillItem billItem;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    PaymentMethod paymentMethod;
    @ManyToOne
    Institute bank;
    String branch;
    String chequeNo;
    String cardNumber;
    String referenceNo;
    Boolean chequeRealized;
    Double paymentValue;
    Boolean paying;
    Boolean receiving;
    @ManyToOne
    Institute fromInstitute;
    @ManyToOne
    Institute toInstitute;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date chequeDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date realizedDate;
    boolean completed = false;
    @ManyToOne
    private Bill bill;
    @Transient
    boolean needBank;
    @Transient
    boolean needChequeNo;
    @Transient
    boolean needCreditCardNo;
    @Transient
    boolean needEnterValue;

    public Boolean getPaying() {
        return paying;
    }

    public void setPaying(Boolean paying) {
        this.paying = paying;
    }

    public Boolean getReceiving() {
        return receiving;
    }

    public void setReceiving(Boolean receiving) {
        this.receiving = receiving;
    }

    public BillItem getBillItem() {
        return billItem;
    }

    public void setBillItem(BillItem billItem) {
        this.billItem = billItem;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isNeedBank() {
        switch (paymentMethod) {
            case Bank_Transfer:
            case Credit_Card:
            case Cheque:
                needBank = true;
                break;
            default:
                needBank = false;
        }
        return needBank;
    }

    public boolean isNeedChequeNo() {
        switch (paymentMethod) {
            case Cheque:
                needChequeNo = true;
                break;
            default:
                needChequeNo = false;
        }
        return needChequeNo;
    }

    public boolean isNeedCreditCardNo() {
        switch (paymentMethod) {
            case Credit_Card:
                needCreditCardNo = true;
                break;
            default:
                needCreditCardNo = false;
        }
        return needCreditCardNo;
    }

    public boolean isNeedEnterValue() {
        switch (paymentMethod) {
            case Credit:
                needEnterValue = false;
                break;
            default:
                needEnterValue = true;
        }
        return needEnterValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Institute getBank() {
        return bank;
    }

    public void setBank(Institute bank) {
        this.bank = bank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Boolean getChequeRealized() {
        return chequeRealized;
    }

    public void setChequeRealized(Boolean chequeRealized) {
        this.chequeRealized = chequeRealized;
    }

    public Double getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(Double paymentValue) {
        this.paymentValue = paymentValue;
        if (bill != null) {
            System.out.println("going to get bill cals");
            bill.calculateTotals();
        } else {
            System.out.println("bill is null");
        }
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

    public Date getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(Date chequeDate) {
        this.chequeDate = chequeDate;
    }

    public Date getRealizedDate() {
        return realizedDate;
    }

    public void setRealizedDate(Date realizedDate) {
        this.realizedDate = realizedDate;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
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
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sss.wc.entity.Payment[ id=" + id + " ]";
    }

}
