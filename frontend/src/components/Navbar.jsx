import { AppBar, Toolbar, Typography, Button } from "@mui/material";
import ShieldIcon from "@mui/icons-material/Shield";
import { useNavigate } from "react-router-dom";

function Navbar() {

  const navigate = useNavigate();

  return (
    <AppBar position="static" sx={{ bgcolor: "#1565c0" }}>
      <Toolbar>

        <ShieldIcon sx={{ mr: 1 }} />

        <Typography
          variant="h6"
          sx={{ flexGrow: 1, fontWeight: "bold" }}
        >
          DocuShield AI
        </Typography>

        <Button
          color="inherit"
          onClick={() => navigate("/")}
        >
          Dashboard
        </Button>

        <Button
          color="inherit"
          onClick={() => navigate("/upload")}
        >
          Upload
        </Button>

      </Toolbar>
    </AppBar>
  );
}

export default Navbar;