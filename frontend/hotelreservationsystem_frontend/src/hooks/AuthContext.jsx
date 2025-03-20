import { createContext, useState, useEffect, useContext } from "react";
import PropTypes from "prop-types";
import * as userService from "../services/UserService";

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);

    // Fetch user on first load
    useEffect(() => {
        const fetchUser = async () => {
            try {
                const userData = await userService.getCurrentUser();
                setUser(userData);
            } catch (error) {
                setUser(null);
                console.error("No user logged in:", error);
            }
        };
        fetchUser();
    }, []);

    const login = async (username, password) => {
        try {
            const user = await userService.login(username, password);
            if (user) {
                setUser(user);
                alert("Login successful: " + user.username);
                return user;
            }
            else {
                alert("Invalid credentials");
            }
        } catch {
            alert("Something went wrong");
            setUser(null);
        }
    };

    const logout = async () => {
        await userService.logout();
        setUser(null);
    };

    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

AuthProvider.propTypes = {
    children: PropTypes.node.isRequired,
};

export default AuthContext;