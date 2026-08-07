import { useState } from "react";
import {
  Paper,
  Button,
  Typography,
} from "@mui/material";
import UploadFileIcon from "@mui/icons-material/UploadFile";
import api from "../services/api";

function UploadBox() {

  const [selectedFile, setSelectedFile] = useState(null);
  const [uploadStatus, setUploadStatus] = useState("");
  const [document, setDocument] = useState(null);

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
  };

  const uploadDocument = async () => {

    if (!selectedFile) {
      alert("Please select a PDF.");
      return;
    }

    const token = localStorage.getItem("token");

    if (!token) {
      alert("Please login first!");
      return;
    }

    const formData = new FormData();
    formData.append("file", selectedFile);

    try {

      const response = await api.post(
        "/documents/upload",
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      console.log(response.data);

      setDocument(response.data);
      setUploadStatus("Document uploaded successfully!");

    } catch (error) {

      console.error(error);

      if (error.response) {
        console.log(error.response.data);
      }

      setUploadStatus("Upload failed!");
    }

  };

  return (
    <Paper
      elevation={4}
      sx={{
        p: 5,
        textAlign: "center",
        borderRadius: 3,
      }}
    >

      <UploadFileIcon
        sx={{
          fontSize: 70,
          color: "#1565c0",
        }}
      />

      <Typography variant="h5" mt={2}>
        Upload PDF Document
      </Typography>

      <Typography color="text.secondary" mb={3}>
        AI will analyze your document
      </Typography>

      <input
        type="file"
        accept=".pdf"
        onChange={handleFileChange}
      />

      <br />
      <br />

      <Button
        variant="contained"
        onClick={uploadDocument}
      >
        Upload
      </Button>

      {uploadStatus && (
        <Typography
          mt={3}
          color={
            uploadStatus.includes("failed")
              ? "error"
              : "success.main"
          }
        >
          {uploadStatus}
        </Typography>
      )}

      {document && (
        <>
          <Typography mt={2}>
            <b>Document ID:</b> {document.id}
          </Typography>

          <Typography>
            <b>File:</b> {document.fileName}
          </Typography>

          <Typography color="primary">
            <b>Status:</b> {document.status}
          </Typography>
        </>
      )}

    </Paper>
  );
}

export default UploadBox;