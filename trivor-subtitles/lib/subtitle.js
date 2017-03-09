'use strict'

/**
 * Module dependencies.
 */

const MovieDB = require('./themoviedb.js');
const Queue = require('./queue.js');
const opensubtitles = require('./opensubtitles.js');
const request = require('request');
const zlib = require('zlib');
const fs = require('fs');
const config = require('config');

/**
 * Manages the download and upload of subtitles
 *
 */
class Subtitle {

    /**
     * Subtitle constructor.
     *
     * @param {String} imdbId
     * @param {Queue} subtitles queue
     * @api private
     */
    constructor(imdbId, subtitlesQ) {
        this.imdbId = imdbId;
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
            this.subtitles.push({
                "id": this.imdbId,
                "type": "MOVIE",
                "year": new Date(this.title.movie_results[0].release_date).getFullYear(),
                "name": this.title.movie_results[0].title
            });

            callback();
        }

        if (this.isTVShow()) {
            this.mdb.tv(this.title.tv_results[0].id, (show) => {
                show.seasons.forEach((season) => {
                    for (var i = 1; i <= season.episode_count; i++) {
                        this.subtitles.push({
                            "id": this.imdbId,
                            "season": season.season_number,
                            "episode": i,
                            "type": "TV_SHOW",
                            "year": new Date(this.title.tv_results[0].release_date).getFullYear(),
                            "name": this.title.tv_results[0].title
                        });
                    }
                });
                callback();
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
            return `${subtitle.id}.srt`;
        }

        return `${subtitle.id}-S${subtitle.season}E${subtitle.episode}.srt`;
    }

    /**
     * Searchs and downloads the file of a subtitle.
     *
     * @param {Function} _callback
     * @api public
     */
    static fetch(subtitle, fileName, callback) {
        opensubtitles.search(subtitle.id, subtitle.season, subtitle.episode, (srt) => {
            if (!srt)
                throw 'subtitle not found';

            var output = fs.createWriteStream("/tmp/" + fileName);

            request({
                url: srt.SubDownloadLink,
                headers: {
                    'Accept-Encoding': 'gzip'
                }
            })
                .pipe(zlib.createGunzip())
                .pipe(output);

            output.on('close', () => {
                if (callback) {
                    callback();
                }
            });
        });
    }

    load(callback) {
        this.mdb.find(this.imdbId, (err, title) => {
            if (err) {
              callback(err, "no subtitles queued");
              return;
            }
            this.title = title;
            this.addSubtitles(() => {
                this.getSubtitles().forEach((subtitle) => {
                    this.subtitlesQ.sendMessage(JSON.stringify(subtitle));
                });

                callback(err, this.getSubtitles().length + " subtitle(s) queued for processing.");
            });
        });
    }
}

/**
 * Module exports.
 */

module.exports = Subtitle;
