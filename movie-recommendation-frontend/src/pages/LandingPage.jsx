import React, { useEffect, useState } from "react"; import { Grid, Card, CardContent, CardMedia, Typography, TextField, Button, Box, List, ListItem, ListItemText, } from "@mui/material"; import api from "../api/axios";
export default function LandingPage() {
  const userId = Number(localStorage.getItem("userId"));

  const [movies, setMovies] = useState([]);
  const [friends, setFriends] = useState([]);
  const [addFriendEmail, setAddFriendEmail] = useState("");

  useEffect(() => {
    loadTrendingMovies();
    loadFriends();
  }, []);

  const loadTrendingMovies = () => {
    api.get("/movies/trending").then((res) => {
      setMovies(res.data.results || []);
    });
  };

  const loadFriends = () => {
    api.get(`/friends/${userId}`).then((res) => {
      setFriends(res.data || []);
    });
  };

  const handleAddFriend = () => {
    if (!addFriendEmail.trim()) return alert("Enter friend email");

    api.post("/friends/add", { userId, friendEmail: addFriendEmail }).then(() => {
      setAddFriendEmail("");
      loadFriends();
    });
  };

  return (
    <Box
      sx={{
        minHeight: "100vh",
        backgroundColor: "#0f0f0f",
        color: "#fff",
        p: 4,
      }}
    >
      <Typography variant="h4" sx={{ mb: 3 }}>
        🎬 MovieVerse Dashboard
      </Typography>

      <Box sx={{ display: "flex", gap: 4, mb: 5 }}>
        {/* ADD FRIEND */}
        <Box
          sx={{
            backgroundColor: "#1e1e1e",
            p: 3,
            borderRadius: 3,
            width: "300px",
          }}
        >
          <Typography variant="h6">➕ Add Friend</Typography>

          <TextField
            fullWidth
            label="Enter Friend Email"
            value={addFriendEmail}
            onChange={(e) => setAddFriendEmail(e.target.value)}
            sx={{
              mt: 2,
              input: { color: "#fff" },
              label: { color: "#bbb" },
              "& label.Mui-focused": { color: "#90caf9" },
              "& .MuiOutlinedInput-root": {
                "& fieldset": { borderColor: "#555" },
                "&:hover fieldset": { borderColor: "#888" },
                "&.Mui-focused fieldset": { borderColor: "#90caf9" },
              },
            }}
          />

          <Button
            fullWidth
            sx={{
              mt: 2,
              background: "linear-gradient(45deg, #2196f3, #21cbf3)",
              fontWeight: "bold",
            }}
            variant="contained"
            onClick={handleAddFriend}
          >
            Add Friend
          </Button>
        </Box>

        {/* FRIEND LIST */}
        <Box
          sx={{
            backgroundColor: "#1e1e1e",
            p: 3,
            borderRadius: 3,
            minWidth: "300px",
          }}
        >
          <Typography variant="h6">👥 Your Friends</Typography>

          {friends.length === 0 ? (
            <Typography sx={{ mt: 2, opacity: 0.7 }}>
              No friends added yet 😕
            </Typography>
          ) : (
            <List>
              {friends.map((f) => (
                <ListItem key={f.id}>
                  <ListItemText
                    primary={f.username}
                    secondary={f.email}
                    primaryTypographyProps={{ color: "#fff" }}
                    secondaryTypographyProps={{ color: "#aaa" }}
                  />
                </ListItem>
              ))}
            </List>
          )}
        </Box>
      </Box>

      <Typography variant="h5" sx={{ mb: 2 }}>
        📈 Trending Movies
      </Typography>

      <Grid container spacing={3}>
        {movies.map((movie) => (
          <Grid item xs={12} sm={6} md={3} key={movie.id}>
            <Card
              sx={{
                backgroundColor: "#1e1e1e",
                color: "#fff",
                borderRadius: 3,
              }}
            >
              <CardMedia
                component="img"
                height="350"
                image={`https://image.tmdb.org/t/p/w500${movie.poster_path}`}
              />
              <CardContent>
                <Typography variant="h6">{movie.title}</Typography>
                <Typography sx={{ color: "#aaa" }}>
                  ⭐ {movie.vote_average}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
}
