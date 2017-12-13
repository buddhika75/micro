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
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 *
 * @author nilukagun
 */
@Entity
public class ItemStock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Item item;
    @ManyToOne
    private Institute institute;
    private Double retailRate;
    private Double purchaseRate;
    private Double stock;

    @Transient
    Double transPurchaseValue;
    Double transSaleValue;
    Double transProfit;

    public Double getTransPurchaseValue() {
        if (stock == null || purchaseRate == null) {
            transPurchaseValue = 0.0;
        } else {
            transPurchaseValue = stock * purchaseRate;
        }
        return transPurchaseValue;
    }

    public Double getTransSaleValue() {
        if (stock == null || retailRate == null) {
            transSaleValue = 0.0;
        } else {
            transSaleValue = stock * retailRate;
        }
        return transSaleValue;
    }

    public Double getTransProfit() {
        transProfit= getTransSaleValue()-getTransPurchaseValue();
        return transProfit;
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
        if (!(object instanceof ItemStock)) {
            return false;
        }
        ItemStock other = (ItemStock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sss.wc.entity.ItemStock[ id=" + id + " ]";
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public Double getRetailRate() {
        return retailRate;
    }

    public void setRetailRate(Double retailRate) {
        this.retailRate = retailRate;
    }

    public Double getStock() {
        if (stock == null) {
            stock = 0.0;
        }
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(Double purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

}
