# trivor [![CircleCI](https://circleci.com/gh/kalnee/trivor.svg?style=svg)](https://circleci.com/gh/kalnee/trivor)

<h5 align="center">Trivor is a REST API that brings valuable information for language learners concerning Movies and TV Shows.</h5>

## Prerequisites

- Java 8+
- NodeJS 6.5+
- AWS IAM user

## Usage

`./gradlew build`

## Architecture

![diagram](https://github.com/kalnee/trivor/blob/danielfc.documentation/docs/architecture/architecture-diagram.png?raw=true)

Trivor is composed by the following Microservices:

- [trivor-subtitle](https://github.com/kalnee/trivor/tree/master/trivor-subtitles)
- [trivor-engine](https://github.com/kalnee/trivor/tree/master/trivor-engine)
- [trivor-client](https://github.com/kalnee/trivor/tree/master/trivor-client)

Microservice Registration and Discovery:

- [trivor-eureka](https://github.com/kalnee/trivor/blob/master/trivor-eureka)

## License

MIT (c) Kalnee. See [LICENSE](https://github.com/kalnee/trivor/blob/master/LICENSE.md) for details.
