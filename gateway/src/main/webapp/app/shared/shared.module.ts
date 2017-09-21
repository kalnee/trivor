import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {DatePipe} from '@angular/common';

import {
    AccountService,
    AuthServerProvider,
    CSRFService,
    GatewaySharedCommonModule,
    GatewaySharedLibsModule,
    HasAnyAuthorityDirective,
    JhiLoginModalComponent,
    LoginModalService,
    LoginService,
    Principal,
    StateStorageService,
    UserService
} from './';
import {ShortenPipe} from './shorten/shorten.pipe';
import {CollapseDirective} from './collapse/collapse.directive';
import {VoiceButtonComponent} from './voice/voice-button.component';
import {VoiceComponent} from './voice/voice.component';
import {VoiceService} from './voice/voice.service';

@NgModule({
    imports: [
        GatewaySharedLibsModule,
        GatewaySharedCommonModule
    ],
    declarations: [
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        ShortenPipe,
        CollapseDirective,
        VoiceComponent,
        VoiceButtonComponent
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
        DatePipe,
        VoiceService
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        GatewaySharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe,
        ShortenPipe,
        CollapseDirective,
        VoiceComponent,
        VoiceButtonComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class GatewaySharedModule {}
