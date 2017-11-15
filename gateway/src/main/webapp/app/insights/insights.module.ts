import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../shared';
import {INSIGHTS_DETAIL_ROUTE, INSIGHTS_ROUTE} from './insights.route';
import {InsightsComponent} from './insights.component';
import {InsightsService} from './insights.service';
import { TranscriptCardComponent } from './transcript-card/transcript-card.component';
import { InsightsDetailComponent } from './insights-detail/insights-detail.component';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import { InsightsSummaryComponent } from './insights-summary/insights-summary.component';
import { InsightsFrequencyComponent } from './insights-frequency/insights-frequency.component';
import { TvShowSearchComponent } from './tv-show-search/tv-show-search.component';

@NgModule({
    imports: [
        GatewaySharedModule,
        NgxChartsModule,
        RouterModule.forRoot([ INSIGHTS_ROUTE, INSIGHTS_DETAIL_ROUTE ], { useHash: true })
    ],
    declarations: [
        InsightsComponent,
        TranscriptCardComponent,
        InsightsDetailComponent,
        InsightsSummaryComponent,
        InsightsFrequencyComponent,
        TvShowSearchComponent
    ],
    entryComponents: [
    ],
    providers: [
        InsightsService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayInsightsModule {}
