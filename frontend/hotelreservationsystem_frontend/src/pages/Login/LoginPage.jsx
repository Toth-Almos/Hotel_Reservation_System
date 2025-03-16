import { useState } from "react";
import { useNavigate } from "react-router";
import { login } from "../../services/UserService";

export default function LoginPage() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError(null); // Reset error message

        try {
            const user = await login(username, password); // Call login function
            if (user) {
                console.log("Login successful:", user);
                navigate("/"); // Redirect to dashboard or home
            }
        } catch (err) {
            setError("Invalid username or password.");
            console.error("Login error:", err);
        }
    };

    return (
        <div>
            <form onSubmit={handleLogin}>
                <h2>Login</h2>

                {error && <p>{error}</p>}

                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />

                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />

                <button
                    type="submit"
                >
                    Login
                </button>
            </form>
        </div>
    );
}