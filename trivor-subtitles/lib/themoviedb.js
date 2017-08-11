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
     * @api private
     */
    constructor() {
        if (!_apiKey) {
          console.error("THE_MOVIE_DB_KEY env var is not set");
        }
    }

    /**
     * Returns the base url of TMDb.
     *
     * @api private
     */
    getBaseUrl() {
      return `${_url}/${_version}`;
    }

    /**
     * Returns the url of a TMDb endpoint.
     *
     * @param {Number} _id
     * @param {String} _resource
     * @api private
     */
    getUrl(_id, _resource) {
        let _endpoint = `${this.getBaseUrl()}/${_resource}/${_id}?api_key=${_apiKey}&language=${_language}`;
        if (_resource === 'find') {
            _endpoint += '&external_source=imdb_id';
        }
        return _endpoint;
    }

    /**
     * Returns the url of the keywords endpoint of TMDb.
     *
     * @param {String} _resource
     * @param {Number} _id
     * @api private
     */
    getKeywordsUrl(_resource, _id) {
      return `${this.getBaseUrl()}/${_resource}/${_id}/keywords?api_key=${_apiKey}`;
    }

    /**
     * Calls the find endpoint of TMDb
     *
     * @param {Number} _id
     * @param {Function} callback
     * @api public
     */
    find(_id, callback) {
        request(this.getUrl(_id, _endpoints.find), (error, response, body) => {
            if (error || response.statusCode !== 200) {
                let err = `a problem occurred when calling themoviedb.\nStatus: ${response.statusCode}`;
                if (!error)  err = err + `\nError: ${error}`;
                console.error(err);
            }

            callback(error, JSON.parse(body));
        });
    }

    /**
     * Calls the tv endpoint to get more details
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

    /**
     * Calls the movie endpoint to get more details
     *
     * @param {Number} _id
     * @param {Function} callback
     * @api public
     */
    movie(_id, callback) {
        request(this.getUrl(_id, _endpoints.movie), (error, response, body) => {
            if (error || response.statusCode !== 200)
                throw error;

            callback(JSON.parse(body));
        });
    }

  /**
   * Calls the movie keywords endpoint
   *
   * @param {Number} _id
   * @param {String} _resource
   * @param {Function} callback
   * @api public
   */
  keywords(_resource, _id, callback) {
    request(this.getKeywordsUrl(_resource, _id), (error, response, body) => {
      if (error || response.statusCode !== 200)
        throw error;

      callback(JSON.parse(body));
    });
  }
}

module.exports = TheMovieDB;
