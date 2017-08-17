"use strict";

/**
 * Module dependencies.
 */

const AWS = require('aws-sdk');
AWS.config.loadFromPath('./config.json');
const sqs = new AWS.SQS({ apiVersion: '2012-11-05', correctClockSkew: true });
const logger = require('winston');

/**
 * AWS SQS client
 * 
 */
class Queue {

    /**
     * Queue constructor.
     *
     * @param {String} url
     * @api private
     */
    constructor(url) {
        this.url = url;
    }

    /**
     * Sends a message.
     *
     * @param {String} body
     * @api public
     */
    sendMessage(body) {
        const params = {
            DelaySeconds: 10,
            MessageBody: body,
            QueueUrl: this.url
        };

        sqs.sendMessage(params, (err, data) => {
            if (err) {
              logger.error("An error occurred while sending message to queue", err);
            } else {
              logger.info(`Message sent to queue (${data.MessageId})`);
            }
        });
    }

    /**
     * Fetchs a message from the queue.
     *
     * @param {Function} callback
     * @api public
     */
    receiveMessage(callback) {
        const params = {
            AttributeNames: [
                "SentTimestamp"
            ],
            MaxNumberOfMessages: 1,
            MessageAttributeNames: [
                "All"
            ],
            QueueUrl: this.url,
            VisibilityTimeout: 0,
            WaitTimeSeconds: 0
        };

        sqs.receiveMessage(params, (err, data) => {
            if (err) {
              logger.error("An error occurred while receiving a message", err);
            }
            callback(data.Messages);
        });
    }

    /**
     * Removes a message from the queue.
     *
     * @param {Message} message
     * @api public
     */
    deleteMessage(message) {
        let params = {
            QueueUrl: this.url,
            ReceiptHandle: message.ReceiptHandle
        };
        sqs.deleteMessage(params, function(err, data) {
            if (err) logger.error(err);
            else     logger.info(`Message deleted from queue (${this.requestId})`);
        });
    }
}

/**
 * Module exports.
 */

module.exports = Queue;