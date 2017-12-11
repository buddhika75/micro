/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author nilukagun
 */
@Entity
public class ProgramPreferences implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    String customerBillHeader;
    @Lob
    String customerBillFooter;
    @Lob
    String customerPaymentReceiveHeader;
    @Lob
    String customerPaymentReceiveBillFooter;
    @Lob
    String dealerBillHeader;
    @Lob
    String dealerBillFooter;
    @Lob
    String dealerPaymentReceiveHeader;
    @Lob
    String dealerPaymentReceiveBillFooter;
    @Lob
    String goodReceiveBillHeader;
    @Lob
    String goodReceiveBillFooter;

    public String getCustomerBillHeader() {
        return customerBillHeader;
    }

    public void setCustomerBillHeader(String customerBillHeader) {
        this.customerBillHeader = customerBillHeader;
    }

    public String getCustomerBillFooter() {
        return customerBillFooter;
    }

    public void setCustomerBillFooter(String customerBillFooter) {
        this.customerBillFooter = customerBillFooter;
    }

    public String getCustomerPaymentReceiveHeader() {
        return customerPaymentReceiveHeader;
    }

    public void setCustomerPaymentReceiveHeader(String customerPaymentReceiveHeader) {
        this.customerPaymentReceiveHeader = customerPaymentReceiveHeader;
    }

    public String getCustomerPaymentReceiveBillFooter() {
        return customerPaymentReceiveBillFooter;
    }

    public void setCustomerPaymentReceiveBillFooter(String customerPaymentReceiveBillFooter) {
        this.customerPaymentReceiveBillFooter = customerPaymentReceiveBillFooter;
    }

    public String getDealerBillHeader() {
        return dealerBillHeader;
    }

    public void setDealerBillHeader(String dealerBillHeader) {
        this.dealerBillHeader = dealerBillHeader;
    }

    public String getDealerBillFooter() {
        return dealerBillFooter;
    }

    public void setDealerBillFooter(String dealerBillFooter) {
        this.dealerBillFooter = dealerBillFooter;
    }

    public String getDealerPaymentReceiveHeader() {
        return dealerPaymentReceiveHeader;
    }

    public void setDealerPaymentReceiveHeader(String dealerPaymentReceiveHeader) {
        this.dealerPaymentReceiveHeader = dealerPaymentReceiveHeader;
    }

    public String getDealerPaymentReceiveBillFooter() {
        return dealerPaymentReceiveBillFooter;
    }

    public void setDealerPaymentReceiveBillFooter(String dealerPaymentReceiveBillFooter) {
        this.dealerPaymentReceiveBillFooter = dealerPaymentReceiveBillFooter;
    }

    public String getGoodReceiveBillHeader() {
        return goodReceiveBillHeader;
    }

    public void setGoodReceiveBillHeader(String goodReceiveBillHeader) {
        this.goodReceiveBillHeader = goodReceiveBillHeader;
    }

    public String getGoodReceiveBillFooter() {
        return goodReceiveBillFooter;
    }

    public void setGoodReceiveBillFooter(String goodReceiveBillFooter) {
        this.goodReceiveBillFooter = goodReceiveBillFooter;
    }

    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof ProgramPreferences)) {
            return false;
        }
        ProgramPreferences other = (ProgramPreferences) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sss.wc.entity.ProgramPreferences[ id=" + id + " ]";
    }

}
