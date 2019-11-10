package com.sss.wc.entity;

import com.sss.wc.entity.Item;
import com.sss.wc.enums.UserType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-09T22:55:22")
@StaticMetamodel(WebUser.class)
public class WebUser_ { 

    public static volatile SingularAttribute<WebUser, Boolean> executiveOfficer;
    public static volatile SingularAttribute<WebUser, String> password;
    public static volatile SingularAttribute<WebUser, String> sname;
    public static volatile SingularAttribute<WebUser, String> name;
    public static volatile SingularAttribute<WebUser, String> tname;
    public static volatile SingularAttribute<WebUser, Boolean> active;
    public static volatile SingularAttribute<WebUser, Long> id;
    public static volatile SingularAttribute<WebUser, Item> position;
    public static volatile SingularAttribute<WebUser, UserType> userType;
    public static volatile SingularAttribute<WebUser, String> userName;
    public static volatile SingularAttribute<WebUser, String> email;

}