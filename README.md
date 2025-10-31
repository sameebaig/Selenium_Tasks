## Selenium Automation Framework

### Overview

This **Selenium Framework** built using:

* **Java**
* **TestNG**
* **Page Object Model (POM)**
* **Database Connectivity (MySQL)**
* **Reporting (TestNG Reports)**
* **Config-driven Browser Setup**

The framework automates end-to-end testing for web applications — such as login, sorting, cart validation, and menu navigation.

## Framework Structure

```
Selenium_Tasks/
├── pom.xml                     # Maven dependencies (Selenium, TestNG, etc.)
├── README.md                   # Project documentation
├── testng.xml                  # TestNG suite configuration
│
├── config/
│   └── config.properties        # Browser, URL, credentials, DB info, etc.
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── base/
│   │       │   └── BaseTest.java              # Common setup/teardown logic
│   │       │
│   │       ├── pages/                         # Page Object Model classes
│   │       │   ├── LoginPage.java
│   │       │   ├── DashboardPage.java
│   │       │   └── CartPage.java
│   │       │
│   │       └── utils/                         # Utility or helper classes
│   │           ├── DBConnection.java
│   │           ├── ConfigReader.java
│   │           ├── ReportManager.java
│   │           └── ScreenShots.java
│   │
│   └── test/
│       └── java/
│           └── tests/                         # All TestNG test classes
│               ├── DashboardTest.java
│               ├── LoginTest.java
│               └── DBTest.java
│
├── reports/
│   ├── test-output/                           # Default TestNG reports
│   └── extent-reports/                        # Custom Extent reports (optional)
│
└── drivers/
    └── chromedriver.exe
```

### Features

✅ Follows Page Object Model (POM)
✅ Supports multiple browsers (configurable from `config.properties`)
✅ Integrated with MySQL for saving test results
✅ Uses TestNG for test execution and reporting
✅ Implements WebDriverWait for synchronization
✅ Modular, reusable, and extendable structure

---

### 🧰 Tools & Dependencies

* Java 11
* Selenium 4.x
* TestNG 7.x
* MySQL JDBC Driver
* Maven

---

### 🚀 How to Run Tests

## Configure browser & credentials

Edit `config/config.properties`:

```properties
browser=chrome or edge
url=https://www.saucedemo.com/
username=standard_user
password=secret_sauce
```

## Run tests via Maven

```bash
mvn test
```
Reports are generated in:

---
test-output/index.html
---

Test results are saved automatically for each tests.

### Example Test Scenarios

* sortAndValidateCartItems → Verifies sorting, adds two cheapest items, and validates totals.
* aboutPageAndFacebookCheck → Navigates to “About” page and verifies the Facebook link.

