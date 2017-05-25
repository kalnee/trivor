"use strict";

const express = require('express');
const router = express.Router();
const config = require('config');
const Subtitle = require('./lib/subtitle.js');
const Queue = require('./lib/queue.js');
const subtitlesQ = new Queue(config.get('SQS.subtitles_url'));

router.route('/subtitles').post((req, res) => {
    if (!req.body || !req.body.imdbId) {
        res.status(400).send('imdbId is required');
    }

    const subtitle = new Subtitle(req.body.imdbId, req.body.resend, subtitlesQ);

    subtitle.load((err, message) => {
        if (err) {
            res.status(400).send({error: 'a problem occurred while loading the subtitle'});
        } else {
            res.send(message);
        }
    });
});

router.route('/info').get((req, res) => {
    res.send({
        status: "UP"
    });
});

module.exports = router;