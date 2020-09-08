package com.financemobile.fmassets.service.impl;

import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Asset;
import com.financemobile.fmassets.model.AssignmentHistory;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.repository.AssetRepository;
import com.financemobile.fmassets.repository.AssignmentHistoryRepository;
import com.financemobile.fmassets.service.AssignmentHistoryService;
import com.financemobile.fmassets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class AssignmentHistoryServiceImpl implements AssignmentHistoryService {

    @Autowired
    private AssignmentHistoryRepository assignmentHistoryRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private UserService userService;

    @Override
    public AssignmentHistory trackAssetAssignment(Asset asset, User user) {

            AssignmentHistory assignmentHistory = new AssignmentHistory();
            assignmentHistory.setAsset(asset);
            assignmentHistory.setUser(user);
            return assignmentHistoryRepository.save(assignmentHistory);
    }
}
