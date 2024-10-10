
type LoginPageProps = {
    login: () => void
    getMe: () => void
}
export default function LoginPage(props:Readonly<LoginPageProps>){


    return(
        <>
            <button onClick={props.login}>Login</button>
            <button onClick={props.getMe}>Me</button>
        </>
    )
}