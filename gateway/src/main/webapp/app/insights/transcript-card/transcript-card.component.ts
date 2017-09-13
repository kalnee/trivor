import {Component, HostBinding, Input, OnInit} from '@angular/core';
import {Transcript} from '../../entities/transcript/transcript.model';

@Component({
    selector: 'jhi-transcript-card',
    template: `
        <div class="card">
            <img class="card-img-top img-responsive card-img-max-height"
                 [src]="transcript.coverUrl"
                 [alt]="transcript.name">
            <div class="card-img-overlay-custom card-text-mt">
                <p class="card-text text-right">
                    <span class="badge badge-primary">{{transcript.type}}</span>
                    <span class="badge badge-success">{{transcript.year}}</span>
                    <span class="badge badge-info">{{transcript.country}}</span>
                </p>
            </div>
            <div class="card-footer">
                <small class="text-muted">{{transcript.name}}</small>
                <a [routerLink]="['/insights', transcript.id]" class="btn btn-primary btn-sm float-right"
                   title="Insights">
                    <span class="fa fa-language"></span> Insights
                </a>
            </div>
        </div>
    `,
    styles: [
            `.card {
            width: 14rem;
            overflow: hidden;
            padding: 0;
            border: none;
            border-radius: .28571429rem;
            box-shadow: 0 1px 3px 0 #d4d4d5, 0 0 0 1px #d4d4d5;
        }`,
        '.card-img-max-height {max-height: 300px}',
        '.card-text-mt {margin-top: 250px}',
            `.card-img-overlay-custom {
            position: absolute;
            top: 0;
            right: 0;
            left: 0;
            padding: 1.25rem;
        }`
    ]
})
export class TranscriptCardComponent implements OnInit {

    @HostBinding('class') cssClass = 'col-md-3 ml-5 mb-3';

    @Input()
    transcript: Transcript;

    constructor() {
    }

    ngOnInit() {
    }
}
