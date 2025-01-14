import React from 'react'
import './layout.css'
import { Outlet } from 'react-router-dom'
import { Navbar } from '../components/navbar/navbar'

export const DashboardLayout: React.FC = () => {
  return (
    <div className='pageBackground'>
      <Navbar />
      <Outlet />
    </div>
  )
}
