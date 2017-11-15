var cheerio = require('cheerio');
var request = require('request');
var fs = require('fs');

var resultsUrl = "http://www.bbc.com/sport/football/premier-league/scores-fixtures/2017-08";

request(resultsUrl, function (error, response, html) {
    if (error) {
        console.log(`Failed to access ${resultsUrl}`);
    }

    console.log(`Successfully accessed ${resultsUrl}`);

    var text = "";
    var urls = [];
    var $ = cheerio.load(html);

    $('a.sp-c-fixture__block-link').each(function () {
        var url = $(this).attr('href');
        urls.push(`http://www.bbc.com${url}`);
    });

    console.log(`Urls found: ${urls.length}`);
    var re = /(.*)(\.\.[\.]*)(.*)/g;
    var re2 = /\.([A-Za-z]+)/g;

    urls.forEach(function (url) {
        console.log(url);
        request(url, function (error, response, html) {
            var $ = cheerio.load(html);
            $('.lx-stream-post-body p').each(function () {
                var sentence = $(this).text();
                if (sentence.indexOf('Video Review') === -1) {
                    if (sentence.length > 1) {
                        text += sentence + "\n";
                    }
                }
            });
            save();
        });
    });

    var count = 0;

    function save() {
        ++count;
        if (count === urls.length) {
            fs.writeFile("./content/tr-football-bbcpl1718", text, {flag: 'w'}, function (err) {
                if (err) {
                    return console.log(err);
                }
                console.log("The file was saved!");
            });
        }
    }
});
