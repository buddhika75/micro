package com.sss.wc.entity;

import com.sss.wc.entity.Item;
import com.sss.wc.enums.ItemType;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-22T06:04:15")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, Item> referenceToItem;
    public static volatile SingularAttribute<Item, ItemType> itemType;
    public static volatile SingularAttribute<Item, String> code;
    public static volatile SingularAttribute<Item, String> contents;
    public static volatile SingularAttribute<Item, Item> referenceFromItem;
    public static volatile SingularAttribute<Item, String> name;
    public static volatile SingularAttribute<Item, Item> parentItem;
    public static volatile SingularAttribute<Item, Long> id;
    public static volatile ListAttribute<Item, Item> childItems;

}