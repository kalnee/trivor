import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    GatewaySharedLibsModule,
    GatewaySharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    Principal,
    HasAnyAuthorityDirective,
    JhiLoginModalComponent
} from './';
import {ShortenPipe} from './shorten/shorten.pipe';
import {CollapseDirective} from './collapse/collapse.directive';

@NgModule({
    imports: [
        GatewaySharedLibsModule,
        GatewaySharedCommonModule
    ],
    declarations: [
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        ShortenPipe,
        CollapseDirective
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        DatePipe
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        GatewaySharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe,
        ShortenPipe,
        CollapseDirective
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class GatewaySharedModule {}
