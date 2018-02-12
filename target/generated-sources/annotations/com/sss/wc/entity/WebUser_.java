package com.sss.wc.entity;

import com.sss.wc.entity.Institute;
import com.sss.wc.entity.Item;
import com.sss.wc.entity.Location;
import com.sss.wc.enums.UserType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-12T13:29:54")
@StaticMetamodel(WebUser.class)
public class WebUser_ { 

    public static volatile SingularAttribute<WebUser, Boolean> executiveOfficer;
    public static volatile SingularAttribute<WebUser, String> tname;
    public static volatile SingularAttribute<WebUser, Boolean> active;
    public static volatile SingularAttribute<WebUser, String> userName;
    public static volatile SingularAttribute<WebUser, String> password;
    public static volatile SingularAttribute<WebUser, String> sname;
    public static volatile SingularAttribute<WebUser, String> name;
    public static volatile SingularAttribute<WebUser, Institute> institute;
    public static volatile SingularAttribute<WebUser, Location> location;
    public static volatile SingularAttribute<WebUser, Long> id;
    public static volatile SingularAttribute<WebUser, Item> position;
    public static volatile SingularAttribute<WebUser, UserType> userType;
    public static volatile SingularAttribute<WebUser, String> email;

}