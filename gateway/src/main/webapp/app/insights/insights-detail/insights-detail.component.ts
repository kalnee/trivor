import {Component, OnInit} from '@angular/core';
import {TranscriptService} from '../../entities/transcript/transcript.service';
import {Transcript} from '../../entities/transcript/transcript.model';
import {ActivatedRoute, Params, Router} from '@angular/router';

@Component({
    selector: 'jhi-insights-detail',
    templateUrl: 'insights-detail.component.html',
    styles: []
})
export class InsightsDetailComponent implements OnInit {

    transcript: Transcript;

    constructor(private transcriptService: TranscriptService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.route.params.subscribe(
            (params: Params) => {
                this.transcriptService.findByImdbId(params['imdbId'])
                    .subscribe((transcript: Transcript) => this.transcript = transcript);
            }
        );
    }
}
