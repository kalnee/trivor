"use strict";

/**
 * Module dependencies.
 */

const request = require('request');
const config = require('config');

/**
 * Module constants
 */

const _apiKey = process.env.THE_MOVIE_DB_KEY;
const _version = config.get('TheMovieDB.version');
const _url = config.get('TheMovieDB.url');
const _language = config.get('TheMovieDB.language');
const _endpoints = { find: 'find', tv: 'tv', movie: 'movie' };

/**
 * TheMovieDB API client
 * 
 */
class TheMovieDB {
    /**
     * TheMovieDB constructor.
     *
     * @param {Number} _imdbId
     * @api private
     */
    constructor(apiKey) {
        this.apiKey = apiKey;        
    }

    /**
     * Returns the url of an endpoint.
     *
     * @param {Number} _id
     * @param {String} _method
     * @api private
     */
    getUrl(_id, _method) {
        let _endpoint = _url + '/' + _version + '/' + _method + '/' + _id + '?api_key=' + _apiKey + '&language=' + _language;
        if (_method === 'find') {
            _endpoint += '&external_source=imdb_id';
        }
        return _endpoint;
    }

    /**
     * Calls the find method of the API
     *
     * @param {Number} _id
     * @param {Function} callback
     * @api public
     */
    find(_id, callback) {
        request(this.getUrl(_id, _endpoints.find), (error, response, body) => {
            if (error || response.statusCode !== 200)
                throw error;
            
            callback(JSON.parse(body));
        });
    }

    /**
     * Calls the tv method of the API
     *
     * @param {Number} _id
     * @param {Function} callback
     * @api public
     */
    tv(_id, callback) {
        request(this.getUrl(_id, _endpoints.tv), (error, response, body) => {
            if (error || response.statusCode !== 200) 
                throw error;
            
            callback(JSON.parse(body));
        });
    }
}

/**
 * Module exports.
 */

module.exports = TheMovieDB;
