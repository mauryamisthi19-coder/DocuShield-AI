import Navbar from "../components/Navbar";
import UploadBox from "../components/UploadBox";
import { Box, Typography } from "@mui/material";

function UploadPage() {
  return (
    <>
      <Navbar />

      <Box sx={{ p: 4 }}>

        <Typography
          variant="h4"
          fontWeight="bold"
          gutterBottom
        >
          Upload Document
        </Typography>

        <Typography
          color="text.secondary"
          mb={4}
        >
          Upload your PDF and let AI analyze it.
        </Typography>

        <UploadBox />

      </Box>
    </>
  );
}

export default UploadPage;