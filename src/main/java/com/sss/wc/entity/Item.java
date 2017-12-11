/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.entity;

import com.sss.wc.enums.ItemType;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author pdhs-sp
 */
@Entity
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String name;
    String code;
    @Lob
    String description;
    @Enumerated(EnumType.STRING)
    ItemType itemType;
    Double retailRate;
    Double retailerRate;
    Double retailerRateMin;
    Double retailerRateMax;
    Double wholesaleRate;
    Double wholesaleRateMin;
    Double wholesaleRateMax;
    Double dealerRate;
    Double dealerRateMin;
    Double dealerRateMax;
    double maxQtySaleForCustomer;
    double maxQtySaleForDealer;

    public Double getRetailerRate() {
        return retailerRate;
    }

    public void setRetailerRate(Double retailerRate) {
        this.retailerRate = retailerRate;
    }

    public Double getRetailerRateMin() {
        return retailerRateMin;
    }

    public void setRetailerRateMin(Double retailerRateMin) {
        this.retailerRateMin = retailerRateMin;
    }

    public Double getRetailerRateMax() {
        return retailerRateMax;
    }

    public void setRetailerRateMax(Double retailerRateMax) {
        this.retailerRateMax = retailerRateMax;
    }

    
    
    public double getMaxQtySaleForCustomer() {
        return maxQtySaleForCustomer;
    }

    public void setMaxQtySaleForCustomer(double maxQtySaleForCustomer) {
        this.maxQtySaleForCustomer = maxQtySaleForCustomer;
    }

    public double getMaxQtySaleForDealer() {
        return maxQtySaleForDealer;
    }

    public void setMaxQtySaleForDealer(double maxQtySaleForDealer) {
        this.maxQtySaleForDealer = maxQtySaleForDealer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRetailRate() {
        return retailRate;
    }

    public void setRetailRate(Double retailRate) {
        this.retailRate = retailRate;
    }

    public Double getWholesaleRate() {
        return wholesaleRate;
    }

    public void setWholesaleRate(Double wholesaleRate) {
        this.wholesaleRate = wholesaleRate;
    }

    public Double getWholesaleRateMin() {
        return wholesaleRateMin;
    }

    public void setWholesaleRateMin(Double wholesaleRateMin) {
        this.wholesaleRateMin = wholesaleRateMin;
    }

    public Double getWholesaleRateMax() {
        return wholesaleRateMax;
    }

    public void setWholesaleRateMax(Double wholesaleRateMax) {
        this.wholesaleRateMax = wholesaleRateMax;
    }

    public Double getDealerRate() {
        return dealerRate;
    }

    public void setDealerRate(Double dealerRate) {
        this.dealerRate = dealerRate;
    }

    public Double getDealerRateMin() {
        return dealerRateMin;
    }

    public void setDealerRateMin(Double dealerRateMin) {
        this.dealerRateMin = dealerRateMin;
    }

    public Double getDealerRateMax() {
        return dealerRateMax;
    }

    public void setDealerRateMax(Double dealerRateMax) {
        this.dealerRateMax = dealerRateMax;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
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
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.gov.sp.healthdept.td.entity.Item[ id=" + id + " ]";
    }

}
