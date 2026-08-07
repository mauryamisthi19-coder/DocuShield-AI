import Navbar from "../components/Navbar";
import { Box, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

function Dashboard() {

  const navigate = useNavigate();

  return (
    <>
      <Navbar />

      <Box sx={{ p: 5, textAlign: "center" }}>

        <Typography variant="h3" fontWeight="bold">
          Welcome to DocuShield AI
        </Typography>

        <Typography mt={2} color="text.secondary">
          AI Powered Document Analysis Platform
        </Typography>

        <Button
          variant="contained"
          sx={{ mt: 4 }}
          onClick={() => navigate("/upload")}
        >
          Upload Document
        </Button>

      </Box>
    </>
  );
}

export default Dashboard;