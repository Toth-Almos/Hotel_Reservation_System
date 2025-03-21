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
    const navigate = useNavigate();
    const { register } = useAuth();

    const handleRegistration = async () => {
        const registerData = {
            username,
            password,
            email,
            phoneNumber,
            address
        };
        try {
            const user = await register(registerData)
            if (user) {
                alert("Successful Registration! Please check your email to validate your profile.");
                navigate("/");
            }
        } catch {
            alert("Registration error!");
        }
    };

    return (
        <div className={classes.registerContainer}>
            <form className={classes.registerForm} onSubmit={handleRegistration}>
                <h3>Create an Account</h3>

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