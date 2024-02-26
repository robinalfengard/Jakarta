@Entity klass som kan lagras i en databas tabell. Tex, Person, Movie, Meeting…

• Repositorieklass med koppling till databas.

• Implementation för Rest api med CRUD funktioner för entiteten. (Create, Read, Update,
Delete)

• Informationen ska skickas som json mellan klient och server.

• DTO ska användas, vi vill alltså inte serializera och deserializera entity klasser direkt.

• Felhantering och meningsfulla Response koder ska användas.
How to handle Exceptions in JAX-RS applications – Mastertheboss

• Validering av inkommande data med hjälp av
Jakarta Bean Validation - Home

• Tester inkluderande tester för endpoints.

• Dockerfile för att enkelt starta applikationen som docker container.

• Docker-compose fil för att kunna starta databas + wildfly med vår applikation.
