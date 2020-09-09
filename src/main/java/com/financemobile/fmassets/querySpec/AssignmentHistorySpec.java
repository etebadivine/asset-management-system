package com.financemobile.fmassets.querySpec;


import com.financemobile.fmassets.model.AssignmentHistory;
import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Or({
        @Spec(path = "asset.id", params="asset", spec = Equal.class),
        @Spec(path = "user.id", params="user", spec = Equal.class),
        @Spec(path = "user.email", params="user", spec = Equal.class),
        @Spec(path = "dateCreated", params={"created_after", "created_before"}, spec = DateBetween.class, config="yyyy-MM-dd")
})

public interface AssignmentHistorySpec extends Specification<AssignmentHistory> {
}