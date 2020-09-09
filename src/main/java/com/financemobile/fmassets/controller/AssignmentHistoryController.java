package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.model.AssignmentHistory;
import com.financemobile.fmassets.querySpec.AssignmentHistorySpec;
import com.financemobile.fmassets.service.AssignmentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/assignment_history")
@RestController
public class AssignmentHistoryController {

    private AssignmentHistoryService assignmentHistoryService;

    @Autowired
    public AssignmentHistoryController(AssignmentHistoryService assignmentHistoryService) {
        this.assignmentHistoryService = assignmentHistoryService;
    }

    @GetMapping
    public ApiResponse searchAssignmentHistory(AssignmentHistorySpec assignmentHistorySpec, Pageable pageable) {

        List<AssignmentHistory> assignmentHistoryList = assignmentHistoryService.searchAssignmentHistory(assignmentHistorySpec, pageable);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(assignmentHistoryList);
        return response;
    }
}
