import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Transcript } from './transcript.model';
import { TranscriptService } from './transcript.service';

@Component({
    selector: 'jhi-transcript-detail',
    templateUrl: './transcript-detail.component.html'
})
export class TranscriptDetailComponent implements OnInit, OnDestroy {

    transcript: Transcript;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private transcriptService: TranscriptService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTranscripts();
    }

    load(id) {
        this.transcriptService.find(id).subscribe((transcript) => {
            this.transcript = transcript;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTranscripts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'transcriptListModification',
            (response) => this.load(this.transcript.id)
        );
    }
}
