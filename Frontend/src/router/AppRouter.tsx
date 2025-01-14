import React from 'react'
import { createBrowserRouter } from "react-router-dom";
import { Login } from '../views/login/Login'
import { Callback } from '../views/login/Callback'
import { DashboardLayout } from '../layout/DashboardLayout'
import { Dashboard } from '../views/dashboard/Dashboard'
import { AuthLayout } from '../layout/AuthLayout'
import { NotFound } from '../views/NotFound/NotFound'
import { BlankPage } from '../views/BlankPage'
import topArtistsLoader from '../utils/topArtistsLoader'

export const appRouter = createBrowserRouter([
  {
    path: "/",
    element: <BlankPage />,
    errorElement: <NotFound />,
  },
  {
    path: "/login",
    element: <AuthLayout />,
    errorElement: <NotFound />,
    children: [
      {
        index: true,
        element: <Login />,
        errorElement: <NotFound />
      },
      {
        index: true,
        path: "/login/callback",
        element: <Callback />,
        errorElement: <NotFound />
      }
    ]
  },
  {
    path: "/dashboard",
    element: <DashboardLayout />,
    errorElement: <NotFound />,
    children: [
      {
        index: true,
        element: <Dashboard />,
        errorElement: <NotFound />
      },
    ]
  }
])