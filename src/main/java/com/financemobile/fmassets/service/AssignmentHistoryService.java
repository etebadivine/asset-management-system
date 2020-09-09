package com.financemobile.fmassets.service;

import com.financemobile.fmassets.model.Asset;
import com.financemobile.fmassets.model.AssignmentHistory;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.querySpec.AssignmentHistorySpec;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AssignmentHistoryService {

    AssignmentHistory trackAssetAssignment(Asset asset, User user);
    List<AssignmentHistory> searchAssignmentHistory(AssignmentHistorySpec assignmentHistorySpec, Pageable pageable);
}
