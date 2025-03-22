import { useState } from "react";
import { useAuth } from "../../hooks/AuthContext";
import { useNavigate } from "react-router";
import { Link } from "react-router";
import classes from "./registerPage.module.css";

export default function LoginPage() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [address, setAddress] = useState("");
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const { register } = useAuth();

    const handleRegistration = async (e) => {
        e.preventDefault();
        setError(null);

        const registerData = {
            username,
            password,
            email,
            phoneNumber,
            address
        };
        try {
            await register(registerData);
            alert("Your registration was successful! Please check your email to verify your account.");
            setUsername("");
            setPassword("");
            setEmail("");
            setPhoneNumber("");
            setAddress("");
            navigate("/login");
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <div className={classes.registerContainer}>
            <form className={classes.registerForm} onSubmit={handleRegistration}>
                <h3>Create an Account</h3>

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
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
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

                <input
                    className={classes.inputField}
                    type="text"
                    placeholder="Phone Number"
                    value={phoneNumber}
                    onChange={(e) => setPhoneNumber(e.target.value)}
                    required
                />

                <input
                    className={classes.inputField}
                    type="text"
                    placeholder="Address"
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    required
                />

                <button className={classes.registerButton} type="submit">
                    Register
                </button>

                <div className={classes.loginContainer}>
                    Already have an account?
                    <Link to="/login" className={classes.loginLink}> Login here</Link>
                </div>
            </form>
        </div>
    );
}