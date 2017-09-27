import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class SubtitlesService {

    constructor(private http: Http) {
    }

    findTvShowByImdbId(imdbId: string): Observable<any> {
        return this.http.get('insights/api/subtitles/tv-show/meta', {params: {'imdbId': imdbId}})
            .map((response: Response) => {
                return response.json();
            });
    }
}
