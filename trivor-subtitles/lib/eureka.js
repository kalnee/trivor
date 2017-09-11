const config = require('config');
const Eureka = require('eureka-js-client').Eureka;
const eureka = config.get("Eureka");
const trivorSubtitles = config.get('TrivorSubtitles');
const uuidv1 = require('uuid/v1');

const client = new Eureka({
  instance: {
    app: 'subtitles',
    hostName: trivorSubtitles.host,
    ipAddr: trivorSubtitles.host,
    instanceId: `subtitles:${uuidv1()}`,
    statusPageUrl: `http://${trivorSubtitles.host}:${trivorSubtitles.port}/api/info`,
    homePageUrl: `http://${trivorSubtitles.host}:${trivorSubtitles.port}`,
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
