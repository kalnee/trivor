import {Component, OnInit} from '@angular/core';
import {InsightsService} from './insights.service';
import {TranscriptService} from '../entities/transcript/transcript.service';
import {ITEMS_PER_PAGE} from '../shared/index';
import {ResponseWrapper} from '../shared/model/response-wrapper.model';
import {Transcript} from '../entities/transcript/transcript.model';
import {JhiAlertService, JhiParseLinks} from 'ng-jhipster';

@Component({
    selector: 'jhi-insights',
    templateUrl: './insights.component.html',
    styles: []
})
export class InsightsComponent implements OnInit {

    imdbId = 'tt4034228';
    insights: any;
    itemsPerPage: number = ITEMS_PER_PAGE;
    links = {
        last: 0
    };
    page = 0;
    totalItems: number;
    transcripts: Transcript[] = [];

    constructor(private insightsService: InsightsService,
                private transcriptService: TranscriptService,
                private alertService: JhiAlertService,
                private parseLinks: JhiParseLinks) {
    }

    ngOnInit() {
        this.loadAllTranscripts();
    }

    loadAllTranscripts() {
        this.transcriptService.query({
            page: this.page,
            size: this.itemsPerPage
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.transcripts.push(data[i]);
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackId(index: number, item: Transcript) {
        return item.id;
    }

    loadPage(page) {
        this.page = page;
        this.loadAllTranscripts();
    }
}
