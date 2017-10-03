import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {GatewaySharedModule} from '../shared';

import {JhiDocsComponent} from './docs.component';
import {docsRoute} from './docs.route';

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot([docsRoute], {useHash: true})
    ],
    declarations: [
        JhiDocsComponent
    ],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayDocsModule {
}
