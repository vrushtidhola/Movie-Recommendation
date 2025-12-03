import React from "react";
import { Card, CardContent, Typography, Box } from "@mui/material";

const AuthCard = ({ title, children }) => {
  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      minHeight="100vh"
      bgcolor="#0f0f0f"
    >
      <Card sx={{ width: 400, bgcolor: "#1e1e1e", color: "white", p: 2, borderRadius: 3 }}>
        <CardContent>
          <Typography variant="h5" align="center" gutterBottom>
            {title}
          </Typography>
          {children}
        </CardContent>
      </Card>
    </Box>
  );
};

export default AuthCard;