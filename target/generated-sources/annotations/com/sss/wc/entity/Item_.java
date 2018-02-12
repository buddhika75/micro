package com.sss.wc.entity;

import com.sss.wc.enums.Agency;
import com.sss.wc.enums.ItemType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-12T13:29:54")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, Double> wholesaleRateMin;
    public static volatile SingularAttribute<Item, ItemType> itemType;
    public static volatile SingularAttribute<Item, String> code;
    public static volatile SingularAttribute<Item, Double> wholesaleRate;
    public static volatile SingularAttribute<Item, Agency> agency;
    public static volatile SingularAttribute<Item, Double> retailerRateMin;
    public static volatile SingularAttribute<Item, String> description;
    public static volatile SingularAttribute<Item, Double> maxQtySaleForCustomer;
    public static volatile SingularAttribute<Item, Double> dealerRateMax;
    public static volatile SingularAttribute<Item, Double> dealerRate;
    public static volatile SingularAttribute<Item, Double> wholesaleRateMax;
    public static volatile SingularAttribute<Item, Double> maxQtySaleForDealer;
    public static volatile SingularAttribute<Item, String> name;
    public static volatile SingularAttribute<Item, Long> id;
    public static volatile SingularAttribute<Item, Double> retailRate;
    public static volatile SingularAttribute<Item, Double> dealerRateMin;
    public static volatile SingularAttribute<Item, Double> retailerRate;
    public static volatile SingularAttribute<Item, Double> retailerRateMax;

}