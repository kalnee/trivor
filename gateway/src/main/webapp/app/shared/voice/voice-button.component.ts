import {Component, Input, OnInit} from '@angular/core';
import {VoiceService} from './voice.service';

@Component({
    selector: 'jhi-voice-button',
    template: `
        <button class="btn btn-primary btn-sm float-right" type="button" title="Play"
                (click)="say(message)">
            <span class="fa fa-volume-up"></span>
        </button>
    `,
    styles: ['button {font-size: 8px;}']
})
export class VoiceButtonComponent implements OnInit {

    @Input()
    message: string;

    constructor(private voiceService: VoiceService) {
    }

    ngOnInit() {
    }

    say(message: string) {
        this.voiceService.say(message);
    }
}
