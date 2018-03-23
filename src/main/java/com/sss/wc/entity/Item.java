/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.entity;

import com.sss.wc.enums.ItemType;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 *
 * @author pdhs-sp
 */
@Entity
public class Item implements Serializable {

    @OneToOne(mappedBy = "referenceToItem")
    private Item referenceFromItem;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String name;
    String code;
    @Lob
    String contents;
    @Lob
    String preContents;
    @Lob
    String postContents;

    @Lob
    String primaryTherapy;
    @Lob
    String alternativeTherapy;
    @Lob
    String comments;

    @Enumerated(EnumType.STRING)
    ItemType itemType;

    @ManyToOne
    Item parentItem;
    @OneToMany(mappedBy = "parentItem")
    private List<Item> childItems;

    @OneToOne(cascade = CascadeType.ALL)
    Item referenceToItem;

    Integer orderNo;

    @Lob
    String dose;
    @Lob
    String administration;

    @Transient
    boolean hasGrandChildren;
    @Transient
    boolean hasDetailsTable;

    public Item getReferenceFromItem() {
        return referenceFromItem;
    }

    public void setReferenceFromItem(Item referenceFromItem) {
        this.referenceFromItem = referenceFromItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Item getParentItem() {
        return parentItem;
    }

    public void setParentItem(Item parentItem) {
        this.parentItem = parentItem;
    }

    public List<Item> getChildItems() {
        return childItems;
    }

    public void setChildItems(List<Item> childItems) {
        this.childItems = childItems;
    }

    public Item getReferenceToItem() {
        return referenceToItem;
    }

    public void setReferenceToItem(Item referenceToItem) {
        this.referenceToItem = referenceToItem;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getAdministration() {
        return administration;
    }

    public void setAdministration(String administration) {
        this.administration = administration;
    }

    public String getPreContents() {
        return preContents;
    }

    public void setPreContents(String preContents) {
        this.preContents = preContents;
    }

    public String getPostContents() {
        return postContents;
    }

    public void setPostContents(String postContents) {
        this.postContents = postContents;
    }

    public String getPrimaryTherapy() {
        return primaryTherapy;
    }

    public void setPrimaryTherapy(String primaryTherapy) {
        this.primaryTherapy = primaryTherapy;
    }

    public String getAlternativeTherapy() {
        return alternativeTherapy;
    }

    public void setAlternativeTherapy(String alternativeTherapy) {
        this.alternativeTherapy = alternativeTherapy;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isHasGrandChildren() {
        if (this.getChildItems() == null) {
            return false;
        }
        if (this.getChildItems().isEmpty()) {
            return false;
        }
        for (Item i : this.getChildItems()) {
            if (i.getChildItems() != null) {
                if (!i.getChildItems().isEmpty()) {
                    return true;
                }

            }
        }
        return false;
    }

    public boolean isHasDetailsTable() {
        hasDetailsTable = false;
        if (this.getAlternativeTherapy() != null && !(this.getAlternativeTherapy().trim().equals(""))) {
            hasDetailsTable = true;
        }
        if (this.getPrimaryTherapy() != null && !(this.getPrimaryTherapy().trim().equals(""))) {
            hasDetailsTable = true;
        }
        if (this.getComments() != null && !(this.getComments().trim().equals(""))) {
            hasDetailsTable = true;
        }
        return hasDetailsTable;
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
        return name;
    }

}
