import {Component, ElementRef, OnDestroy, OnInit, Renderer2, ViewChild} from '@angular/core';
import {VoiceService} from './voice.service';

@Component({
    selector: 'jhi-voice',
    template: `
        <div class="voice-container" #voiceContainer>
            <div class="row justify-content-center p-2">
                <div class="col-md-6 text-center">
                    <div>
                        <span class="fa fa-microphone text-muted"></span>
                        <span class="small text-muted">You've said: </span>
                        <span class="voice-text" #voiceText>Say something...</span>
                    </div>
                </div>
            </div>
        </div>
    `,
    styles: [
        'button {font-size: 8px;}',
            `.voice-container {
            background-color: #ffffff;
            position: fixed;
            bottom: 0;
            width: 100%;
            z-index: 100;
            opacity: 0.8;
            border-top: 1px solid #331F5E
        }`,
        '.voice-text {font-size: 20px;}'
    ]
})
export class VoiceComponent implements OnInit, OnDestroy {

    @ViewChild('voiceContainer')
    private voiceContainerEl: ElementRef;

    @ViewChild('voiceText')
    private voiceTextEl: ElementRef;

    constructor(private voiceService: VoiceService,
                private renderer: Renderer2) {
    }

    ngOnInit() {
        this.voiceService.init();

        this.voiceService.artyom.redirectRecognizedTextOutput((recognized, isFinal) => {
            if (isFinal) {
                this.renderer.removeClass(this.voiceTextEl.nativeElement, 'font-italic');
            } else {
                this.renderer.addClass(this.voiceTextEl.nativeElement, 'font-italic');
            }
            this.voiceTextEl.nativeElement.innerText = recognized;
        });

        this.voiceService.artyom.on(['open please']).then((i, wildcard) => {
            this.voiceTextEl.nativeElement.innerText = '';
            this.renderer.addClass(this.voiceContainerEl.nativeElement, 'show');
        });

        this.voiceService.artyom.on(['clear please', 'close please']).then((i, wildcard) => {
            this.voiceTextEl.nativeElement.innerText = '';
            if (i === 1) {
                this.renderer.removeClass(this.voiceContainerEl.nativeElement, 'show');
                this.renderer.addClass(this.voiceContainerEl.nativeElement, 'hide');
            }
        });

        this.voiceService.artyom.on(['repeat *'], true).then((i, wildcard) => {
            this.voiceService.say(`You've said: ${wildcard}`);
        });

        this.voiceService.artyom.on(['translate * in Portuguese'], true).then((i, word) => {
            this.voiceService.say(`Google has a better translation to ${word}`);
            window.open(`https://translate.google.com/?source=gtx_m#en/pt/${word}`, `_blank`);
        });
    }

    ngOnDestroy() {
        this.voiceService.destroy();
    }
}
