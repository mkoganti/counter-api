# Counter API

## Overview
Given an internal paragraph loaded within project, couple of APIs have been written to perform word search.

## Project details and Component Diagrams
- Confluence : <link to detail/design page>
- JIRA : <link to EPIC/STORY>

## API Details
The work flow of counter-api has 2 APIs

#### 1. Word Count

- POST
- /counter-api/search
- Consumes JSON data with words desired to be searched for frequency.
- Produces count of occurrence of desired words in JSON format
- Produces Error response(code and message) in case of exception

#### 2. TOP Count Words

- GET
- /counter-api/top/{count}
- Picks up the number designating TOP records to be displayed from PATH-VARIABLE
- Produces CSV output with '|' delimier
- Produces Error response(code and message) in case of exception

#### GIT Repo
- Repo : https://github.com/mkoganti/counter-api-v1.git

#### Maven Cmd
- mvn clean install
- mvn clean install -DskipTests

### Pointers to check before committing
- Unit-tests are written
- Test coverage minimum of 80%
- README is updated
- Meaningful logging has been added

#### Instructions To Run
- Start the APP as a standalone SPRING-BOOT application.
- Got to Postman and execute the following sets:
-- http://localhost:8081/counter-api/top/5
-- http://localhost:8081/counter-api/search

Add Header : Authorization = Basic b3B0dXM6Y2FuZGlkYXRlcw==
Please find attached the executed results SCREEN SHOTS uploaded in git-repo. 

