import React from "react";
import { FaCheckCircle, FaTachometerAlt } from "react-icons/fa";
import "./Logo.css";

const Logo = () => {
  return (
    <div className="logo">
      <FaTachometerAlt className="logo-icon tachometer" />
      <span className="logo-text">NoStress</span>
      <FaCheckCircle className="logo-icon check" />
    </div>
  );
};

export default Logo;
