import { useState } from "react";
import {
  Paper,
  TextField,
  Button,
  Typography,
  Box,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function Login() {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const handleLogin = async () => {

    try {

      const response = await api.post("/users/login", {
        email,
        password,
      });

      localStorage.setItem("token", response.data.token);

      alert("Login Successful!");

      navigate("/");

    }catch (error) {
  console.log("FULL ERROR:", error);
  console.log("RESPONSE:", error.response);
  console.log("DATA:", error.response?.data);

  alert("Check console");
}

  };

  return (
    <Box
      display="flex"
      justifyContent="center"
      mt={10}
    >
      <Paper sx={{ p: 4, width: 400 }}>

        <Typography variant="h4" mb={3}>
          Login
        </Typography>

        <TextField
          fullWidth
          label="Email"
          margin="normal"
          value={email}
          onChange={(e)=>setEmail(e.target.value)}
        />

        <TextField
          fullWidth
          type="password"
          label="Password"
          margin="normal"
          value={password}
          onChange={(e)=>setPassword(e.target.value)}
        />

        <Button
          fullWidth
          variant="contained"
          sx={{ mt:2 }}
          onClick={handleLogin}
        >
          Login
        </Button>

      </Paper>
    </Box>
  );
}

export default Login;