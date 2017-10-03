import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {GatewaySharedModule} from '../shared';
import {SubtitlesService} from './subtitles.service';

@NgModule({
    imports: [
        GatewaySharedModule
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    providers: [
        SubtitlesService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewaySubtitlesModule {}
