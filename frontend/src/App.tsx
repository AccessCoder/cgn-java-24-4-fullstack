
import './App.css'
import axios from "axios";
import {Route, Routes} from "react-router-dom";
import LoginPage from "./LoginPage.tsx";
import Dashboard from "./Dashboard.tsx";
import ProtectedRoute from "./ProtectedRoute.tsx";
import {useEffect, useState} from "react";

type User = {
    username:string
    id:string
    avatarUrl:string
    favList:string[]
}
function App() {

    const [user, setUser] = useState<User|null>(null)

    useEffect(() => {
        getMe()
    }, [])

    function login(){
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin

        window.open(host+'/oauth2/authorization/github', '_self')
    }

    function logout(){
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin

        window.open(host+'/api/auth/logout', '_self')
    }

    function getMe(){
        axios.get("/api/auth/me/2")
            .then(r => setUser(r.data))
    }

  return (
    <>
      <h1>Hallo Test!</h1>
        <p>{user?.username}</p>
        <p>{user?.id}</p>
        <p>{user?.avatarUrl}</p>
        <p>{user?.favList}</p>
        <Routes>
            <Route path={"/"} element={<LoginPage login={login} getMe={getMe}/>}/>
            <Route element={<ProtectedRoute username={user?.username}/> }>
               //Outlet
                <Route path={"/dashboard"} element={<Dashboard logout={logout}/>}/>

            </Route>
        </Routes>



    </>
  )
}

export default App
