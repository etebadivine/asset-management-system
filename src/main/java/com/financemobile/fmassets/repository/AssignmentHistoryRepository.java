package com.financemobile.fmassets.repository;

import com.financemobile.fmassets.model.AssignmentHistory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface AssignmentHistoryRepository extends PagingAndSortingRepository<AssignmentHistory,Long>, JpaSpecificationExecutor<AssignmentHistory> {
}
