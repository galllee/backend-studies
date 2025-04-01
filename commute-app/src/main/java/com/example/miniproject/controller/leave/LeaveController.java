package com.example.miniproject.controller.leave;

import com.example.miniproject.domain.leave.LeaveRequest;
import com.example.miniproject.dto.request.leave.CreateLeaveRequest;
import com.example.miniproject.service.leave.LeaveService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class LeaveController {
    private final LeaveService leaveService;

    @PostMapping("/api/v1/leave-request")
    public void leaveRequest(@RequestBody CreateLeaveRequest request) {
        leaveService.leaveRequest(request);
    }

    @GetMapping("/api/v1/leave-request/remaining")
    public int getRemainingLeaves(@RequestParam Long memberId) {
        return leaveService.getRemainingLeaves(memberId);
    }
}
