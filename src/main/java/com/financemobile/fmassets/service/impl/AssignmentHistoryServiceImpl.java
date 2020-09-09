package com.financemobile.fmassets.service.impl;

import com.financemobile.fmassets.model.Asset;
import com.financemobile.fmassets.model.AssignmentHistory;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.querySpec.AssignmentHistorySpec;
import com.financemobile.fmassets.repository.AssignmentHistoryRepository;
import com.financemobile.fmassets.service.AssignmentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AssignmentHistoryServiceImpl implements AssignmentHistoryService {

    @Autowired
    private AssignmentHistoryRepository assignmentHistoryRepository;

    @Override
    public AssignmentHistory trackAssetAssignment(Asset asset, User user) {
        AssignmentHistory assignmentHistory = new AssignmentHistory();
        assignmentHistory.setAsset(asset);
        assignmentHistory.setUser(user);
        return assignmentHistoryRepository.save(assignmentHistory);
    }

    @Override
    public List<AssignmentHistory> searchAssignmentHistory(AssignmentHistorySpec assignmentHistorySpec, Pageable pageable) {

        List<AssignmentHistory> assignmentHistoryList = new ArrayList<>();
        Page<AssignmentHistory> assignmentHistoryPage = assignmentHistoryRepository.findAll(assignmentHistorySpec, pageable);
        if (assignmentHistoryPage.hasContent())
            return assignmentHistoryPage.getContent();
        return assignmentHistoryList;
    }
}
