import {Route} from '@angular/router';

import {UserRouteAccessService} from '../shared';
import {InsightsComponent} from './insights.component';
import {InsightsDetailComponent} from './insights-detail/insights-detail.component';
import {InsightsSummaryComponent} from './insights-summary/insights-summary.component';
import {InsightsFrequencyComponent} from './insights-frequency/insights-frequency.component';
import {InsightsVerbTenseComponent} from './insights-verb-tense/insights-verb-tense.component';

export const INSIGHTS_ROUTE: Route = {
    path: 'insights',
    component: InsightsComponent,
    data: {
        authorities: ['ROLE_USER', 'ROLE_ADMIN'],
        pageTitle: 'insights.title'
    },
    canActivate: [UserRouteAccessService]
};
export const INSIGHTS_DETAIL_ROUTE: Route = {
    path: 'insights/:imdbId',
    component: InsightsDetailComponent,
    data: {
        authorities: ['ROLE_USER', 'ROLE_ADMIN'],
        pageTitle: 'insights.title'
    },
    children: [{
        path: '',
        redirectTo: 'summary',
        pathMatch: 'full'
    }, {
        path: 'summary',
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'insights.title'
        },
        component: InsightsSummaryComponent
    }, {
        path: 'frequency/:code',
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'insights.title'
        },
        component: InsightsFrequencyComponent
    }, {
        path: 'verb-tenses/:code',
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'insights.title'
        },
        component: InsightsVerbTenseComponent
    }],
    canActivate: [UserRouteAccessService]
};
