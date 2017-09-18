import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Transcript } from './transcript.model';
import { TranscriptPopupService } from './transcript-popup.service';
import { TranscriptService } from './transcript.service';

@Component({
    selector: 'jhi-transcript-delete-dialog',
    templateUrl: './transcript-delete-dialog.component.html'
})
export class TranscriptDeleteDialogComponent {

    transcript: Transcript;

    constructor(
        private transcriptService: TranscriptService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.transcriptService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'transcriptListModification',
                content: 'Deleted an transcript'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-transcript-delete-popup',
    template: ''
})
export class TranscriptDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transcriptPopupService: TranscriptPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.transcriptPopupService
                .open(TranscriptDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
