import React, { useState } from "react";
import { TextField, Button, Typography, Stack, Link } from "@mui/material";
import axios from "axios";

const SignupForm = ({ onSwitch }) => {
  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSignup = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8080/api/auth/signup", {
        username: userName,
        email:email,
        password:password,
      });
      alert("Signup successful! Please login.");
      onSwitch();
    } catch (err) {
      alert("Signup failed!");
    }
  };

  return (
    <form onSubmit={handleSignup}>
      <Stack spacing={3}>
        <TextField
          variant="outlined"
          label="Full Name"
          fullWidth
          InputProps={{ style: { color: "white" } }}
          InputLabelProps={{ style: { color: "#aaa" } }}
          value={userName}
          onChange={(e) => setUserName(e.target.value)}
        />
        <TextField
          variant="outlined"
          label="Email"
          type="email"
          fullWidth
          InputProps={{ style: { color: "white" } }}
          InputLabelProps={{ style: { color: "#aaa" } }}
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <TextField
          variant="outlined"
          label="Password"
          type="password"
          fullWidth
          InputProps={{ style: { color: "white" } }}
          InputLabelProps={{ style: { color: "#aaa" } }}
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <Button variant="contained" color="success" fullWidth type="submit">
          Sign Up
        </Button>
        <Typography align="center" variant="body2">
          Already have an account?{" "}
          <Link onClick={onSwitch} sx={{ cursor: "pointer", color: "#90caf9" }}>
            Login
          </Link>
        </Typography>
      </Stack>
    </form>
  );
};

export default SignupForm;
