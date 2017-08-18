"use strict";

const express = require('express');
const router = express.Router();
const config = require('config');
const Subtitle = require('./lib/subtitle.js');
const Queue = require('./lib/queue.js');
const subtitlesQ = new Queue(config.get('SQS.subtitles_url'));
const logger = require('winston');

router.route('/subtitles').post((req, res) => {
  if (!req.body || !req.body.imdbIds) {
    res.status(400).json({error: "imdbIds is required"});
    return;
  }

  for (let imdbId of req.body.imdbIds) {
    const subtitle = new Subtitle(imdbId, req.body.resend, subtitlesQ);

    subtitle.load((err, message) => {
      if (err) {
        logger.error(`A problem occurred while loading the subtitle for ${imdbId}`);
      }
      logger.info(message);
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