const express = require('express');
const Subtitle = require('./lib/subtitle.js');
const config = require('config');
const Queue = require('./lib/queue.js');
const Storage = require('./lib/storage.js');

const app = express();
const engineQ = new Queue(config.get('SQS.engine_url'));
const subtitlesQ = new Queue(config.get('SQS.subtitles_url'));

app.get('/subtitles/:imdbId', (req, res) => {
  const subtitle = new Subtitle(req.params.imdbId, subtitlesQ);

  subtitle.load((message) => {
    res.send(message);
  });
});

app.listen(3000, () => {
  console.log('Listening on port 3000!');
});

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
