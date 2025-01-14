import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { Login } from '../views/login/Login'
import { Callback } from '../views/login/Callback'
import { DashboardLayout } from '../layout/DashboardLayout'
import { Dashboard } from '../views/dashboard/Dashboard'
import { AuthLayout } from '../layout/AuthLayout'
import { NotFound } from '../views/NotFound/NotFound'
import { BlankPage } from '../views/BlankPage'

export const AppRouter: React.FC = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route index path='/' element={ <BlankPage /> } errorElement={<NotFound />} />

        <Route path='/login' element={ <AuthLayout /> } errorElement={<NotFound />}>
          <Route index element={ <Login /> } />
          <Route path='/login/callback' element={ <Callback /> } />
        </Route>

        <Route path='/dashboard' element={ <DashboardLayout /> } errorElement={<NotFound />}>
          <Route index element={ <Dashboard /> } />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}
