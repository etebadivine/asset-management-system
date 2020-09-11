package com.financemobile.fmassets.querySpec;


import com.financemobile.fmassets.model.Asset;
import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;


@Or({
        @Spec(path = "name", params="name", spec = Equal.class),
        @Spec(path = "status", params="status", spec = Equal.class),
        @Spec(path = "category.name", params="category", spec = Equal.class),
        @Spec(path = "location.name", params="location", spec = Equal.class),
        @Spec(path = "department.name", params="department", spec = Equal.class),
        @Spec(path = "supplier.name", params="supplier", spec = Equal.class),
        @Spec(path = "dateCreated", params={"created_after", "created_before"}, spec = DateBetween.class, config="yyyy-MM-dd")

})

public interface AssetSpec extends Specification<Asset> { }
