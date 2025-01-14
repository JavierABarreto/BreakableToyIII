import { createContext, useState } from "react"

const UserContext = createContext({});

export const UserProvider = ({ children }: any) => {
  const [userId, setUserId] = useState()

  return (
    <UserContext.Provider value={{ userId, setUserId}}>
        {children}
    </UserContext.Provider>
  )
}

export default UserContext;
