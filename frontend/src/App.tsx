
import './App.css'
import axios from "axios";

function App() {

    function getBook(){
        axios.get("/api/book")
            .then(response => console.log(response.data))
    }

    function getSecretBook(){
        axios.get("/api/book/secret")
            .then(response => console.log(response.data))
    }

    function login(){
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin

        window.open(host+'/oauth2/authorization/github', '_self')
    }

    function getMe(){
        axios.get("/api/auth/me/2")
            .then(r => console.log(r.data))
    }

  return (
    <>
      <h1>Hallo Test!</h1>
        <button onClick={getBook}>GetBook</button>
        <button onClick={getSecretBook}>GetSecretBook</button>

        <button onClick={login}>Login</button>
        <button onClick={getMe}>Me</button>
    </>
  )
}

export default App
