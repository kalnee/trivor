const config = require('config');
const Eureka = require('eureka-js-client').Eureka;
const eureka = config.get("Eureka");
const trivorSubtitles = config.get('TrivorSubtitles');

const client = new Eureka({
  instance: {
    app: 'trivor-subtitles',
    hostName: trivorSubtitles.host,
    ipAddr: trivorSubtitles.host,
    statusPageUrl: `http://${trivorSubtitles.host}:${trivorSubtitles.port}/info`,
    port: {
      '$': trivorSubtitles.port,
      '@enabled': 'true',
    },
    vipAddress: 'subtitles.trivor.com',
    dataCenterInfo: {
      '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
      name: 'MyOwn',
    },
  },
  eureka: {
    host: eureka.host,
    port: eureka.port,
    servicePath: '/eureka/apps/'
  },
});

/**
 * Module exports.
 */

module.exports = client;
