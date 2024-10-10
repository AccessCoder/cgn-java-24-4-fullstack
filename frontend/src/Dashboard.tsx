import axios from "axios";

type DashboardProps = {
    logout: () => void
}
export default function Dashboard(props:Readonly<DashboardProps>){

    function getBook(){
        axios.get("/api/book")
            .then(response => console.log(response.data))
    }

    function getSecretBook(){
        axios.get("/api/book/secret")
            .then(response => console.log(response.data))
    }


    return(
        <>
            <button onClick={getBook}>GetBook</button>
            <button onClick={getSecretBook}>GetSecretBook</button>
            <button onClick={props.logout}>Logout</button>
        </>
    )
}