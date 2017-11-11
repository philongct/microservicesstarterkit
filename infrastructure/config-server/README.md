#Overview

Store configs for all microservices (including Gateway, Service Registry). The respective microservice picks up the configuration based on a `<spring.application.name>.yml` file defined in the configuration by matching the service's `spring.application.name` property defined in the `bootstrap.yml` file.
Properties in application.yml apply for all microservices