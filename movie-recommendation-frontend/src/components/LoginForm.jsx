import React, { useState } from "react";
import { TextField, Button, Typography, Stack, Link } from "@mui/material";
import axios from "axios";

const LoginForm = ({ onSwitch }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post("http://localhost:8080/api/auth/login", { email, password });
      localStorage.setItem("token", res.data.token);
      alert("Login successful!");
      window.location.href = "/landing";
    } catch (err) {
      alert("Invalid credentials!");
    }
  };

  return (
    <form onSubmit={handleLogin}>
      <Stack spacing={3}>
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
        <Button variant="contained" color="primary" fullWidth type="submit">
          Login
        </Button>
        <Typography align="center" variant="body2">
          Don’t have an account?{" "}
          <Link onClick={onSwitch} sx={{ cursor: "pointer", color: "#90caf9" }}>
            Sign up
          </Link>
        </Typography>
      </Stack>
    </form>
  );
};

export default LoginForm;
