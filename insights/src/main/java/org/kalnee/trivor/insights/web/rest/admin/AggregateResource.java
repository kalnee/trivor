package org.kalnee.trivor.insights.web.rest.admin;

import com.codahale.metrics.annotation.Timed;
import org.kalnee.trivor.insights.domain.AggregateRequest;
import org.kalnee.trivor.insights.service.InsightAggregatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.CollectionUtils.isEmpty;

@RestController
@RequestMapping(value = "/api/admin/aggregate")
public class AggregateResource {

    private final InsightAggregatorService insightAggregatorService;

    public AggregateResource(InsightAggregatorService insightAggregatorService) {
        this.insightAggregatorService = insightAggregatorService;
    }

    @PostMapping
    @Timed
    public ResponseEntity aggregate(@RequestBody(required = false) AggregateRequest request) {
        if (request != null && !isEmpty(request.getImdbIds())) {
            insightAggregatorService.aggregateByImdbIds(request.getImdbIds());
        } else {
            insightAggregatorService.aggregateAllByImdbIds();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/by-year/{year}")
    @Timed
    public ResponseEntity aggregateByYear(@PathVariable("year") Integer year) {
        insightAggregatorService.aggregateByYear(year);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/global")
    @Timed
    public ResponseEntity aggregateGlobal() {
        insightAggregatorService.aggregateGlobal();
        return ResponseEntity.noContent().build();
    }
}
