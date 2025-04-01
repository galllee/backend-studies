package com.example.miniproject.controller.commute;

import com.example.miniproject.dto.request.commute.WorkTimeRequest;
import com.example.miniproject.dto.response.commute.OverTimeResponse;
import com.example.miniproject.dto.response.commute.WorkTimeResponse;
import com.example.miniproject.service.commute.WorkTimeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;

@AllArgsConstructor
@RestController
public class WorkTimeController {
    private final WorkTimeService workTimeService;

    @GetMapping("/api/v1/member/work-time")
    public WorkTimeResponse getWorkTime(WorkTimeRequest request) {
        return workTimeService.getWorkTime(request);
    }

    @GetMapping("/api/v2/member/work-time")
    public WorkTimeResponse getWorkTimeV2(WorkTimeRequest request) {
        return workTimeService.getWorkTimeV2(request);
    }

    @GetMapping("/api/v1/commute/overtime")
    public List<OverTimeResponse> getOvertime(@RequestParam YearMonth yearMonth) {
        return workTimeService.getOvertime(yearMonth);
    }
}
