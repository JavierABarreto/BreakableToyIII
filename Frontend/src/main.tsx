import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { appRouter } from './router/AppRouter.tsx'
import { UserProvider } from './context/UserProvider.tsx'
import { RouterProvider } from 'react-router-dom'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <UserProvider>
      <RouterProvider router={appRouter} />
    </UserProvider>
  </StrictMode>,
)
