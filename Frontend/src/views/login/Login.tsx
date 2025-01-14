import React, { useState } from 'react'
import './login.css'
import { Box, ThemeProvider } from '@mui/material';
import Container from '@mui/material/Container';
import LoadingButton from '@mui/lab/LoadingButton'
import { loginTheme } from '../../utils/customColors';
import { login } from '../../api/auth';

export const Login: React.FC = () => {
  const [isDisabled, setIsDisabled] = useState(false);
  const [loading, setLoading] = useState(false);

  const btnLoading = (value: boolean) => {
    setIsDisabled(value);
    setLoading(value);
  }

  const handleSubmit = () => {
    btnLoading(true)

    setTimeout(() => {
      btnLoading(false)
      login();
    }, 2000);
  }

  return (
    <div className='loginBackground'>
      <Container maxWidth="sm">
        <Box sx={{
            height: '100vh', alignContent: 'center'
          }}>
          <Box sx={{
            color: '#e0e1dd',
            padding: '10',
            textAlign: 'center',
            fontFamily: 'Arial'
          }}>
            <ThemeProvider theme={loginTheme}>
              <h1 className='appTitle'>BreakableSpotifyApp</h1>

              <LoadingButton loadingIndicator="Loading..." variant="contained" color='customButtonColor'
                onClick={() => handleSubmit()} loading={loading} disabled={isDisabled}
              >
                SIGN IN
              </LoadingButton>
            </ThemeProvider>
          </Box>
        </Box>
      </Container>
    </div>
  )
}
