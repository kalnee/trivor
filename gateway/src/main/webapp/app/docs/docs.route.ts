import { Route } from '@angular/router';

import { JhiDocsComponent } from './docs.component';
import {UserRouteAccessService} from '../shared/auth/user-route-access-service';

export const docsRoute: Route = {
    path: 'docs',
    component: JhiDocsComponent,
    data: {
        authorities: [],
        pageTitle: 'global.menu.admin.apidocs'
    },
    canActivate: [UserRouteAccessService]
};
