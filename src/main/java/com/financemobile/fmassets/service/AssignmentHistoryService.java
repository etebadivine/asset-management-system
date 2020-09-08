package com.financemobile.fmassets.service;

import com.financemobile.fmassets.model.Asset;
import com.financemobile.fmassets.model.AssignmentHistory;
import com.financemobile.fmassets.model.User;


public interface AssignmentHistoryService {

    AssignmentHistory trackAssetAssignment(Asset asset, User user);
}
