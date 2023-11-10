# Live Football World Cup Scoreboard Library

## Overview

This is a simple Java library that provides functionality for managing a live football World Cup scoreboard. The library
allows you to start new matches, update scores, finish matches, and get a summary of ongoing matches ordered by total
score and most recently started.

## Table of Contents

- [Usage](#usage)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Example Usage](#example-usage)
- [Design Decisions](#design-decisions)
- [Contributing](#contributing)
- [License](#license)

## Usage

### Prerequisites

- Java 17 which is an LTS version or higher

### Installation

You can include this library in your project by adding the JAR file to your classpath or by including the source code
directly.

#### Using the JAR file

1. Download the [latest release](#) of the JAR file.
2. Include the JAR file in your project's dependencies.
3. Operations are the following:

- create a game
- update a game
- finish a game
- list scoreboard of active games sorted by total score and recency

4. An example usage is provided with comments in the Example class inside the test package
5. Test coverage is 100% class, 88% method and 94% line coverage
6. In this implementation, the decision has been made to keep the `getActiveGames()` method within
   the `WorldCupScoreBoardService` class, rather than extracting it into a separate service class. Below are the reasons
   behind this design choice:

7. The `getActiveGames()` method is tightly related to the responsibilities of the `WorldCupScoreBoardService`. It
   directly leverages the internal state of the service, accessing the `games` list. Placing it in the same class
   ensures a cohesive and streamlined implementation.

8. Being part of the same class allows the method direct access to the `games` list. This direct access simplifies the
   implementation and enhances performance by avoiding unnecessary method calls or additional layers of abstraction.

9. Maintaining the method within the `WorldCupScoreBoardService` contributes to code simplicity. There's no need to
   introduce an additional service class for a single method, keeping the overall structure clear and straightforward.

10. While the Single Responsibility Principle (SRP) suggests separating concerns, the decision here prioritizes
    simplicity and direct access to the required state. The application's specific requirements and the desire to keep
    the implementation straightforward played a crucial role in this design choice.

#### Using the source code

1. Clone this repository:

   ```bash
   git clone https://github.com/vcalexa/world-cup-score-board.git

build the maven project

#### Design Decisions

The library uses an in-memory storage solution for simplicity. The state of ongoing matches is stored in a simple List.
Used Thread.sleep in tests to avoid same values for LocalDateNow.now() values
instead of using Clock for simplicity
Other concurrence mechanisms such as synchronized were omitted for simplicity 

