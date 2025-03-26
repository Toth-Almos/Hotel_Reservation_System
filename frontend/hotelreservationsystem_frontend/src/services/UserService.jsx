import apiClient from "./axiosConfig";

export const login = async (username, password) => {
    try {
        const response = await apiClient.post('api/v1/auth/login', { username, password });
        return response.data;
    } catch (error) {
        console.error("Login error: ", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Login failed. Please try again.");
    }
};

export const getCurrentUser = async () => {
    try {
        const response = await apiClient.get('api/v1/aut/current-user');
        return response.data;
    } catch (error) {
        console.error("User fetch error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "No user logged in.");
    }
}

export const register = async (registerData) => {
    try {
        const response = await apiClient.post('api/v1/auth/register', registerData);
        return response.data;
    } catch (error) {
        console.error("Registration error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Registration failed. Please try again.");
    }
}

export const logout = async () => {
    try {
        const response = await apiClient.post('api/v1/auth/logout');
        return response.data;
    } catch (error) {
        console.error("Logout error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Logout failed. Please try again.");
    }
}

export const getProfileDetails = async (userId) => {
    try {
        const response = await apiClient.get('api/v1/user/' + userId);
        return response.data;
    } catch (error) {
        console.error("Profile detail error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Loading profile details failed. Please try again.");
    }
}
