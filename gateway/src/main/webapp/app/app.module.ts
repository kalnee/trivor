import './vendor.ts';

import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {Ng2Webstorage} from 'ng2-webstorage';

import {GatewaySharedModule, UserRouteAccessService} from './shared';
import {GatewayHomeModule} from './home/home.module';
import {GatewayAdminModule} from './admin/admin.module';
import {GatewayAccountModule} from './account/account.module';
import {GatewayEntityModule} from './entities/entity.module';

import {customHttpProvider} from './blocks/interceptor/http.provider';
import {PaginationConfig} from './blocks/config/uib-pagination.config';
import {
    ActiveMenuDirective,
    ErrorComponent,
    FooterComponent,
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    PageRibbonComponent,
    ProfileService
} from './layouts';
import {GatewayInsightsModule} from './insights/insights.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from 'ngx-toastr';
import {GatewaySubtitlesModule} from './subtitles/subtitles.module';
import {GatewayDocsModule} from './docs/docs.module';

// jhipster-needle-angular-add-module-import JHipster will add new module here

@NgModule({
    imports: [
        BrowserAnimationsModule,
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        ToastrModule.forRoot(),
        GatewaySharedModule,
        GatewayHomeModule,
        GatewayAdminModule,
        GatewayAccountModule,
        GatewayEntityModule,
        GatewayInsightsModule,
        GatewaySubtitlesModule,
        GatewayDocsModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class GatewayAppModule {}
