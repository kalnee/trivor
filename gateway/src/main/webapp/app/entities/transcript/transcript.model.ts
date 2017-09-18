import { BaseEntity } from './../../shared';

const enum TranscriptTypeEnum {
    'TRANSCRIPT',
    'MOVIE',
    'TV_SHOW'
}

export class Transcript implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public type?: TranscriptTypeEnum,
        public imdbId?: string,
        public coverUrl?: string,
        public createdAt?: any,
        public description?: string,
        public year?: number,
        public genres?: string,
        public country?: string,
        public duration?: number,
        public language?: string,
    ) {
    }
}
