import React, {useEffect, useState} from 'react';
import { Box, AppBar, Toolbar, Typography, Grid, Card, CardMedia, CardContent, Chip, colors } from '@mui/material';
import api from '../api/axios';

const posterBase = (path) => path ? `https://image.tmdb.org/t/p/w500${path}` : '/placeholder.png';

export default function LandingPage(){
  const [trending, setTrending] = useState([]);
  const [personal, setPersonal] = useState([]);
  const [friendsRec, setFriendsRec] = useState([]);

  useEffect(()=>{
    api.get('/movies/trending').then(r => setTrending(r.data.results || []));
    // personal recommendations: backend returns list of {tmdbId, title, thumbnail}
    api.get('/recommend/personal/1').then(r => setPersonal(r.data)).catch(()=>setPersonal([]));
    api.get('/recommend/friends/1').then(r => {
      // result contains tmdbId + recommendedByCount
      const ids = r.data.map(x => x.tmdbId);
      // fetch details for these IDs (backend has movie details endpoint)
      Promise.all(ids.slice(0,8).map(id => api.get(`/movies/${id}`))).then(res=> {
        setFriendsRec(res.map(x=> x.data));
      }).catch(()=> setFriendsRec([]));
    }).catch(()=>setFriendsRec([]));
  },[]);

  return (
    <Box sx={{ bgcolor:'#040404', minHeight:'100vh', color:'white' }}>
      <AppBar position="static" sx={{ bgcolor:'#050505' }}>
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow:1 }}>MovieMate</Typography>
        </Toolbar>
      </AppBar>

      <Box sx={{ p:4 }}>
        <Typography variant="h3" gutterBottom>Welcome to Movie Recommendations</Typography>

        <Typography variant="h5" sx={{ mt:4, mb:1 }}>Trending Movies</Typography>
        <Grid container spacing={2}>
          {trending.slice(0,8).map(m => (
            <Grid item xs={6} sm={3} key={m.id}>
              <Card sx={{ bgcolor:'#ffff' }}>
                <CardMedia component="img" height="280" image={posterBase(m.poster_path)} />
                <CardContent sx={{ bgcolor:'#0d0d0d' }}>
                <Typography variant="subtitle1" sx={{ color: '#fff' }}>
                  {m.title}
                  </Typography>
                  <Typography variant="caption" color="gold">⭐ {m.vote_average}</Typography>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>

        <Typography variant="h5" sx={{ mt:5, mb:1 }}>Personalized Recommendations</Typography>
        <Grid container spacing={2}>
          {personal.map((m, i) => (
            <Grid item xs={6} sm={3} key={i}>
              <Card sx={{ bgcolor:'#0d0d0d' }}>
                <CardMedia component="img" height="280" image={m.thumbnail || posterBase(m.poster_path)} />
                <CardContent>
                <Typography variant="subtitle1" sx={{ color: '#fff' }}>
                  {m.title}
                  </Typography>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>

        <Typography variant="h5" sx={{ mt:5, mb:1 }}>Friends' Recommendations</Typography>
        <Grid container spacing={2}>
          {friendsRec.map(m => (
            <Grid item xs={6} sm={3} key={m.id}>
              <Card sx={{ position:'relative', bgcolor:'#0d0d0d' }}>
                <CardMedia component="img" height="280" image={posterBase(m.poster_path)} />
                <Chip label="Recommended by friends" color="secondary" sx={{ position:'absolute', top:8, left:8 }} />
                <CardContent>
                <Typography variant="subtitle1" sx={{ color: '#fff' }}>
                  {m.title}
                  </Typography>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Box>
    </Box>
  );
}
