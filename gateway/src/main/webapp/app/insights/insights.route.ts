import {Route} from '@angular/router';

import {UserRouteAccessService} from '../shared';
import {InsightsComponent} from './insights.component';

export const INSIGHTS_ROUTE: Route = {
    path: 'insights',
    component: InsightsComponent,
    data: {
        authorities: ['ROLE_USER', 'ROLE_ADMIN'],
        pageTitle: 'insights.title'
    },
    canActivate: [UserRouteAccessService]
};
