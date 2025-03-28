package com.example.miniproject.controller.commute;

import com.example.miniproject.dto.request.commute.ClockInRequest;
import com.example.miniproject.dto.request.commute.ClockOutRequest;
import com.example.miniproject.dto.request.commute.WorkTimeRequest;
import com.example.miniproject.dto.response.commute.WorkTimeResponse;
import com.example.miniproject.service.commute.CommuteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class CommuteController {
    private final CommuteService commuteService;

    @PostMapping("/api/v1/commute/clock-in")
    public void clockIn(@RequestBody ClockInRequest request) {
        commuteService.clockIn(request);
    }

    @PatchMapping("/api/v1/commute/clock-out")
    public void clockOut(@RequestBody ClockOutRequest request) {
        commuteService.clockOut(request.getMemberId());
    }

    @GetMapping("/api/v1/member/work-time")
    public WorkTimeResponse getWorkTime(WorkTimeRequest request) {
        System.out.println("request: " + request.getMemberId() + request.getYearMonth());
        return commuteService.getWorkTime(request);
    }
}
