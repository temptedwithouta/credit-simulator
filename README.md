# Credit Simulator

A Java command-line application that simulates vehicle loan installments. This portfolio project demonstrates object-oriented Java, separation of application responsibilities, automated tests, Docker packaging, and a GitHub Actions delivery pipeline.

## Features

- Simulate vehicle loans for cars and motorcycles, with new and used vehicle rules.
- Calculate yearly installment amounts with changing interest rates.
- Validate loan requests before calculating.
- Accept interactive input or multiple requests from a text file.
- Load a sample loan request from an HTTP JSON API and save interactive results to a text file.
- Build and publish a Docker image through GitHub Actions.

## Java Structure

```text
src/main/java/org/example/
├── Main.java
├── controller/   # Coordinates menu, input, and application services
├── model/        # Loan request and calculation result data
├── service/      # Loan calculation, validation, and HTTP/JSON API access
└── view/         # Console output and result formatting

src/test/java/
├── ApiServiceTest.java
├── CalculationServiceTest.java
└── ValidationServiceTest.java
```

The controllers coordinate user interaction and service calls. Calculation and validation rules are kept in services, while the models represent requests and results. `ApiService` retrieves JSON over HTTP and maps it to a loan request.

## Requirements

- Java 25
- Maven 3.9 or later
- Docker, for container builds and runs
- Bash, for the `credit_simulator.sh` launcher

## Run

Start the interactive CLI from a Bash-compatible shell:

```bash
./credit_simulator.sh
```

To process the included sample input file:

```bash
./credit_simulator.sh file_input.txt
```

Each non-empty line uses this format:

```text
<VehicleType>#<Condition>#<Year>#<TotalLoanAmount>#<TenorYears>#<DownPayment>
```

For example:

```text
Mobil#Baru#2025#100000000#5#35000000
Motor#Bekas#2020#80000000#3#25000000
```

The interactive menu supports calculating a loan, loading a sample request from the API, and exiting. A completed interactive calculation can also be appended to a text file.

## Test and Package

Run the test suite and Maven verification lifecycle:

```bash
mvn -B clean verify
```

There are 14 tests across the calculation, validation, and API service test classes. To build the executable assembly JAR:

```bash
mvn -B clean package assembly:single
java -jar target/credit-simulator-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Docker

Build the image locally. The Dockerfile packages the application in a Maven/Java 25 build stage and copies the runnable JAR into a Java 25 JRE runtime image.

```bash
docker build -t credit-simulator:local .
docker run --rm -it credit-simulator:local
```

To process a local input file in the container from a Bash-compatible shell:

```bash
docker run --rm \
    --mount "type=bind,source=$(pwd)/file_input.txt,target=/app/file_input.txt,readonly" \
    credit-simulator:local file_input.txt
```

The published image is available from [Docker Hub](https://hub.docker.com/r/temptedwithouta/credit-simulator):

```bash
docker pull temptedwithouta/credit-simulator:latest
docker run --rm -it temptedwithouta/credit-simulator:latest
```

Each successful publish from `main` also creates a traceable `sha-<commit-sha>` tag.

## CI/CD

The workflow at `.github/workflows/docker-build.yaml` runs on pushes to `main`:

1. Checks out the repository and configures Java 25.
2. Runs `mvn -B clean verify`; image publishing does not proceed if verification fails.
3. Authenticates to Docker Hub using GitHub Actions secrets.
4. Builds one image and publishes it as both `latest` and `sha-<commit-sha>`.

Configure these repository secrets in GitHub before publishing:

- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`

## Author

Daniel Sulistio · [GitHub](https://github.com/temptedwithouta)
