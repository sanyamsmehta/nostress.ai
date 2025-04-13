// import React from "react";
// import ApiInputForm from "./components/ApiInputForm";
// import './App.css';


// function App() {
//   // const handleSubmit = async (apiUrl, prompt) => {
//   //   try {
//   //     const response = await fetch("http://localhost:8080/api/test", {
//   //       method: "POST",
//   //       headers: { "Content-Type": "application/json" },
//   //       body: JSON.stringify(prompt),
//   //     });
//   //     const data = await response.text();
//   //     alert(data);
//   //   } catch (error) {
//   //     console.error("Error:", error);
//   //     alert("Failed to execute test.");
//   //   }
//   // };
//   const handleSubmit = async (apiUrl, prompt) => {
//     try {
//         const response = await fetch(`http://localhost:8080/api/test?apiUrl=${encodeURIComponent(apiUrl)}&prompt=${encodeURIComponent(prompt)}`, {
//             method: "POST",
//             headers: { "Content-Type": "application/json" }
//         });
//         const data = await response.text();
//         alert(data);
//     } catch (error) {
//         console.error("Error:", error);
//         alert("Failed to execute test.");
//     }
// };


//   return (
//     <div>
//       <h1>Performance Testing Tool</h1>
//       <ApiInputForm onSubmit={handleSubmit} />
//     </div>
//   );
// }

// export default App;

















/************************************************************************ */
// STABLE STABLE VERSION
// import React from "react";
// import ApiInputForm from "./components/ApiInputForm";
// import "./App.css";

// function App() {
//   const handleSubmit = async (repoUrl, prompt) => {
//     try {
//       const response = await fetch(
//         `http://localhost:8080/api/test?apiUrl=${encodeURIComponent(repoUrl)}&prompt=${encodeURIComponent(prompt)}`,
//         {
//           method: "POST",
//           headers: {
//             "Content-Type": "application/json",
//           },
//         }
//       );
//       const data = await response.text();
//       alert(data);
//     } catch (error) {
//       console.error("Error:", error);
//       alert("Failed to execute test.");
//     }
//   };
  

//   return (
//     <div className="App">
//       <header className="App-header">
//         <h1 className="App-title">NoStress </h1>
//         <h3>An Insurance for your Software</h3>
//       </header>
//       <main>
//         <ApiInputForm onSubmit={handleSubmit} />
//       </main>
//     </div>
//   );
// }

// export default App;

/************************************************************************ */





import React, { useState } from "react";
import ApiInputForm from "./components/ApiInputForm";
import "./App.css";

function App() {
  const [loading, setLoading] = useState(false); // For dynamic "executing" message
  const [result, setResult] = useState(null); // To store JMeter summary response

  // const handleSubmit = async (repoUrl, prompt) => {
  //   setLoading(true); // Show "Your test case is being executed"
  //   setResult(null); // Clear previous results
  //   try {
  //     const response = await fetch(
  //       `http://localhost:8080/api/test?apiUrl=${encodeURIComponent(repoUrl)}&prompt=${encodeURIComponent(prompt)}`,
  //       {
  //         method: "POST",
  //         headers: { "Content-Type": "application/json" },
  //       }
  //     );

  //     const data = await response.text(); // Assuming your backend returns a string with summary results
  //     setResult(data);
  //   } catch (error) {
  //     console.error("Error:", error);
  //     setResult("An error occurred while executing the test case.");
  //   } finally {
  //     setLoading(false); // Hide the "executing" message
  //   }
  // };

  const handleSubmit = async (repoUrl, prompt) => {
    setLoading(true);
    setResult(null);
  
    try {
      const response = await fetch(
        `http://localhost:8080/api/test?apiUrl=${encodeURIComponent(repoUrl)}&prompt=${encodeURIComponent(prompt)}`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
        }
      );
  
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
  
      const htmlContent = await response.text();
      setResult(htmlContent); // Set HTML content in result state
    } catch (error) {
      console.error("Error:", error);
      setResult("An error occurred while executing the test case.");
    } finally {
      setLoading(false);
    }
  };
  

  return (
    <div className="App">
      <header className="App-header">
        <h1 className="App-title">NoStress - Insurance for your Software</h1>
      </header>
      <main className="App-main">
        <ApiInputForm onSubmit={handleSubmit} />
        {loading && <p className="App-status">Your test case is being executed...</p>}
        {result && (
          <div
            className="App-result"
            dangerouslySetInnerHTML={{ __html: result }}
          />
        )}       
      </main>
    </div>
  );
}

export default App;