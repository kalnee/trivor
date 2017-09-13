import {Route} from '@angular/router';

import {UserRouteAccessService} from '../shared';
import {InsightsComponent} from './insights.component';
import {InsightsDetailComponent} from './insights-detail/insights-detail.component';

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
    path: 'insights/:id',
    component: InsightsDetailComponent,
    data: {
        authorities: ['ROLE_USER', 'ROLE_ADMIN'],
        pageTitle: 'insights.title'
    },
    canActivate: [UserRouteAccessService]
};
