"use strict";

/**
 * Module dependencies.
 */

const AWS = require('aws-sdk');
const fs = require('fs');
const config = require('config');
const Subtitle = require('./subtitle.js');
const logger = require('winston');

AWS.config.loadFromPath('./config.json');
const s3 = new AWS.S3({ apiVersion: '2006-03-01' });

/**
 * AWS S3 Bucket client
 *
 */
class Storage {
	/**
     * Storage constructor.
     *
     * @param {Subtitle} subtitle
     * @api private
     */
	constructor(subtitle) {
		this.subtitle = subtitle;
		this.fileName = Subtitle.getFileName(this.subtitle);
	}

	/**
		 * Checks if an object exists on S3
		 *
		 * @param {Function} callback
		 * @api public
		 */
	exists(callback) {
		let params = {
			Bucket: config.get('S3.bucket'),
			Key: this.fileName
		};

		s3.headObject(params, function(err, data) {
			if (err)  {
				callback(false);
			} else {
				logger.info(`Object found on S3: ${params.Key}`);
        callback(true);
      }
		});
	}

	/**
     * Uploads a file to the S3 bucket
     *
     * @param {Function} callback
     * @api public
     */
	upload(callback) {
		let headParams = {
			Bucket: config.get('S3.bucket'),
			Key: this.fileName
		};
		let uploadParams = {
			Bucket: config.get('S3.bucket'),
			Key: this.fileName,
			Body: ''
		};

		let fileStream = fs.createReadStream('/tmp/' + this.fileName);
		fileStream.on('error', (err) => {
			logger.error('File Error', err);
		});

		uploadParams.Body = fileStream;
		s3.headObject(headParams, function(err, data) {
			if (err)  {
				s3.upload(uploadParams, (err, data) => {
					if (err) {
            logger.error("Error", err);
					} if (data) {
            logger.info("Upload Success", data.Location);
						callback(false);
					}
				});
			} else {
        logger.info(`Object found on S3: ${headParams.Key}`);
				callback(true);
			}
		});
	}
}

/**
 * Module exports.
 */

module.exports = Storage;
