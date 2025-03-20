import { useState } from "react";
import { useNavigate } from "react-router";
import { useAuth } from "../../hooks/AuthContext";
import classes from "./loginPage.module.css";
import { Link } from 'react-router'

export default function LoginPage() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const { login } = useAuth();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError(null);

        try {
            const user = await login(username, password);
            if (user) {
                console.log("Login successful:", user);
                navigate("/");
            }
        } catch (err) {
            setError("Invalid username or password.");
            console.error("Login error:", err);
        }
    };

    return (
        <div className={classes.loginContainer} >
            <form className={classes.loginForm} onSubmit={handleLogin}>
                <h3>Login if you already have an account</h3>

                {error && <p className={classes.errorMessage} >{error}</p>}

                <input
                    className={classes.inputField}
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />

                <input
                    className={classes.inputField}
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />

                <button className={classes.loginButton} type="submit">
                    Login
                </button>

                <div className={classes.registerContainer}>Don&apos;t have an account yet? </div>
                <Link to='/register' className={classes.registerLink}>Register here</Link>
            </form>
        </ div>
    );
}