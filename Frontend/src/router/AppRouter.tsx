import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { AuthLayout } from '../layout/AuthLayout'
import { Login } from '../views/login/Login'
import { Callback } from '../views/login/Callback'
import { DashboardLayout } from '../layout/DashboardLayout'
import { Dashboard } from '../views/dashboard/Dashboard'

export const AppRouter: React.FC = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={ <AuthLayout /> }>
          <Route index element={ <Login /> } />
          <Route path='/callback' element={ <Callback /> } />
        </Route>

        <Route path='/dashboard' element={ <DashboardLayout /> }>
          <Route index element={ <Dashboard /> } />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}
