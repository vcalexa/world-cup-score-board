# Live Football World Cup Scoreboard Library

## Overview

This is a simple Java library that provides functionality for managing a live football World Cup scoreboard. The library
allows you to start new matches, update scores, finish matches, and get a summary of ongoing matches ordered by total
score and most recently started.

## Table of Contents

- [Usage](#usage)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Operations](#operations)
- [Design Decisions](#design-decisions)

## Usage

### Prerequisites

- Java 17 which is an LTS version or higher

### Installation

You can include this library in your project by adding the JAR file to your classpath or by including the source code
directly.

### Operations

Operations are the following:

- create a game
- update a game
- finish a game
- list scoreboard of active games sorted by total score and recency

An example usage is provided with useful comments in the Example class inside the test package

#### Using the source code

1. Clone this repository:

   ```bash
   git clone https://github.com/vcalexa/world-cup-score-board.git

build the maven project

#### Design Decisions

1. The library uses an in-memory storage solution for simplicity. The state of ongoing matches is stored in a
   ConcurrentHashMap to insure thread safety.
2. It is assumed that a score can be updated to a smaller value, due to revoked goals(eg after VAR) so no validation
   will be made in this direction
2. Used Thread.sleep() in tests to avoid same values for LocalDateNow.now() values
   instead of using Clock for simplicity
3. In this implementation, the decision has been made to keep the `getActiveGames()` method within
   the `WorldCupScoreBoardService` class, rather than extracting it into a separate service class.
4. The `getActiveGames()` method is tightly related to the responsibilities of the `WorldCupScoreBoardService`. It
   directly leverages the internal state of the service, accessing the `games` list.
5. While the Single Responsibility Principle (SRP) suggests separating concerns, the decision here prioritizes
   simplicity and direct access to the required state.

