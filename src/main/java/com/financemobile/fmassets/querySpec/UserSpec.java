package com.financemobile.fmassets.querySpec;


import com.financemobile.fmassets.model.User;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;


@Or({
        @Spec(path = "firstName", params="first_name", spec = Equal.class),
        @Spec(path = "lastName", params="last_name", spec = Equal.class),
        @Spec(path = "status", params="status", spec = Equal.class),
        @Spec(path = "email", params="email", spec = Equal.class),
        @Spec(path = "phone", params="phone", spec = Equal.class),
        @Spec(path = "department.name", params="department", spec = Equal.class),
        @Spec(path = "role.name", params="role", spec = Equal.class)
})

public interface UserSpec extends Specification<User> { }
