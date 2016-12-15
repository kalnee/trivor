var cheerio = require('cheerio');
var request = require('request');

var urls = ["http://speakspeak.com/resources/vocabulary-general-english/english-irregular-verbs",
    "http://speakspeak.com/resources/vocabulary-general-english/list-irregular-verbs-2",
    "http://speakspeak.com/resources/vocabulary-general-english/list-irregular-verbs-3"
];

request(urls[2], function(error, response, html) {
    if (error) {
        console.log(`Failed to access ${url}`);
    }

    console.log(`Successfully accessed ${url}`);

    var words = [];
    var verbs = [];
    var $ = cheerio.load(html);

    $('table').each(function(i, elem) {
        $(this).children('tbody').children('tr').each(function(i, elem) {
            if ($(this).children('td').length === 3) {
                $(this).children('td').each(function(i, elem) {
                    if ($(this).children('strong').length === 0 && $(this).text().trim().length > 0) {
                        words.push($(this).text());
                    }
                });
            }
        });
    });

    words.forEach(function(word, i) {
        if (i > 0 && i % 3 === 0) {
            verbs.push({
                present: words[(i - 1) - 2],
                simplePast: words[(i - 1) - 1],
                simpleParticiple: words[i - 1]
            })
        }
    });

    console.log(JSON.stringify(verbs));
});
