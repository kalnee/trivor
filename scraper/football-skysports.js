var cheerio = require('cheerio');
var request = require('request');
var fs = require('fs');

var resultsUrl = "http://www.skysports.com/champions-league-results";

request(resultsUrl, function (error, response, html) {
  if (error) {
    console.log(`Failed to access ${resultsUrl}`);
  }

  console.log(`Successfully accessed ${resultsUrl}`);

  var text = "";
  var urls = [];
  var $ = cheerio.load(html);

  $('.fixres__item a').each(function () {
    var url = $(this).attr('href');
    if (url.indexOf('bet') === -1) {
      var partials = url.replace('http://', '').split('/');
      urls.push(`http://${partials[0]}/${partials[1]}/${partials[2]}/live/${partials[3]}`);
    }
  });

  console.log(`Urls found: ${urls.length}`);
  var re = /(.*)(\.\.[\.]*)(.*)/g;
  var re2 = /\.([A-Za-z]+)/g;

  urls.forEach(function (url) {
    request(url, function (error, response, html) {
      var $ = cheerio.load(html);
      $('.fyre-comment').each(function () {
        var count = 0;
        $(this).children("p").each(function () {
          if (count > 1) {
            var sentence = $(this).text().replace(re, "$1 $3").replace(re2, " $1");
            if (!/\bbet\b/i.test(sentence) && !/\bodds\b/i.test(sentence)) {
                text += sentence + "\n";
            }
          }
          ++count;
        });
      });
      save();
    });
  });

  var count = 0;

  function save() {
    ++count;
    if (count === urls.length) {
      fs.writeFile("./content/tr-football-cl1718", text, {flag: 'w'}, function (err) {
        if (err) {
          return console.log(err);
        }
        console.log("The file was saved!");
      });
    }
  }
});
