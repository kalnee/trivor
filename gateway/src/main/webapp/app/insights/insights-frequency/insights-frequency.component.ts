import {Component, OnInit} from '@angular/core';
import {InsightsService} from '../insights.service';
import {ActivatedRoute, Params} from '@angular/router';
import {TranscriptService} from '../../entities/transcript/transcript.service';
import {Observable} from 'rxjs/Observable';
import {Transcript, TranscriptTypeEnum} from '../../entities/transcript/transcript.model';
import {TvShowSearch} from '../tv-show-search/tv-show-search.model';

@Component({
    selector: 'jhi-insights-frequency',
    templateUrl: './insights-frequency.component.html',
    styles: [
        '.card-block>.list-group>.list-group-item {font-size: 0.8rem}',
        '.card-header {font-size: 0.7rem}'
    ]
})
export class InsightsFrequencyComponent implements OnInit {
    insight: any;
    title: string;

    season = 1;
    episode = 1;

    imdbId: string;
    code: string;
    transcript: Transcript;

    constructor(private insightsService: InsightsService,
                private transcriptService: TranscriptService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: Params) => {
            this.imdbId = this.route.parent.snapshot.params['imdbId'];
            this.title = params['code'];
            this.code = `${params['code']}`;

            this.transcriptService.findByImdbId(this.imdbId).subscribe((transcript) => {
                this.transcript = transcript;
                this.findVocabularyUsageByImdbId();
            });
        });
    }

    private findVocabularyUsageByImdbId() {
        const endpoint: Observable<any> = this.isTvShow()
            ? this.insightsService.findVocabularyUsageByImdbId(this.code, this.imdbId, this.season, this.episode)
            : this.insightsService.findVocabularyUsageByImdbId(this.code, this.imdbId);

        endpoint.subscribe((insight: any) => {
            if (insight.length === 0) {
                return;
            }
            this.insight = insight;
        });
    }

    search(data: TvShowSearch) {
        this.season = data.season;
        this.episode = data.episode;
        this.findVocabularyUsageByImdbId();
    }

    isTvShow(): boolean {
        return this.transcript.type === TranscriptTypeEnum.TV_SHOW;
    }
}
