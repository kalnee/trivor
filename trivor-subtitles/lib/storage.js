"use strict"

/**
 * Module dependencies.
 */

const AWS = require('aws-sdk');
const fs = require('fs');
const config = require('config');
const Subtitle = require('./subtitle.js');

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
     * Uploads a file to the S3 bucket
     *
     * @param {Function} callback
     * @api public
     */
	upload(callback) {
		let uploadParams = { 
			Bucket: config.get('S3.bucket'), 
			Key: this.fileName, 
			Body: '' 
		};
		
		let fileStream = fs.createReadStream('/tmp/' + this.fileName);
		fileStream.on('error', (err) => {
			console.log('File Error', err);
		});

		uploadParams.Body = fileStream;

		s3.upload(uploadParams, (err, data) => {
			if (err) {
				console.log("Error", err);
			} if (data) {
				console.log("Upload Success", data.Location);
				callback();
			}
		});
	}
}

/**
 * Module exports.
 */

module.exports = Storage;