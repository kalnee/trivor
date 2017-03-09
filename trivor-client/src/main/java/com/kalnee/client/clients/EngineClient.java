package com.kalnee.client.clients;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("trivor-engine")
public interface EngineClient {

  @RequestMapping(method = GET, value = "/insights/search/findByImdbId")
  Object getInsightsByImdb(@RequestParam("imdbId") String imdbId);
}
