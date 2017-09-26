import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {TvShowSearch} from './tv-show-search.model';

@Component({
    selector: 'jhi-tv-show-search',
    templateUrl: './tv-show-search.component.html',
    styles: []
})
export class TvShowSearchComponent implements OnInit {

    season = 1;
    episode = 1;

    @Output()
    public onSearch = new EventEmitter<TvShowSearch>();

    constructor() {
    }

    ngOnInit() {
    }

    search() {
        console.log(this.season + ' ' + this.episode);
        this.onSearch.emit(new TvShowSearch(this.season, this.episode));
    }

}
