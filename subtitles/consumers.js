"use strict";

const Subtitle = require('./lib/subtitle.js');
const config = require('config');
const Queue = require('./lib/queue.js');
const Storage = require('./lib/storage.js');
const OpenSubtitles = require('./lib/opensubtitles.js');
const insightsQ = new Queue(config.get('SQS.insights_url'));
const subtitlesQ = new Queue(config.get('SQS.subtitles_url'));
const logger = require('winston');

const consumers = {
    receiveSubtitleMessage: function () {
        setInterval(() => {
            subtitlesQ.receiveMessage((messages) => {
                if (!messages) {
                    return;
                }

                messages.forEach((message) => {
                    let subtitle = JSON.parse(message.Body);
                    let storage = new Storage(subtitle);
                    storage.exists((fileExists) => {
                      if (fileExists) {
                        sendInsightsMessage(subtitle, message,  fileExists);
                      } else {
                        OpenSubtitles.fetch(subtitle, Subtitle.getFileName(subtitle), () => {
                          storage.upload((subtitleExists) => {
                            logger.info(`File ${Subtitle.getFileName(subtitle)} uploaded locally.`);
                            sendInsightsMessage(subtitle, message, subtitleExists);
                          });
                        });
                      }

                    });
                });

            });
        }, config.get('SQS.consumer_interval'));
    }
};

let sendInsightsMessage = function(subtitle, message, subtitleExists) {
  let subtitleAsString = JSON.stringify(subtitle);
  if (!subtitleExists || subtitle.resend) {
    delete subtitleAsString.resend;
    logger.info(`Message sent to insights queue: ${subtitleAsString}`);
    insightsQ.sendMessage(subtitleAsString);
    subtitlesQ.deleteMessage(message);
  } else {
    logger.info(`message NOT sent to insights queue: ${message}`);
  }
};

module.exports = consumers;