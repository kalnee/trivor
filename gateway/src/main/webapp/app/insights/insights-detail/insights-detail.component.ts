import {Component, OnInit} from '@angular/core';
import {TranscriptService} from '../../entities/transcript/transcript.service';
import {Transcript} from '../../entities/transcript/transcript.model';
import {ActivatedRoute, Params, Router} from '@angular/router';

@Component({
    selector: 'jhi-insights-detail',
    templateUrl: 'insights-detail.component.html',
    styles: []
})
export class InsightsDetailComponent implements OnInit {

    transcript: Transcript;

    single = [
        {
            'name': 'Verbs',
            'value': 101
        },
        {
            'name': 'Nouns',
            'value': 78
        },
        {
            'name': 'Adverbs',
            'value': 75
        },
        {
            'name': 'Adjectives',
            'value': 50
        },
        {
            'name': 'Prepositions',
            'value': 35
        },
        {
            'name': 'Modals',
            'value': 5
        },
        {
            'name': 'Comparatives',
            'value': 5
        },
        {
            'name': 'Superlatives',
            'value': 3
        }
    ];
    multi = [
        {
            'name': 'Simple Present',
            'value': 335
        },
        {
            'name': 'Simple past',
            'value': 300
        },
        {
            'name': 'Simple Future',
            'value': 110
        },
        {
            'name': 'Present Perfect',
            'value': 35
        },
        {
            'name': 'Past Perfect',
            'value': 5
        },
        {
            'name': 'Future Perfect',
            'value': 5
        },
        {
            'name': 'Non Sentences',
            'value': 220
        },
        {
            'name': 'Mixed',
            'value': 450
        },
    ];

    view: any[] = [800, 300];

    onSelect(event) {
        console.log(event);
    }

    constructor(private transcriptService: TranscriptService,
                private route: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit() {
        this.route.params.subscribe(
            (params: Params) => this.transcriptService.find(params['id'])
                .subscribe((transcript: Transcript) => this.transcript = transcript)
        );
    }
}
