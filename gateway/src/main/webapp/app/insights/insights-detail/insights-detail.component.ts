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
                private route: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit() {
        this.route.params.subscribe(
            (params: Params) => this.transcriptService.find(params['id'])
                .subscribe((transcript: Transcript) => this.transcript = transcript)
        );
    }
}
