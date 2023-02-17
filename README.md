JUnit 5 Playwright Demo
---

> A JUnit 5 based demo project for Playwright

## Clone the repository

    git clone https://github.com/kolorobot/junit5-playwright-todomvc-demo.git

## Check JUnit configuration

    ./src/test/resources/junit-platform.properties

## Run Playwright Codegen

    ./gradlew playwright --args="codegen https://todomvc.com/examples/vanillajs"
    ./gradlew playwright --args="codegen --browser=firefox https://todomvc.com/examples/vanillajs"
    
## Run tests

    ./gradlew clean test
    ./gradlew clean test --tests TodoMvcTests
    ./gradlew clean test -PtagsInclude=tag1 -PtagsExclude=tag2

## Upgrade Gradle Wrapper

    ./gradlew wrapper --gradle-version=<VERSION>
