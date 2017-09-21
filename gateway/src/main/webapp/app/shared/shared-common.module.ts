import {LOCALE_ID, NgModule} from '@angular/core';
import {Title} from '@angular/platform-browser';

import {
    FindLanguageFromKeyPipe,
    GatewaySharedLibsModule,
    JhiAlertComponent,
    JhiAlertErrorComponent,
    JhiLanguageHelper
} from './';

@NgModule({
    imports: [
        GatewaySharedLibsModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ],
    providers: [
        JhiLanguageHelper,
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'en'
        }
    ],
    exports: [
        GatewaySharedLibsModule,
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ]
})
export class GatewaySharedCommonModule {}
