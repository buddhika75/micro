package com.sss.wc.entity;

import com.sss.wc.entity.WebUser;
import com.sss.wc.enums.Privilege;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-21T13:51:59")
@StaticMetamodel(UserPrivilege.class)
public class UserPrivilege_ { 

    public static volatile SingularAttribute<UserPrivilege, WebUser> webUser;
    public static volatile SingularAttribute<UserPrivilege, Long> id;
    public static volatile SingularAttribute<UserPrivilege, Privilege> privilege;

}