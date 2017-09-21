import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {InsightsService} from '../insights.service';

@Component({
    selector: 'jhi-insights-verb-tense',
    templateUrl: './insights-verb-tense.component.html',
    styles: []
})
export class InsightsVerbTenseComponent implements OnInit {

    sentences: string[];

    constructor(private insightsService: InsightsService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: Params) => {
            const imdbId = this.route.parent.snapshot.params['imdbId'];
            const code = `${params['code']}`;
            this.insightsService.findVerbTensesByInsightAndImdb(code, imdbId).subscribe((insight: any) => {
                this.sentences = insight;
                if (this.sentences.length === 0) {
                    return;
                }
            });
        });
    }

}
