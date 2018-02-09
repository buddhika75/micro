package com.sss.wc.entity;

import com.sss.wc.entity.Institute;
import com.sss.wc.enums.Agency;
import com.sss.wc.enums.InstituteType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-10T03:27:35")
@StaticMetamodel(Institute.class)
public class Institute_ { 

    public static volatile SingularAttribute<Institute, String> code;
    public static volatile SingularAttribute<Institute, String> address;
    public static volatile SingularAttribute<Institute, String> comments;
    public static volatile SingularAttribute<Institute, Agency> agency;
    public static volatile SingularAttribute<Institute, Double> balance;
    public static volatile SingularAttribute<Institute, String> phone;
    public static volatile SingularAttribute<Institute, String> name;
    public static volatile SingularAttribute<Institute, Long> id;
    public static volatile SingularAttribute<Institute, InstituteType> instituteType;
    public static volatile SingularAttribute<Institute, String> email;
    public static volatile SingularAttribute<Institute, Institute> parentInstitute;

}