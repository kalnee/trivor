"use strict";

const express = require('express');
const router = express.Router();
const config = require('config');
const Subtitle = require('./lib/subtitle.js');
const Queue = require('./lib/queue.js');
const subtitlesQ = new Queue(config.get('SQS.subtitles_url'));

router.route('/subtitles').post((req, res) => {
  if (!req.body || !req.body.imdbIds) {
    res.status(400).json({error: "imdbIds is required"});
    return;
  }

  for (let imdbId of req.body.imdbIds) {
    const subtitle = new Subtitle(imdbId, req.body.resend, subtitlesQ);

    subtitle.load((err, message) => {
      if (err) {
        console.log(`A problem occurred while loading the subtitle for ${imdbId}`);
      }
      console.log(message);
    });
  }

  res.json({"message": `${req.body.imdbIds.length} items sent to queue`});
});

router.route('/info').get((req, res) => {
  res.send({
    status: "UP"
  });
});

module.exports = router;