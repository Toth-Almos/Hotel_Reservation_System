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
                console.error("AuthContext Current User Error:", error);
            }
        };
        fetchUser();
    }, []);

    const login = async (username, password) => {
        try {
            const user = await userService.login(username, password);
            setUser(user);
        } catch (error) {
            console.error("AuthContext Login Error:", error);
            throw error;
        }
    };

    const logout = async () => {
        try {
            await userService.logout();
            setUser(null);
        } catch (error) {
            console.error("AuthContext Logout Error:", error);
            throw error;
        }
    };

    const register = async (registerData) => {
        try {
            await userService.register(registerData);
        } catch (error) {
            console.error("AuthContext Register Error:", error);
            throw error;
        }
    }

    return (
        <AuthContext.Provider value={{ user, login, logout, register }}>
            {children}
        </AuthContext.Provider>
    );
};

AuthProvider.propTypes = {
    children: PropTypes.node.isRequired,
};

export default AuthContext;