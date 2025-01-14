import React from 'react'
import './layout.css'
import { Outlet } from 'react-router-dom'

export const DashboardLayout: React.FC = () => {
  return (
    <Outlet />
  )
}
