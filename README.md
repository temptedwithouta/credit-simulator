# 💳 Credit Simulator

A command-line Java application for simulating vehicle loan (credit) calculation based on **vehicle type**, **condition
**, **year**, **loan amount**, and **down payment**.  
Developed as part of the Backend Developer technical test.

---

## 🧠 Features

- Calculate annual loan simulation for **Mobil** or **Motor**
- Different rules for **Baru** and **Bekas** vehicles
- Dynamic **interest rate increase** every year (0.1% / 0.5%)
- Validation for DP percentage, vehicle year, and tenor
- Support for both **manual input** and **file input**
- Includes **unit tests** for all services:
    - `CalculationService`
    - `ValidationService`
    - `ApiService`
- Fully containerized using **Docker**
- **CI/CD with GitHub Actions** to build & push image to Docker Hub automatically

---

## ⚙️ Project Structure

```

itsme-credit-simulator/
├── credit_simulator                # Executable launcher script (MANDATORY)
├── Dockerfile                      # Docker image definition
├── pom.xml                         # Maven configuration
├── README.md                       # Project documentation
├── src/
│   ├── main/java/org/example/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── service/
│   │   └── Main.java
│   └── test/java/
│       ├── CalculationServiceTest.java
│       ├── ValidationServiceTest.java
│       └── ApiServiceTest.java
└── .github/workflows/docker-build.yaml # CI/CD pipeline
````

---

## 🚀 How to Run the Application

### 🔹 1. Run Without File Input

```bash
./credit_simulator
````

This will start the simulator and ask you to input credit parameters manually (vehicle type, year, price, etc).

---

### 🔹 2. Run With File Input

```bash
./credit_simulator file_inputs.txt
```

**Sample `file_inputs.txt`:**

```
Mobil#Baru#2025#100000000#5#35000000
Motor#Bekas#2023#50000000#3#15000000
```

Each line represents one credit simulation with:

```
<VehicleType>#<Condition>#<Year>#<TotalLoanAmount>#<Tenor>#<DownPayment>
```

**Example Output:**

```
=== Hasil Simulasi Kredit ===
Jenis: Mobil, Kondisi: Baru, Tahun: 2025, Pinjaman: Rp 100.000.000, Tenor: 5 tahun, DP: Rp 35.000.000
Tahun: 1: Rp2.250.000/bulan, Suku Bunga: 8,00%
Tahun: 2: Rp2.432.250/bulan, Suku Bunga: 8,10%
Tahun: 3: Rp2.641.423/bulan, Suku Bunga: 8,60%
===============================
```

---

## 🧪 Running Unit Tests

To execute all unit tests:

```bash
mvn test
```

Tests included:

* `CalculationServiceTest` → verifies per-year loan calculation matches formula
* `ValidationServiceTest` → validates all loan business rules
* `ApiServiceTest` → mocks external API response using Mockito

If successful, you’ll see:

```
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## 🐳 Run with Docker

### 🔹 Build and Run Locally

```bash
docker build -t credit-simulator .
docker run --rm credit-simulator
```

### 🔹 Pull from Docker Hub

```bash
docker pull temptedwithouta/credit-simulator:latest
docker run --rm temptedwithouta/credit-simulator:latest
```

---

## 🤖 CI/CD with GitHub Actions

This project includes a workflow file:
`.github/workflows/docker-build.yaml`

### Pipeline Steps

1. Runs automatically on every push to `main`
2. Builds the Maven project (`mvn clean package assembly:single`)
3. Builds Docker image
4. Pushes image to Docker Hub:

   ```
   https://hub.docker.com/r/temptedwithouta/credit-simulator
   ```

---

## 🧰 Environment Setup

### Requirements

* Java 25 or higher
* Maven 3.9+
* Docker (if building container)
* Linux/Mac/WSL for `credit_simulator` execution script

---

## 📦 Build Manually

If you want to build JAR manually:

```bash
mvn clean package assembly:single
```

The compiled JAR will be located in:

```
target/credit-simulator-1.0-SNAPSHOT-jar-with-dependencies.jar
```

---

## 📄 Notes for Reviewer

* All calculations, validation, and API calls are fully covered by unit tests.
* The `credit_simulator` executable allows direct execution in UNIX-like systems.
* Source code follows MVC structure and Java best practices.

---

## 👤 Author

- **Name:** Daniel Sulistio
- **Position Applied:** Backend Developer (BCA Digital / ITSME)
- **GitHub:** [temptedwithouta](https://github.com/temptedwithouta)
