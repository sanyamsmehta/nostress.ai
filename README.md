# nostress.ai

**NoStress** is a microservice-based platform for automating performance testing of REST APIs using JMeter and a clean frontend UI.  
It enables users to run tests by simply entering a repo URL and a test prompt, and visualizes results in an HTML report.
---

## Technologies Used  ⚙️

- **Java 17**, Spring Boot 3
- **Apache JMeter 5.6.3**
- **React 18**, Vite (optional), CSS Modules
- **Maven**, Node.js
---

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/sanyamsmehta/nostress.ai.git
cd nostress-platform
```
---

### 2. Backend Setup (nostress-backend-service)   🖥️

Prerequisites
- Java 17+
- Apache JMeter installed locally
- Maven

Run the Backend
```bash
cd nostress-backend-service
./mvnw spring-boot:run
```

⚠️ Make sure to update the file paths in TestController.java to point to your local JMeter .bat file and .jmx script:
```bash
String jmeterPath = "C:/path/to/jmeter.bat";
String jmxFilePath = "test.jmx";
String reportPath = "./html-report";
```
---

### 3. Frontend Setup (nostress-frontend-ui)  🌐

Prerequisites
- Node.js 18+
- npm or yarn

Run the Frontend
---

```bash 
cd nostress-frontend-ui
npm install
npm start

```
Open your browser at http://localhost:3000 to start testing.
---

##  How It Works  📊

- User inputs a repo URL and test prompt in the UI.
- Backend generates or reads a .jmx file for JMeter.
- JMeter runs in non-GUI mode and produces:
- .jtl results
- html-report/ folder with analysis
- The backend returns the HTML report as a response to the frontend.
- User sees test progress and results on screen.


## Sample Test Output 🧪 

- results.jtl: Raw JMeter output
- html-report/index.html: Human-friendly report with graphs and metrics
- console.log: logs

---
## 👨‍💻 Developed By: Sanyam Mehta
---
