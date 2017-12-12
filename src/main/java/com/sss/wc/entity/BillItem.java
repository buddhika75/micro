/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.entity;

import com.sss.wc.enums.BillItemType;
import com.sss.wc.enums.PaymentMethod;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author nilukagun
 */
@Entity
public class BillItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    BillItemType billItemType;
    @ManyToOne
    Bill bill;
    @ManyToOne
    Item item;
    Double quentity;
    Double expectedQuentity;
    Double freeQuentity;
    Double returnQuentity;
    Double soldQuentity;
    Double loadingQuentity;
    Double unloadingQuentity;
    Double damagedQuentity;
    Double differanceQuentity;
    Double rate;
    Double netValue;
    @OneToOne
    Payment payment;
    Integer serial;
    Double retailRate;
    @ManyToOne
    ItemStock itemStock;

    public Double getExpectedQuentity() {
        return expectedQuentity;
    }

    public void setExpectedQuentity(Double expectedQuentity) {
        this.expectedQuentity = expectedQuentity;
    }

    public Double getReturnQuentity() {
        return returnQuentity;
    }

    public void setReturnQuentity(Double returnQuentity) {
        this.returnQuentity = returnQuentity;
    }

    public Double getSoldQuentity() {
        return soldQuentity;
    }

    public void setSoldQuentity(Double soldQuentity) {
        this.soldQuentity = soldQuentity;
    }

    public Double getLoadingQuentity() {
        return loadingQuentity;
    }

    public void setLoadingQuentity(Double loadingQuentity) {
        this.loadingQuentity = loadingQuentity;
    }

    public Double getUnloadingQuentity() {
        return unloadingQuentity;
    }

    public void setUnloadingQuentity(Double unloadingQuentity) {
        this.unloadingQuentity = unloadingQuentity;
    }

    public Double getDamagedQuentity() {
        return damagedQuentity;
    }

    public void setDamagedQuentity(Double damagedQuentity) {
        this.damagedQuentity = damagedQuentity;
    }

    public Double getDifferanceQuentity() {
        return differanceQuentity;
    }

    public void setDifferanceQuentity(Double differanceQuentity) {
        this.differanceQuentity = differanceQuentity;
    }

    
    
    public ItemStock getItemStock() {
        return itemStock;
    }

    public void setItemStock(ItemStock itemStock) {
        this.itemStock = itemStock;
    }

    
    
    public Double getRetailRate() {
        return retailRate;
    }

    public void setRetailRate(Double retailRate) {
        this.retailRate = retailRate;
    }
    

    
    
    public void calculateNetValue() {
        if (quentity == null ) {
            quentity = 0.0;
        }
        if (rate == null) {
            rate = 0.0;
        }
        netValue = rate * quentity;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    
    
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BillItemType getBillItemType() {
        return billItemType;
    }

    public void setBillItemType(BillItemType billItemType) {
        this.billItemType = billItemType;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Double getQuentity() {
        if(quentity==null){
            quentity = 0.0;
        }
        return quentity;
    }

    public void setQuentity(Double quentity) {
        this.quentity = quentity;
    }

    public Double getFreeQuentity() {
        if(freeQuentity==null) {
            freeQuentity =0.0;
        }
        return freeQuentity;
    }

    public void setFreeQuentity(Double freeQuentity) {
        this.freeQuentity = freeQuentity;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        System.out.println("rate = " + rate);
        this.rate = rate;
    }

    public Double getNetValue() {
        calculateNetValue();
        return netValue;
    }

    public void setNetValue(Double netValue) {
        this.netValue = netValue;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
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
        if (!(object instanceof BillItem)) {
            return false;
        }
        BillItem other = (BillItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sss.wc.entity.BillItem[ id=" + id + " ]";
    }

}
