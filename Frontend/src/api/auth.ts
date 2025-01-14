import axios from 'axios';

const AUTH_API_URL: string = 'http://localhost:8080/auth/spotify'

export const login = async () => {
  try {
    axios.post(AUTH_API_URL)
      .then((res) => {
        if(res.status == 200) {
          window.location.href = res.data;
        }
      })
  } catch (error) {
    console.log(error);
  }
};