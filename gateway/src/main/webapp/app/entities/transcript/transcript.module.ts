import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    TranscriptService,
    TranscriptPopupService,
    TranscriptComponent,
    TranscriptDetailComponent,
    TranscriptDialogComponent,
    TranscriptPopupComponent,
    TranscriptDeletePopupComponent,
    TranscriptDeleteDialogComponent,
    transcriptRoute,
    transcriptPopupRoute,
} from './';

const ENTITY_STATES = [
    ...transcriptRoute,
    ...transcriptPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TranscriptComponent,
        TranscriptDetailComponent,
        TranscriptDialogComponent,
        TranscriptDeleteDialogComponent,
        TranscriptPopupComponent,
        TranscriptDeletePopupComponent,
    ],
    entryComponents: [
        TranscriptComponent,
        TranscriptDialogComponent,
        TranscriptPopupComponent,
        TranscriptDeleteDialogComponent,
        TranscriptDeletePopupComponent,
    ],
    providers: [
        TranscriptService,
        TranscriptPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayTranscriptModule {}
