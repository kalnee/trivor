const express = require('express');
const bodyParser = require('body-parser');
const process = require('process');
const Subtitle = require('./lib/subtitle.js');
const config = require('config');
const trivorSubtitles = config.get('TrivorSubtitles');
const Queue = require('./lib/queue.js');
const Storage = require('./lib/storage.js');
const eureka = require('./lib/eureka.js');
const OpenSubtitles = require('./lib/opensubtitles.js');

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
const engineQ = new Queue(config.get('SQS.engine_url'));
const subtitlesQ = new Queue(config.get('SQS.subtitles_url'));

app.post('/subtitles', (req, res) => {
    if (!req.body || !req.body.imdbId) {
        res.status(400).send('imdbId is required');
    }

    const subtitle = new Subtitle(req.body.imdbId, subtitlesQ);

    subtitle.load((err, message) => {
        if (err) {
            res.status(400).send({error: 'a problem occurred while loading the subtitle'});
        } else {
            res.send(message);
        }
    });
});

app.get('/info', (req, res) => {
    res.send({
        status: "UP"
    });
});

app.listen(trivorSubtitles.port, () => {
    console.log(`Listening on port ${trivorSubtitles.port}`);
    if (config.get('Env') !== 'development') {
        eureka.start();
    }
});

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

setInterval(() => {
    subtitlesQ.receiveMessage((messages) => {
        if (messages) {
            messages.forEach((message) => {
                let subtitle = JSON.parse(message.Body);
                OpenSubtitles.fetch(subtitle, Subtitle.getFileName(subtitle), () => {
                    let storage = new Storage(subtitle);
                    storage.upload(() => {
                        console.log(`file ${Subtitle.getFileName(subtitle)} uploaded locally.`);
                        let message = JSON.stringify(subtitle);
                        console.log(`message sent to engine queue: ${message}`);
                        engineQ.sendMessage(message);
                    });
                });
            });
        }
    });
}, 20000);


module.exports = app;
