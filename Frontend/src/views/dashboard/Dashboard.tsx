import React, { useEffect } from 'react'
import './dashboard.css'
import { TopArtistsSection } from '../../components/topArtistsSection/topArtistsSection';

export const Dashboard: React.FC = () => {
  return (
    <div className='dashboard'>
      <TopArtistsSection />
    </div>
  )
}
