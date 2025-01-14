import React, { useEffect } from 'react'
import { useNavigate, useSearchParams } from 'react-router-dom';
import useUser from '../../hooks/useUser';

export const Callback: React.FC = () => {
  const navigate = useNavigate();

  const [searchParams] = useSearchParams();
  const { setUserId }: any = useUser();

  useEffect(() => {
    const code: string | null = searchParams.get("userId");

    setTimeout(() => {
      if(code != null) {
        setUserId(code)
        navigate("/dashboard")
      } else {
        navigate("/login")
      }
    }, 2000)
  })

  return (
    <>
      <p>Loading...</p>
    </>
  )
}
