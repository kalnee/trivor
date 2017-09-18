import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Transcript } from './transcript.model';
import { TranscriptPopupService } from './transcript-popup.service';
import { TranscriptService } from './transcript.service';

@Component({
    selector: 'jhi-transcript-dialog',
    templateUrl: './transcript-dialog.component.html'
})
export class TranscriptDialogComponent implements OnInit {

    transcript: Transcript;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private transcriptService: TranscriptService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.transcript.id !== undefined) {
            this.subscribeToSaveResponse(
                this.transcriptService.update(this.transcript));
        } else {
            this.subscribeToSaveResponse(
                this.transcriptService.create(this.transcript));
        }
    }

    private subscribeToSaveResponse(result: Observable<Transcript>) {
        result.subscribe((res: Transcript) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Transcript) {
        this.eventManager.broadcast({ name: 'transcriptListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-transcript-popup',
    template: ''
})
export class TranscriptPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transcriptPopupService: TranscriptPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.transcriptPopupService
                    .open(TranscriptDialogComponent as Component, params['id']);
            } else {
                this.transcriptPopupService
                    .open(TranscriptDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
