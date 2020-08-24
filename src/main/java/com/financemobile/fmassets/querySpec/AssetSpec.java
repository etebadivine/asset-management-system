package com.financemobile.fmassets.querySpec;


import com.financemobile.fmassets.model.Asset;
import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Or({
        @Spec(path = "name", params="portfolio_id", spec = Equal.class),
        @Spec(path = "status", params="portfolio_id", spec = Equal.class),
        @Spec(path = "category.name", params="portfolio_id", spec = Equal.class),
        @Spec(path = "location.name", params="portfolio_id", spec = Equal.class),
        @Spec(path = "department.name", params="portfolio_id", spec = Equal.class),
        @Spec(path = "user.name", params="portfolio_id", spec = Equal.class),
        @Spec(path = "supplier.name", params="portfolio_id", spec = Equal.class),
        @Spec(path = "dateCreated", params={"created_after", "created_before"}, spec = DateBetween.class, config="yyyy-MM-dd")

})
public interface AssetSpec extends Specification<Asset> {

}
