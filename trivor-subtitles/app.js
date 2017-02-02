const express = require('express');
const process = require('process');
const Subtitle = require('./lib/subtitle.js');
const config = require('config');
const trivorSubtitles = config.get('TrivorSubtitles');
const Queue = require('./lib/queue.js');
const Storage = require('./lib/storage.js');
const eureka = require('./lib/eureka.js');

const app = express();
const engineQ = new Queue(config.get('SQS.engine_url'));
const subtitlesQ = new Queue(config.get('SQS.subtitles_url'));

app.post('/subtitles', (req, res) => {
  if (!req.body.imdbId) {
    res.status(400).send('imdbId is required');
  }

  const subtitle = new Subtitle(req.body.imdbId, subtitlesQ);

  subtitle.load((message) => {
    res.send(message);
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

var cleanUp = function() {
  if (config.get('Env') !== 'development') {
    eureka.stop();
  }
  process.exit();
};

//executed when the process is finished
process.on('exit', cleanUp);

//catches ctrl+c event
process.on('SIGINT', cleanUp);

//catches uncaught exceptions
process.on('uncaughtException', cleanUp);

setInterval(() => {
  subtitlesQ.receiveMessage((messages) => {
    if (messages) {
      messages.forEach((message) => {
        var subtitle = JSON.parse(message.Body);
        Subtitle.fetch(subtitle, Subtitle.getFileName(subtitle), () => {
          console.log('file ' + Subtitle.getFileName(subtitle) + ' downloaded.');
          var storage = new Storage(subtitle);
          storage.upload(() => {
            console.log('file ' + Subtitle.getFileName(subtitle) + ' uploaded.');
            engineQ.sendMessage(JSON.stringify(subtitle));
          });
        });
      });
    }
  });
}, 20000);


module.exports = app;
