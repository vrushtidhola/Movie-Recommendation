import React, { useEffect, useMemo, useRef, useState } from "react";
import {
  Grid,
  Card,
  CardMedia,
  Typography,
  TextField,
  Button,
  Box,
  MenuItem,
  Snackbar,
  Alert,
  Chip,
} from "@mui/material";
import api from "../api/axios";

export default function LandingPage() {
  const userId = Number(localStorage.getItem("userId"));

  const [movies, setMovies] = useState([]);
  const [friends, setFriends] = useState([]);
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [selectedFriend, setSelectedFriend] = useState("");
  const [rating, setRating] = useState("");
  const [comment, setComment] = useState("");
  const [friendRecommendations, setFriendRecommendations] = useState([]);
  const [tmdbMap, setTmdbMap] = useState({});
  const [snack, setSnack] = useState({ open: false, msg: "", severity: "info" });

  const formRef = useRef(null);

  /* ---------------- EFFECTS ---------------- */

  useEffect(() => {
    loadTrendingMovies();
    loadFriends();
    loadFriendRecommendations();
  }, []);

  useEffect(() => {
    if (!selectedMovie) return;
    setTimeout(() => {
      formRef.current?.scrollIntoView({ behavior: "smooth" });
    }, 100);
  }, [selectedMovie]);

  useEffect(() => {
    const loadTmdbDetails = async () => {
      const ids = [...new Set(friendRecommendations.map(r => r.movieTmdbId))];
      const map = {};

      for (const id of ids) {
        const res = await api.get(`/movies/${id}`);
        map[id] = res.data;
      }
      setTmdbMap(map);
    };

    if (friendRecommendations.length) loadTmdbDetails();
  }, [friendRecommendations]);

  const showSnack = (msg, severity = "info") =>
    setSnack({ open: true, msg, severity });

  /* ---------------- API CALLS ---------------- */

  const loadTrendingMovies = async () => {
    try {
      const res = await api.get("/movies/trending");
      setMovies(res.data?.results || []);
    } catch {
      showSnack("Failed to load trending movies", "error");
    }
  };

  const loadFriends = async () => {
    try {
      const res = await api.get(`/friends/${userId}`);
      setFriends(res.data || []);
    } catch {
      showSnack("Failed to load friends", "error");
    }
  };

  const loadFriendRecommendations = async () => {
    try {
      const res = await api.get(`/recommend/friends/${userId}`);
      setFriendRecommendations(res.data || []);
    } catch {
      showSnack("Failed to load friend recommendations", "error");
    }
  };

  const submitRecommendation = async () => {
    if (!selectedMovie || !selectedFriend)
      return showSnack("Select movie & friend", "warning");

    try {
      await api.post("/recommend/recommendMovie", {
        userId,
        friendId: Number(selectedFriend),
        movieTmdbId: selectedMovie.id,
        movieTitle: selectedMovie.title,
        rating,
        comment,
      });

      showSnack("Recommendation sent 🎉", "success");
      setSelectedMovie(null);
      setSelectedFriend("");
      setRating("");
      setComment("");
      loadFriendRecommendations();
    } catch {
      showSnack("Failed to send recommendation", "error");
    }
  };

  /* ---------------- UI ---------------- */

  return (
    <Box sx={{ minHeight: "100vh", background: "#0f0f0f", color: "#fff", p: 4 }}>
      <Typography variant="h4" sx={{ mb: 3 }}>
        🎬 MovieVerse Dashboard
      </Typography>
      {/* FRIEND RECOMMENDATIONS */}
<Typography variant="h5" sx={{ mt: 6, mb: 2 }}>
  👥 Recommended by Friends
</Typography>

{friendRecommendations.length === 0 ? (
  <Typography sx={{ color: "#aaa" }}>
    No friend recommendations yet
  </Typography>
) : (
  <Grid container spacing={3}>
    {friendRecommendations.map((rec, index) => {
      const movie = tmdbMap[rec.movieTmdbId];
      if (!movie) return null;

      return (
        <Grid item xs={12} sm={6} md={3} key={index}>
          <Card
            sx={{
              height: 520,
              background: "#1e1e1e",
              color: "#fff",
              borderRadius: 2,
              overflow: "hidden",
              display: "flex",
              flexDirection: "column",
            }}
          >
            <CardMedia
              component="img"
              image={`https://image.tmdb.org/t/p/w500${movie.poster_path}`}
              alt={movie.title}
              sx={{
                height: 360,
                objectFit: "cover",
              }}
            />

            <Box sx={{ p: 2 }}>
              <Typography
                sx={{
                  fontWeight: 600,
                  height: 42,
                  overflow: "hidden",
                  display: "-webkit-box",
                  WebkitLineClamp: 2,
                  WebkitBoxOrient: "vertical",
                }}
              >
                {movie.title}
              </Typography>

              <Typography sx={{ fontSize: 14, color: "#aaa" }}>
                ⭐ {rec.rating} / 10
              </Typography>

              <Chip
  label={`Recommended by ${rec.recommendedBy}`}
  size="small"
  sx={{
    mt: 1,
    maxWidth: "100%",
    whiteSpace: "nowrap",
    overflow: "hidden",
    textOverflow: "ellipsis",
  }}
/>


              {rec.comment && (
                <Typography
                  sx={{
                    mt: 1,
                    fontSize: 13,
                    color: "#ccc",
                    overflow: "hidden",
                    display: "-webkit-box",
                    WebkitLineClamp: 2,
                    WebkitBoxOrient: "vertical",
                  }}
                >
                  “{rec.comment}”
                </Typography>
              )}
            </Box>
          </Card>
        </Grid>
      );
    })}
  </Grid>
)}

      {/* TRENDING MOVIES */}
      <Typography variant="h5" sx={{ mb: 2 }}>
        📈 Trending Movies
      </Typography>

      <Grid container spacing={3} alignItems="stretch">
        {movies.map((movie) => (
          <Grid item xs={12} sm={6} md={3} key={movie.id} sx={{
    maxWidth: "100%",
  }}>
            <Card
              sx={{
    width: "100%",
    maxWidth: "100%",
    height: 520,
    background: "#1e1e1e",
    color: "#fff",
    borderRadius: 2,
    overflow: "hidden",
    display: "flex",
    flexDirection: "column",
  }}
            >
              {/* HEADER */}
              <Box sx={{ p: 2 }}>
              <Typography
  sx={{
    fontWeight: 600,
    fontSize: 16,
    lineHeight: "20px",
    height: 40,
    overflow: "hidden",
    textOverflow: "ellipsis",
    display: "-webkit-box",
    WebkitLineClamp: 2,
    WebkitBoxOrient: "vertical",
    whiteSpace: "normal",
    maxWidth: "100%",
  }}
>
  {movie.title}
</Typography>


                <Typography sx={{ color: "#aaa", fontSize: 14 }}>
                  ⭐ {movie.vote_average}
                </Typography>

                <Button
                  fullWidth
                  variant="contained"
                  sx={{ mt: 1 }}
                  onClick={() => setSelectedMovie(movie)}
                >
                  Recommend
                </Button>
              </Box>

              {/* POSTER */}
              <CardMedia
                component="img"
                image={`https://image.tmdb.org/t/p/w500${movie.poster_path}`}
                alt={movie.title}
                sx={{
                  height: 360,
                  objectFit: "cover",
                }}
              />
            </Card>
          </Grid>
        ))}
      </Grid>
      
      {/* RECOMMEND FORM */}
      {selectedMovie && (
        <Box ref={formRef} sx={{ mt: 5, p: 3, background: "#1e1e1e" }}>
          <Typography variant="h5">
            ⭐ Recommend: {selectedMovie.title}
          </Typography>

          <TextField
            select
            fullWidth
            label="Select Friend"
            sx={{ mt: 2, background: "#fff" }}
            value={selectedFriend}
            onChange={(e) => setSelectedFriend(e.target.value)}
          >
            {friends.map((f) => (
              <MenuItem key={f.id} value={f.id}>
                {f.username}
              </MenuItem>
            ))}
          </TextField>

          <TextField
            fullWidth
            type="number"
            label="Rating (1-10)"
            sx={{ mt: 2, background: "#fff" }}
            value={rating}
            onChange={(e) => setRating(e.target.value)}
          />

          <TextField
            fullWidth
            multiline
            rows={3}
            label="Comment"
            sx={{ mt: 2, background: "#fff" }}
            value={comment}
            onChange={(e) => setComment(e.target.value)}
          />

          <Button sx={{ mt: 2 }} variant="contained" onClick={submitRecommendation}>
            Submit
          </Button>
        </Box>
      )}

      <Snackbar
        open={snack.open}
        autoHideDuration={2500}
        onClose={() => setSnack({ ...snack, open: false })}
      >
        <Alert severity={snack.severity} variant="filled">
          {snack.msg}
        </Alert>
      </Snackbar>
    </Box>
  );
}
