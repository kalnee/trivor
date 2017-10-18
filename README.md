# trivor [![CircleCI](https://circleci.com/gh/kalnee/trivor.svg?style=svg)](https://circleci.com/gh/kalnee/trivor)

<p align="center">
<img src="https://github.com/kalnee/trivor/blob/master/docs/logo/transparent-purple.png" width="300">
</p>

<h5 align="center">Trivor is a REST API that provides useful insights into spoken language found in Movies and TV Shows. By leveraging the use of NLP (Natural Language Processing), Trivor is capable of identifying context from subtitle files which has several applications, mainly as a language learning content provider.</h5>

## Prerequisites

- Java 8+
- NodeJS 6.5+
- AWS IAM user

## Usage (To Be Improved)

- `> docker-compose -f gateway/src/main/docker/jhipster-registry.yml up`
- `> docker-compose -f gateway/src/main/docker/mysql.yml up`
- `> docker-compose -f insights/src/main/docker/mongodb.yml up`
- `> cd gateway` and `./mvnw`
- `> cd insights` and `./mvnw`

## Architecture

![diagram](https://github.com/kalnee/trivor/blob/master/docs/architecture/architecture-diagram.png?raw=true)

Trivor is composed by the following Microservices and libraries:

- [gateway](https://github.com/kalnee/trivor/tree/master/gateway)
- [insights](https://github.com/kalnee/trivor/tree/master/insights)
- [subtitles](https://github.com/kalnee/trivor/tree/master/subtitles)

Microservice Registration and Discovery with JHipster Registry Docker image:

- [jhipster-registry](https://github.com/kalnee/trivor/blob/master/gateway/src/main/docker)

Natural Language Processing lib to parse and generate insights from subtitles:

- [trivor-nlp](https://github.com/kalnee/trivor-nlp)

## License

MIT (c) Kalnee. See [LICENSE](https://github.com/kalnee/trivor/blob/master/LICENSE.md) for details.
