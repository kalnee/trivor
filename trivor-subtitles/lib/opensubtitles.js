"use strict";

/**
 * Module dependencies.
 */

const xmlrpc = require('xmlrpc');
const request = require('request');
const zlib = require('zlib');
const fs = require('fs');
const config = require('config');

const client = xmlrpc.createClient({
    url: config.get('OpenSubtitles.url'),
    headers: {
        'User-Agent': config.get('OpenSubtitles.user_agent')
    }
});

/**
 * OpenSubtitles API client.
 *
 */
class OpenSubtitles {

    /**
     * OpenSubtitles constructor.
     * @api private
     */
    constructor() {

    }

    /**
     * Retrieves an authentication token.
     *
     * @param {Function} _callback
     * @api private
     */
    static login(_callback) {
        client.methodCall('LogIn', [
            null,
            null,
            null,
            'PopcornHour v1'
        ], (error, auth) => {
            if (error)
                throw error;

            _callback(auth.token);

        });
    }

    /**
     * Search and return a subtitle.
     *
     * @param {String} _imdbId
     * @param {Number} _season
     * @param {Number} _episode
     * @param {Function} _callback
     * @api private
     */
    static search(_imdbId, _season, _episode, _callback) {
        let queries = [{
            imdbid: _imdbId.substring(2),
            sublanguageid: 'eng',
            season: _season ? _season : null,
            episode: _episode ? _episode : null
        }];

        this.login((token) => {
            client.methodCall('SearchSubtitles', [token, queries, { limit: 100 }], (error, subtitles) => {
                if (error)
                    throw error;

                let status = this.parseStatus(subtitles.status);

                if (status.code === '200' && subtitles.data) {
                    console.log("Sorting subtitles");
                    let filteredSubtitles = subtitles.data.filter(sub => {
                        return sub.SubFormat === "srt" && parseInt(sub.SubSize) > 0;
                    });
                    filteredSubtitles.sort((a, b) => {
                       return b.SubRating - a.SubRating;
                    });
                    _callback(null, filteredSubtitles[0]);
                } else {
                    _callback(status.msg, null);
                }
            });
        });
    }

    /**
     * Searches and downloads the file of a subtitle.
     *
     * @param {Subtitle} subtitle
     * @param {String} fileName
     * @param {Function} callback
     * @api public
     */
    static fetch(subtitle, fileName, callback) {
        this.search(subtitle.imdbId, subtitle.season, subtitle.episode, (error, srt) => {
            if (error) {
                console.log(error);
                return;
            }

            if (!srt) {
                console.log(`subtitle not found for ${subtitle.imdbId} (${subtitle.season}-${subtitle.episode})`);
                return;
            }

            fs.stat("/tmp/" + fileName, function(err) {
                if(err === null) {
                    console.log(`File ${fileName} exists locally.`);
                    callback();
                } else if(err.code === 'ENOENT') {
                    let output = fs.createWriteStream("/tmp/" + fileName);

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
                            console.log(`file ${fileName} downloaded.`);
                            callback();
                        }
                    });
                } else {
                    console.log('Unexpected error: ', err.code);
                }
            });
        });
    }

    static parseStatus(status) {
        if (status) {
            return {
                code: status.substring(0, 3),
                msg: status.substring(4)
            };
        }

        return {
            code: '500',
            msg: 'Error fetching subtitle'
        };
    }
}

module.exports = OpenSubtitles;
