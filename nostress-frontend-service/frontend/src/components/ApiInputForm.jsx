// import React, { useState } from "react";

// const ApiInputForm = ({ onSubmit }) => {
//   const [apiUrl, setApiUrl] = useState("");
//   const [prompt, setPrompt] = useState("");

//   const handleSubmit = (e) => {
//     e.preventDefault();
//     onSubmit(apiUrl, prompt);
//   };

//   return (
//     <form onSubmit={handleSubmit}>
//       <label>API URL:</label>
//       <input
//         type="text"
//         value={apiUrl}
//         onChange={(e) => setApiUrl(e.target.value)}
//         placeholder="Enter API URL"
//         required
//       />
//       <label>Test Case Prompt:</label>
//       <textarea
//         value={prompt}
//         onChange={(e) => setPrompt(e.target.value)}
//         placeholder="Describe your test case"
//         required
//       />
//       <button type="submit">Run Test</button>
//     </form>
//   );
// };

// export default ApiInputForm;


import React, { useState } from "react";
import { FaLink, FaEdit } from "react-icons/fa";
import "./ApiInputForm.css";

const ApiInputForm = ({ onSubmit }) => {
  const [repoUrl, setRepoUrl] = useState("");
  const [prompt, setPrompt] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(repoUrl, prompt);
  };

  return (
    <form className="form" onSubmit={handleSubmit}>
      <div className="form-group">
        <label htmlFor="repoUrl">
          <FaLink className="icon" />
          Repo URL:
        </label>
        <input
          id="repoUrl"
          type="text"
          value={repoUrl}
          onChange={(e) => setRepoUrl(e.target.value)}
          placeholder="Repo - Git/BitBucket/Cloud"
          required
        />
      </div>
      <div className="form-group">
        <label htmlFor="prompt">
          <FaEdit className="icon" />
          Test Case Prompt:
        </label>
        <textarea
          id="prompt"
          value={prompt}
          onChange={(e) => setPrompt(e.target.value)}
          placeholder="Describe your test case"
          required
        />
      </div>
      <button className="submit-button" type="submit">
        Run Test
      </button>
    </form>
  );
};

export default ApiInputForm;
