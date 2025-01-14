import axios from 'axios';

const SPOTIFY_API_URL: string = 'http://localhost:8080'

export const TopArtistsRequest = async (userId: string) => {
  try {
    const response = await axios.get(SPOTIFY_API_URL + "/me/top/artists?userId=" + userId);
    if (response.status != 200) {

    }

    return response.data;
  } catch (error) {
    console.log(error);
  }
};