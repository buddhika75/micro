package com.sss.wc.entity;

import com.sss.wc.entity.Bill;
import com.sss.wc.entity.Item;
import com.sss.wc.entity.ItemStock;
import com.sss.wc.entity.Payment;
import com.sss.wc.enums.BillItemType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-12T01:33:45")
@StaticMetamodel(BillItem.class)
public class BillItem_ { 

    public static volatile SingularAttribute<BillItem, Double> expectedQuentity;
    public static volatile SingularAttribute<BillItem, Item> item;
    public static volatile SingularAttribute<BillItem, Double> loadingQuentity;
    public static volatile SingularAttribute<BillItem, Double> unloadingQuentity;
    public static volatile SingularAttribute<BillItem, Double> netValue;
    public static volatile SingularAttribute<BillItem, BillItemType> billItemType;
    public static volatile SingularAttribute<BillItem, Bill> bill;
    public static volatile SingularAttribute<BillItem, ItemStock> itemStock;
    public static volatile SingularAttribute<BillItem, Double> differanceQuentity;
    public static volatile SingularAttribute<BillItem, Double> rate;
    public static volatile SingularAttribute<BillItem, Integer> serial;
    public static volatile SingularAttribute<BillItem, Payment> payment;
    public static volatile SingularAttribute<BillItem, Long> id;
    public static volatile SingularAttribute<BillItem, Double> returnQuentity;
    public static volatile SingularAttribute<BillItem, Double> damagedQuentity;
    public static volatile SingularAttribute<BillItem, Double> quentity;
    public static volatile SingularAttribute<BillItem, Double> freeQuentity;
    public static volatile SingularAttribute<BillItem, Double> retailRate;
    public static volatile SingularAttribute<BillItem, Double> soldQuentity;

}