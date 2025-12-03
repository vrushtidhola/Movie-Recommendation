import React, {useState} from 'react';
import { TextField, Grid, Card, CardMedia, CardContent, Typography, Box } from '@mui/material';
import api from '../api/axios';

export default function SearchPage(){
  const [query, setQuery] = useState('');
  const [results, setResults] = useState([]);

  const onSearch = async (e) => {
    e.preventDefault();
    if(!query) return;
    const res = await api.get(`/movies/search?q=${encodeURIComponent(query)}&page=1`);
    setResults(res.data.results || []);
  };

  return (
    <Box sx={{ p:4 }}>
      <form onSubmit={onSearch}>
        <TextField placeholder="Search movies..." fullWidth value={query} onChange={e=>setQuery(e.target.value)} />
      </form>
      <Grid container spacing={2} sx={{ mt:2 }}>
        {results.map(m => (
          <Grid item xs={6} sm={3} key={m.id}>
            <Card>
              <CardMedia component="img" height="260" image={`https://image.tmdb.org/t/p/w500${m.poster_path}`} />
              <CardContent>
                <Typography>{m.title}</Typography>
                <Typography variant="caption">⭐ {m.vote_average}</Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
}
