package com.financemobile.fmassets.querySpec;


import com.financemobile.fmassets.model.AssignmentHistory;
import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Or({
        @Spec(path = "asset.id", params="asset_id", spec = Equal.class),
        @Spec(path = "user.id", params="user_id", spec = Equal.class),
        @Spec(path = "user.email", params="user_email", spec = Equal.class),
})
public interface AssignmentHistorySpec extends Specification<AssignmentHistory> {
}