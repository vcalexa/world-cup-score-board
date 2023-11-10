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

