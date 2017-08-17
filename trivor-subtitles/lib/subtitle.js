'use strict';

/**
 * Module dependencies.
 */

const MovieDB = require('./themoviedb.js');

/**
 * Manages the download and upload of subtitles
 *
 */
class Subtitle {

    /**
     * Subtitle constructor.
     *
     * @param {String} imdbId
     * @param {Boolean} resend Define if the subtitle will be resent to the queue
     * @param {Queue} subtitlesQ queue
     * @api private
     */
    constructor(imdbId, resend, subtitlesQ) {
        this.imdbId = imdbId;
        this.resend = resend === undefined ? true : resend;
        this.mdb = new MovieDB();
        this.subtitlesQ = subtitlesQ;
        this.subtitles = [];
    }

    /**
     * Checks if the provided id is of a movie.
     *
     * @api private
     */
    isMovie() {
        return this.title.movie_results.length;
    }

    /**
     * Checks if the provided id is of a tv show.
     *
     * @api private
     */
    isTVShow() {
        return this.title.tv_results.length;
    }

    /**
     * Returns all subtitles of the title
     *
     * @api public
     */
    getSubtitles() {
        return this.subtitles;
    }

    /**
     * Adds all subtitles of a title.
     *
     * @param {Function} callback
     *
     * @api private
     */
    addSubtitles(callback) {
        if (this.isMovie()) {
            let movieId = this.title.movie_results[0].id;
            this.mdb.movie(movieId, movie => {
                this.mdb.keywords("movie", movieId, keywords => {
                  this.subtitles.push({
                    "imdbId": this.imdbId,
                    "type": "MOVIE",
                    "year": new Date(movie.release_date).getFullYear(),
                    "name": movie.title,
                    "duration": movie.runtime,
                    "genres": movie.genres.map(genre => genre.name.toLowerCase()),
                    "keywords": keywords.keywords.map(keyword => keyword.name.toLowerCase()),
                    "resend": this.resend
                  });

                  callback();
                });
            });
        }

        if (this.isTVShow()) {
            let tvId = this.title.tv_results[0].id;
            this.mdb.tv(tvId, show => {
              this.mdb.keywords("tv", tvId, keywords => {
                show.seasons.forEach(season => {
                  for (let i = 1; i <= season.episode_count; i++) {
                    // skipping pilot episodes
                    if (season.season_number === 0) {
                      continue;
                    }
                    this.subtitles.push({
                      "imdbId": this.imdbId,
                      "season": season.season_number,
                      "episode": i,
                      "type": "TV_SHOW",
                      "year": new Date(show.first_air_date).getFullYear(),
                      "name": show.name,
                      "duration": show.episode_run_time.sort()[0],
                      "status": show.status,
                      "genres": show.genres.map(genre => genre.name.toLowerCase()),
                      "keywords": keywords.keywords.map(keyword => keyword.name.toLowerCase()),
                      "resend": this.resend
                    });
                  }
                });
                callback();
              });
            });
        }
    }

    /**
     * Returns the name of the file that will be saved.
     *
     * @api public
     */
    static getFileName(subtitle) {
        if (!subtitle.season) {
            return `${subtitle.imdbId}.srt`;
        }

        return `${subtitle.imdbId}-S${subtitle.season}E${subtitle.episode}.srt`;
    }

    load(callback) {
        this.mdb.find(this.imdbId, (err, title) => {
            if (err) {
                callback(err, `No subtitles queued (${this.imdbId})`);
                return;
            }
            this.title = title;
            this.addSubtitles(() => {
                this.getSubtitles().forEach((subtitle) => {
                    this.subtitlesQ.sendMessage(JSON.stringify(subtitle));
                });

                callback(err, `${this.getSubtitles().length} subtitle(s) queued for processing (${this.imdbId})`);
            });
        });
    }
}

/**
 * Module exports.
 */

module.exports = Subtitle;
