import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Transcript } from './transcript.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TranscriptService {

    private resourceUrl = 'api/transcripts';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(transcript: Transcript): Observable<Transcript> {
        const copy = this.convert(transcript);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(transcript: Transcript): Observable<Transcript> {
        const copy = this.convert(transcript);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Transcript> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findByImdbId(imdbId: string): Observable<Transcript> {
        return this.http.get(`${this.resourceUrl}/imdb/${imdbId}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.createdAt = this.dateUtils
            .convertDateTimeFromServer(entity.createdAt);
    }

    private convert(transcript: Transcript): Transcript {
        const copy: Transcript = Object.assign({}, transcript);

        copy.createdAt = this.dateUtils.toDate(transcript.createdAt);
        return copy;
    }
}
