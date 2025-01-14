import React from 'react'
import './layout.css'
import { Outlet } from 'react-router-dom'

export const AuthLayout: React.FC = () => {
  return (
    <Outlet />
  )
}
