import React from 'react'
import { Card, CardActionArea, CardMedia } from '@mui/material'

export const ArtistCard = ({ data }: any) => {
  const { genres, name, images } = data;
  const url: string = images[0]?.url != null ? images[0].url : 'https://play-lh.googleusercontent.com/eN0IexSzxpUDMfFtm-OyM-nNs44Y74Q3k51bxAMhTvrTnuA4OGnTi_fodN4cl-XxDQc';

  return (
    <Card sx={{ height: '20vh', width: '15vh', backgroundColor: '#415a77', marginLeft: '1vh' }}>
      <CardActionArea onClick={() => console.log("To artist info")}>
        <CardMedia
          component="img"
          sx={{ width: '15vh' }}
          image={url}
        />
        
        <div className='artistInfo'>
          <p>{name}</p>
          <p className='genres'>{genres.join(", ")}</p>
        </div>
      </CardActionArea>
    </Card> 
  )
}
