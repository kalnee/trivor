import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TranscriptComponent } from './transcript.component';
import { TranscriptDetailComponent } from './transcript-detail.component';
import { TranscriptPopupComponent } from './transcript-dialog.component';
import { TranscriptDeletePopupComponent } from './transcript-delete-dialog.component';

export const transcriptRoute: Routes = [
    {
        path: 'transcript',
        component: TranscriptComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.transcript.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'transcript/:id',
        component: TranscriptDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.transcript.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const transcriptPopupRoute: Routes = [
    {
        path: 'transcript-new',
        component: TranscriptPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.transcript.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'transcript/:id/edit',
        component: TranscriptPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.transcript.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'transcript/:id/delete',
        component: TranscriptDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.transcript.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
