"use strict";

const express = require('express');
const bodyParser = require('body-parser');
const process = require('process');
const config = require('config');
const trivorSubtitles = config.get('TrivorSubtitles');
const eureka = require('./lib/eureka.js');
const routes = require('./routes');
const consumers = require('./consumers');
const logger = require('winston');

logger.add(logger.transports.File, { filename: `${config.get('Env')}.log` });

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.listen(trivorSubtitles.port, () => {
    logger.info(`Listening on port ${trivorSubtitles.port}`);
    if (config.get('Env') !== 'development') {
        eureka.start();
    }
});

app.use('/api', routes);
consumers.receiveSubtitleMessage();

let cleanUp = function () {
    if (config.get('Env') !== 'development') {
        eureka.stop();
    }
    process.exit();
};

let handleError = function (error, e) {
    console.error(error);
    cleanUp();
};

//executed when the process is finished
process.on('exit', cleanUp);

//catches ctrl+c event
process.on('SIGINT', cleanUp);

//catches uncaught exceptions
process.on('uncaughtException', handleError);

module.exports = app;
