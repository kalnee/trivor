<h1 align="center">trivor-insights</h1>

<h5 align="center">REST API to detect and generate insights over spoken language found in Movies and TV Shows.</h5>

## Prerequisites

- Docker or...
- Java 8+

## Usage

`$ ./scripts/start`: Spins up the `trivor-insights` REST API and mongodb containers. The API is accessible on `http://localhost:8080`.

## Endpoints

`GET /insights/search/findByImdbId?imdbId=:imdbId`

Return all insights for a Movie or TV Show episodes filtered by `imdbId`.

`GET http://localhost:8080/insights/search/findByImdbIdAndSubtitleId?imdbId=:imdbId&subtitleId=:subtitleId`

Return all insights for a Movie or TV Show filtered by `imdbId` and `subtitleId`.

## Insights

The list of current insights is:

- number-of-sentences: `Integer`
- frequent-adjectives: `Map<String, Integer>`
- frequent-superlative: `Map<String, Integer>`
- frequent-comparative: `Map<String, Integer>`
- frequent-nouns: `Map<String, Integer>` 
- frequent-sentences: `Map<String, Integer>`
- pace: `String` Values: SLOW, MODERATE, FAST, SUPER_FAST
- simple-present: `List<String>`
- simple-past: `List<String>`
- simple-future: `List<String>`
- present-progressive: `List<String>`
- past-progressive: `List<String>`
- future-progressive: `List<String>`
- present-perfect: `List<String>`
- past-perfect: `List<String>`
- future-perfect: `List<String>`
- non-sentences: `List<String>`
- mixed-sentences: `List<String>`

See JSON [example](https://github.com/kalnee/trivor/tree/master/trivor-insights/docs/examples/insights.json).