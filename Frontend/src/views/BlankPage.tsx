import React, { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'

export const BlankPage: React.FC = () => {
  const navigate = useNavigate();

  useEffect(() => {
    setTimeout(() => {
        navigate('/login')
    }, 1000);
  });

  return (
    <></>
  )
}
