package com.sss.wc.entity;

import com.sss.wc.entity.Institute;
import com.sss.wc.entity.Item;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-08T03:39:52")
@StaticMetamodel(ItemStock.class)
public class ItemStock_ { 

    public static volatile SingularAttribute<ItemStock, Item> item;
    public static volatile SingularAttribute<ItemStock, Double> purchaseRate;
    public static volatile SingularAttribute<ItemStock, Double> transSaleValue;
    public static volatile SingularAttribute<ItemStock, Double> transProfit;
    public static volatile SingularAttribute<ItemStock, Institute> institute;
    public static volatile SingularAttribute<ItemStock, Long> id;
    public static volatile SingularAttribute<ItemStock, Double> stock;
    public static volatile SingularAttribute<ItemStock, Double> retailRate;

}