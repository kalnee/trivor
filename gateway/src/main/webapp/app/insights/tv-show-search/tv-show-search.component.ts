import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TvShowSearch} from './tv-show-search.model';
import {TvShow} from '../../subtitles/tv-show.model';
import {SubtitlesService} from '../../subtitles/subtitles.service';
import {Transcript} from '../../entities/transcript/transcript.model';

@Component({
    selector: 'jhi-tv-show-search',
    templateUrl: './tv-show-search.component.html',
    styles: []
})
export class TvShowSearchComponent implements OnInit {

    episodes = [];
    seasons = [];

    season = 1;
    episode = 1;

    tvShow: any;

    @Input()
    transcript: Transcript;

    @Output()
    public onSearch = new EventEmitter<TvShowSearch>();

    constructor(private subtitlesService: SubtitlesService) {
    }

    ngOnInit() {
        this.findTvShowByImdbId();
    }

    search() {
        this.onSearch.emit(new TvShowSearch(this.season, this.episode));
    }

    private findTvShowByImdbId() {
        this.subtitlesService.findTvShowByImdbId(this.transcript.imdbId).subscribe((tvShow: TvShow) => {
            this.tvShow = tvShow;
            this.seasons = [...Object.keys(tvShow.seasons)];
            this.episodes = tvShow.seasons[1];
        });
    }

    onChangeSeason() {
        this.episodes = this.tvShow.seasons[this.season];
        this.episode = 1;
    }

}
