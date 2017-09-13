import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../shared';
import {INSIGHTS_ROUTE} from './insights.route';
import {InsightsComponent} from './insights.component';
import {InsightsService} from './insights.service';

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot([ INSIGHTS_ROUTE ], { useHash: true })
    ],
    declarations: [
        InsightsComponent
    ],
    entryComponents: [
    ],
    providers: [
        InsightsService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayInsightsModule {}
