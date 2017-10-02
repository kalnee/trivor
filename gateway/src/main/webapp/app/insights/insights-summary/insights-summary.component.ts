import {Component, OnInit} from '@angular/core';
import {InsightsService} from '../insights.service';
import {ActivatedRoute, Params, Router} from '@angular/router';

@Component({
    selector: 'jhi-insights-summary',
    templateUrl: './insights-summary.component.html',
    styles: ['.frequency-card {max-height: 286px;}']
})
export class InsightsSummaryComponent implements OnInit {

    numOfSentences: number;
    rateOfSpeech: string;
    sentimentAnalysis: any;

    frequency = [];
    tenses = [];
    frequencyRate = [];

    viewFrequency: any[] = [630, 210];
    viewTenses: any[] = [900, 500];
    viewFrequencyRate: any[] = [500, 200];

    summary: any;
    error: any;

    frequentNouns: any;
    leastFrequentNouns: any;
    frequentVerbs: any;
    leastFrequentVerbs: any;

    constructor(private insightsService: InsightsService,
                private route: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit() {
        this.route.parent.params.subscribe((params: Params) => {
            this.insightsService.getSummaryByImdbId(params['imdbId']).subscribe((summary: any) => {
                if (Object.keys(summary).length === 0) {
                    return;
                }
                this.summary = summary;
                this.numOfSentences = summary['number-of-sentences'];
                this.rateOfSpeech = summary['rate-of-speech'];
                this.sentimentAnalysis = summary['sentiment-analysis'];
                this.pushFrequencyInsights(summary);
                this.pushTenseInsights(summary);
                this.pushFrequencyRateInsight(summary);
            }, (error: any) => {
                console.error(`Error trying to get summary for ${params['imdbId']}: ${error}`);
                this.error = error;
            });

            this.insightsService.findTopFrequencyByImdbId('nouns-frequency', params['imdbId'], 10)
                .subscribe((nouns: any) => {
                    this.frequentNouns = nouns;
                });

            this.insightsService.findTopFrequencyByImdbId('nouns-frequency', params['imdbId'], -10)
                .subscribe((nouns: any) => {
                    this.leastFrequentNouns = nouns;
                });

            this.insightsService.findTopFrequencyByImdbId('verbs-frequency', params['imdbId'], 10)
                .subscribe((verbs: any) => {
                    this.frequentVerbs = verbs;
                });

            this.insightsService.findTopFrequencyByImdbId('verbs-frequency', params['imdbId'], -10)
                .subscribe((verbs: any) => {
                    this.leastFrequentVerbs = verbs;
                });
        });
    }

    onSelectFrequency(event) {
        const name = event.name.toLowerCase();
        this.router.navigate(['../frequency', name], {relativeTo: this.route});
    }

    onSelectTense(event) {
        const name = event.name.toLowerCase().replace(/ /g, '-');
        this.router.navigate(['../verb-tenses', name], {relativeTo: this.route});
    }

    private pushFrequencyInsights(summary: any) {
        this.frequency.push(...[
            {'name': 'Nouns', 'value': summary['nouns-frequency']},
            {'name': 'Verbs', 'value': summary['verbs-frequency']},
            {'name': 'Adjectives', 'value': summary['adjectives-frequency']},
            {'name': 'Adverbs', 'value': summary['adverbs-frequency']},
            {'name': 'Prepositions', 'value': summary['prepositions-frequency']},
            {'name': 'Modals', 'value': summary['modals-frequency']},
            {'name': 'Comparatives', 'value': summary['comparatives-frequency']},
            {'name': 'Superlatives', 'value': summary['superlatives-frequency']}
        ]);
    }

    private pushTenseInsights(summary: any) {
        this.tenses.push(...[
            {'name': 'Simple Present', 'value': summary['simple-present']},
            {'name': 'Simple Past', 'value': summary['simple-past']},
            {'name': 'Simple Future', 'value': summary['simple-future']},
            {'name': 'Present Progressive', 'value': summary['present-progressive']},
            {'name': 'Past Progressive', 'value': summary['past-progressive']},
            {'name': 'Future Progressive', 'value': summary['future-progressive']},
            {'name': 'Present Perfect', 'value': summary['present-perfect']},
            {'name': 'Past Perfect', 'value': summary['past-perfect']},
            {'name': 'Future Perfect', 'value': summary['future-perfect']},
            {'name': 'Non Sentences', 'value': summary['non-sentences']},
            {'name': 'Mixed Tenses', 'value': summary['mixed-tenses']}
        ]);
    }

    private pushFrequencyRateInsight(summary: any) {
        this.frequencyRate.push(...[
            {'name': 'High', 'value': summary['frequency-rate']['HIGH']},
            {'name': 'Middle', 'value': summary['frequency-rate']['MIDDLE']},
            {'name': 'Low', 'value': summary['frequency-rate']['LOW']},
        ]);
    }

    getROSClass(): string {
        switch (this.rateOfSpeech) {
            case 'SLOW':
            case 'NONE':
                return 'badge-info';
            case 'MODERATE':
                return 'badge-warning';
            case 'FAST':
            case 'SUPER_FAST':
                return 'badge-danger';
            default:
                return 'badge-info';
        }
    }

    getSentimentClass(): string {
        return parseFloat(this.sentimentAnalysis['POSITIVE']) > parseFloat(this.sentimentAnalysis['NEGATIVE'])
            ? 'fa-thumbs-up text-success' : 'fa-thumbs-down text-warning';
    }

    getSentimentTitle(): string {
        const positive = parseFloat(this.sentimentAnalysis['POSITIVE']);
        const negative = parseFloat(this.sentimentAnalysis['NEGATIVE']);
        return positive > negative
            ? `Positive (${(100 * positive).toFixed(2)}%)`
            : `Negative (${(100 * negative).toFixed(2)}%)`;
    }
}
