import React from 'react'
import './dashboard.css'
import { Outlet } from 'react-router-dom'
import { Navbar } from '../../components/navbar/navbar'

export const Dashboard: React.FC = () => {
  return (
    <div className='pageBackground'>
      <Navbar />
      <Outlet />
    </div>
  )
}
