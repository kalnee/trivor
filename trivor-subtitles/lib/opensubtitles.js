"use strict"

/**
 * Module dependencies.
 */

const xmlrpc = require('xmlrpc');
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
     *
     * @param {Number} _imdbId
     * @param {Number} _season
     * @param {Number} _episode
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
     * Searchs and returns a subtitle.
     *
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
            client.methodCall('SearchSubtitles', [token, queries, { limit: 1 }], (error, subtitles) => {
                if (error)
                    throw error;

                _callback(subtitles.data[0]);
            });
        });
    }
}

/**
 * Module exports.
 */

module.exports = OpenSubtitles;
