import React from 'react'
import './navbar.css'
import IconButton from '@mui/material/IconButton';
import { Box, InputAdornment, TextField } from '@mui/material'
import { Logout, SearchOutlined } from '@mui/icons-material'

export const Navbar = () => {
  return (
    <div className='nav'>
      <TextField id="searchField" hiddenLabel variant="filled" size="small" 
            sx={{ backgroundColor: '#e0e1dd', width: '40vh' }} placeholder="Vicente Fernandez, Believer, etc..."
            slotProps={{
              input: {
                startAdornment: (
                  <InputAdornment position="start">
                    <SearchOutlined sx={{ mr: 1, my: 0.5 }} />
                  </InputAdornment>
                ),
              },
            }}
      />
      
      <IconButton className='logoutIcon' sx={{ position: 'absolute', right: '0', color: "#e0e1dd", marginRight: '10px' }}>
        <Logout fontSize='large' />
      </IconButton>
    </div>
  )
}
