import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Params} from '@angular/router';

@Injectable()
export class InsightsService {

    insights: any[] = [];

    constructor(private http: Http) {
    }

    findByImdbId(imdbId: string): Observable<any> {
        return this.http.get('insights/api/insights/nouns-frequency/', {params: {'imdbId': imdbId}})
            .map((response: Response) => {
                console.log(response);
                return response.json();
            });
    }

    getSummaryByImdbId(imdbId: string): Observable<any> {
        return this.http.get('insights/api/insights/summary', {params: {'imdbId': imdbId}})
            .map((response: Response) => {
                return response.json();
            });
    }

    findVocabularyUsageByImdbId(vocabulary: string, imdbId: string, season: number = null, episode: number = null): Observable<any> {
        const params: Params = (season !== null && episode !== null) ? {'season': season, 'episode': episode} : {};
        return this.http.get(`insights/api/insights/vocabulary/${vocabulary}/usage`,
            {params: {'imdbId': imdbId, ...params}})
            .map((response: Response) => {
                return response.json();
            });
    }

    findVerbTensesByInsightAndImdb(tense: string, imdbId: string): Observable<any> {
        return this.http.get(`insights/api/insights/verb-tenses/${tense}/usage`, {params: {'imdbId': imdbId}})
            .map((response: Response) => {
                return response.json();
            });
    }

    findVocabularyFrequencyByImdbId(vocabulary: string, imdbId: string, limit: number): Observable<any> {
        return this.http.get(`insights/api/insights/vocabulary/${vocabulary}/frequency`, {
            params: {'imdbId': imdbId, 'limit': limit}
        }).map((response: Response) => {
            return response.json();
        });
    }
}
