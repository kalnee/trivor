"use strict";

const Subtitle = require('./lib/subtitle.js');
const config = require('config');
const Queue = require('./lib/queue.js');
const Storage = require('./lib/storage.js');
const OpenSubtitles = require('./lib/opensubtitles.js');
const engineQ = new Queue(config.get('SQS.engine_url'));
const subtitlesQ = new Queue(config.get('SQS.subtitles_url'));

const consumers = {
    receiveSubtitleMessage: function () {
        setInterval(() => {
            subtitlesQ.receiveMessage((messages) => {
                if (!messages) {
                    return;
                }

                messages.forEach((message) => {
                    let subtitle = JSON.parse(message.Body);
                    OpenSubtitles.fetch(subtitle, Subtitle.getFileName(subtitle), () => {
                        let storage = new Storage(subtitle);
                        storage.upload((subtitleExists) => {
                            console.log(`file ${Subtitle.getFileName(subtitle)} uploaded locally.`);
                            let message = JSON.stringify(subtitle);
                            if (!subtitleExists || subtitle.resend) {
                                delete message.resend;
                                console.log(`message sent to engine queue: ${message}`);
                                engineQ.sendMessage(message);
                            } else {
                                console.log(`message NOT sent to engine queue: ${message}`);
                            }
                        });
                    });
                });

            });
        }, 20000);
    }
};

module.exports = consumers;