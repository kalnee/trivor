package org.kalnee.trivor.client.clients;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import org.kalnee.trivor.client.dto.SubtitleRequestDTO;

@FeignClient(name = "trivor-subtitles", url = "${trivor.subtitles.url}")
public interface SubtitleClient {

  @RequestMapping(method = GET, value = "/info")
  Object getInfo();

  @RequestMapping(method = POST, value = "/subtitles", consumes = "application/json")
  ResponseEntity<Object> create(SubtitleRequestDTO subtitleRequestDTO);

}
