import {Component, OnInit} from '@angular/core';
import {InsightsService} from '../insights.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {SentenceFrequency} from '../sentence-frequency.model';
import {ChunkFrequency} from '../chunk-frequency.model';
import {PhrasalVerbsSentences} from '../phrasal-verb-sentences.model';

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
    frequencyRate = [];

    viewFrequency: any[] = [630, 210];
    viewFrequencyRate: any[] = [500, 200];

    summary: any;
    error: any;

    frequentNouns: any;
    leastFrequentNouns: any;
    frequentVerbs: any;
    leastFrequentVerbs: any;
    frequentSentences: SentenceFrequency[];
    frequentChunks: ChunkFrequency[];
    frequentPhrasalVerbs: PhrasalVerbsSentences[];

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
                this.numOfSentences = summary['number-sentences'];
                this.rateOfSpeech = summary['rate-of-speech'];
                this.sentimentAnalysis = summary['sentiment-analysis'];
                this.pushFrequencyInsights(summary);
                this.pushFrequencyRateInsight(summary);
            }, (error: any) => {
                console.error(`Error trying to get summary for ${params['imdbId']}: ${error}`);
                this.error = error;
            });

            this.insightsService.findVocabularyFrequencyByImdbId('nouns', params['imdbId'], 20)
                .subscribe((nouns: any) => {
                    this.frequentNouns = nouns;
                });

            this.insightsService.findVocabularyFrequencyByImdbId('nouns', params['imdbId'], -20)
                .subscribe((nouns: any) => {
                    this.leastFrequentNouns = nouns;
                });

            this.insightsService.findVocabularyFrequencyByImdbId('verbs', params['imdbId'], 20)
                .subscribe((verbs: any) => {
                    this.frequentVerbs = verbs;
                });

            this.insightsService.findVocabularyFrequencyByImdbId('verbs', params['imdbId'], -20)
                .subscribe((verbs: any) => {
                    this.leastFrequentVerbs = verbs;
                });

            this.insightsService.findSentencesFrequencyByImdbId(params['imdbId'], 50)
                .subscribe((sentences: SentenceFrequency[]) => {
                    this.frequentSentences = sentences;
                });

            this.insightsService.findChunksFrequencyByImdbId(params['imdbId'], 50)
                .subscribe((chunks: ChunkFrequency[]) => {
                    this.frequentChunks = chunks;
                });

            this.insightsService.findPhrasalVerbsByImdbId(params['imdbId'])
                .subscribe((phrasalVerbs: PhrasalVerbsSentences[]) => {
                    this.frequentPhrasalVerbs = phrasalVerbs;
                });
        });
    }

    onSelectFrequency(event) {
        const name = event.name.toLowerCase();
        this.router.navigate(['../frequency', name], {relativeTo: this.route});
    }

    private pushFrequencyInsights(summary: any) {
        this.frequency.push(...[
            {'name': 'Nouns', 'value': summary['nouns-usage']},
            {'name': 'Verbs', 'value': summary['verbs-usage']},
            {'name': 'Adjectives', 'value': summary['adjectives-usage']},
            {'name': 'Adverbs', 'value': summary['adverbs-usage']},
            {'name': 'Prepositions', 'value': summary['prepositions-usage']},
            {'name': 'Modals', 'value': summary['modals-usage']},
            {'name': 'Comparatives', 'value': summary['comparatives-usage']},
            {'name': 'Superlatives', 'value': summary['superlatives-usage']}
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
        if (!this.sentimentAnalysis) {
            return 'fa-minus';
        }
        return parseFloat(this.sentimentAnalysis['POSITIVE']) > parseFloat(this.sentimentAnalysis['NEGATIVE'])
            ? 'fa-thumbs-up text-success' : 'fa-thumbs-down text-warning';
    }

    getSentimentTitle(): string {
        if (!this.sentimentAnalysis) {
            return 'None';
        }
        const positive = parseFloat(this.sentimentAnalysis['POSITIVE']);
        const negative = parseFloat(this.sentimentAnalysis['NEGATIVE']);
        return positive > negative
            ? `Positive (${(100 * positive).toFixed(2)}%)`
            : `Negative (${(100 * negative).toFixed(2)}%)`;
    }
}
