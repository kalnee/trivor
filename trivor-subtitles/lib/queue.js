"use strict";

/**
 * Module dependencies.
 */

const AWS = require('aws-sdk');
AWS.config.loadFromPath('./config.json');
const sqs = new AWS.SQS({ apiVersion: '2012-11-05' });

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
                console.log("Error", err);
            } else {
                console.log("Success", data.MessageId);
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
                console.log("Receive Error", err);
            } else {
                if (data.Messages) {
                    let deleteParams = {
                        QueueUrl: this.url,
                        ReceiptHandle: data.Messages[0].ReceiptHandle
                    };
                    sqs.deleteMessage(deleteParams, (err, data) => {
                        if (err) {
                            console.log("Delete Error", err);
                        }
                    });
                }

                callback(data.Messages);
            }
        });
    }
}

/**
 * Module exports.
 */

module.exports = Queue;