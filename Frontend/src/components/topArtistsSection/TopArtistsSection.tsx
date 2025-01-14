import React, { useEffect, useState } from 'react'
import './TopArtistsSection.css'
import { ArtistCard } from '../ArtistCard/ArtistCard'
import useUser from '../../hooks/useUser';
import { topArtistInterface } from '../../interfaces/topArtistInterface';
import { TopArtistsRequest } from '../../api/spotifyApi';

export const TopArtistsSection = () => {
  const { userId }: any = useUser();
  const [topArtists, setTopArtists] = useState([]);

  const getTopArtists = async () => {
    setTopArtists(await TopArtistsRequest(userId))
  }

  useEffect(() => {
    getTopArtists();
  }, [])

  return (
    <div id="topArtists">
      <h2 className="topArtistsTitle">My Top Artists</h2>
      <div className="topArtistsContainer">
        {
          topArtists?.map((artist: topArtistInterface, index: number) => {
            return <ArtistCard key={"topArtist-"+index} data={artist} />
          })
        }
      </div>
    </div>
  )
}
