# Video series app

<ins>2022/12/09</ins> - Spring Boot Release 3

Spring Boot 3.0.0, albeit being a fantastic release, it did forced to rethink the whole project.

For a quick fix, I'll try first to update from Mongo to Posgres and see if that works.

As mention in Axon
Framework [website](https://docs.axoniq.io/reference-guide/v/3.3/part-iii-infrastructure-components/repository-and-event-store#mongodb-event-storage-engine),
from Axon Version 3 onwards it is no longer advisable to use Mongo as the event store database.

2022/09/19

- [Axon Framework issue 2365](https://github.com/AxonFramework/AxonFramework/issues/2365#event-7412299709)
- [Notice on Mongo Extensions](https://docs.axoniq.io/reference-guide/extensions/mongo)

New Goals:

- Remove `Mongo`
- Axon's `JacksonSerializer`

## Roadmap to revision 2.0.0

2021/08/21

- Audit tables for `association_value_entry`, `saga_entry` and `token_entry` (not continued - off-scope)

- 2021/08/19
- Issues file creation
- Pipeline segregation
- Branch with faulty record example
- Kotest
- Mockk
- Command/Query separation made clear

2021/08/17 - Integration test

- E2E Spring Integration test

2021/08/14 - Virtualization

- Dockerize project
- Test Containers

2021/08/11 Baseline upgrade

- Coverage
- Upgrade to latest Axon libraries
- Open issues - [Why H2 and UUID's explanation - Issue 1](https://gitlab.com/jesperancinha/video-series-app/-/issues/1)

- Mongo in Ubuntu and Windows

2021/08/09 Migration to JDK 16

## Roadmap to v1.0.0

2021/06/18 - Release 1.0.0

