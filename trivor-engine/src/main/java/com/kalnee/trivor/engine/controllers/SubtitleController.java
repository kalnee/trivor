package com.kalnee.trivor.engine.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalnee.trivor.engine.models.SubtitleModel;
import com.kalnee.trivor.engine.repositories.SubtitleRepository;

@RestController
public class SubtitleController {

  private final SubtitleRepository repository;

  @Autowired
  public SubtitleController(SubtitleRepository repository) {
    this.repository = repository;
  }

  @RequestMapping(path = "/subtitles", method = GET)
  public List<SubtitleModel> list() {
    return repository.findAll();
  }
}
