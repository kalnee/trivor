import {Component, OnInit} from '@angular/core';
import {InsightsService} from '../insights.service';
import {ActivatedRoute, Params} from '@angular/router';

@Component({
    selector: 'jhi-insights-frequency',
    templateUrl: './insights-frequency.component.html',
    styles: [
        '.card-block>.list-group>.list-group-item {font-size: 0.8rem}',
        '.card-header {font-size: 0.7rem}'
    ]
})
export class InsightsFrequencyComponent implements OnInit {

    words: string[];
    insight: any;

    constructor(private insightsService: InsightsService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: Params) => {
            const imdbId = this.route.parent.snapshot.params['imdbId'];
            const code = `${params['code']}-sentences`;
            this.insightsService.findSentencesByInsightAndImdb(code, imdbId).subscribe((insight: any) => {
                this.words = Object.keys(insight);
                if (this.words.length === 0) {
                    return;
                }
                this.insight = insight;
            });
        });
    }
}
