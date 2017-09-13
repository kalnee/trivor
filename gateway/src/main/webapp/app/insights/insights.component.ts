import {Component, OnInit} from '@angular/core';
import {InsightsService} from './insights.service';

@Component({
    selector: 'jhi-insights',
    templateUrl: './insights.component.html',
    styles: []
})
export class InsightsComponent implements OnInit {

    imdbId = 'tt4034228';
    insights: any;

    constructor(private insightsService: InsightsService) {
    }

    ngOnInit() {
        this.insightsService.findByImdbId(this.imdbId).subscribe((data) => this.insights = data);
    }

}
