import {Component, HostBinding, Input, OnInit} from '@angular/core';
import {Transcript} from '../../entities/transcript/transcript.model';

@Component({
    selector: 'jhi-transcript-card',
    template: `
        <div class="card jhi-card">
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
            <div class="card-footer text-center">
                <small class="text-muted" [title]="transcript.name">{{transcript.name | shorten:28}}</small>
                <div>
                    <a [routerLink]="['/insights', transcript.imdbId, 'summary']" class="btn btn-primary btn-sm"
                       title="Insights">
                        <span class="fa fa-language"></span> Insights
                    </a>
                </div>
            </div>
        </div>
    `,
    styles: [
        '.jhi-card {width: 13rem;}',
        '.card-img-max-height {max-height: 300px}',
        '.card-text-mt {margin-top: 250px}',
        '.card-footer {padding: 0 0 10px 0}',
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

    @HostBinding('class') cssClass = 'col-md-3 mb-3';

    @Input()
    transcript: Transcript;

    constructor() {
    }

    ngOnInit() {
    }
}
