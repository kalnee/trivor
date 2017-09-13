import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';

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
}
