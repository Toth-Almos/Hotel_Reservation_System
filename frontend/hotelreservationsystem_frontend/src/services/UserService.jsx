import apiClient from "./axiosConfig";

export const login = async (username, password) => {
    try {
        const response = await apiClient.post('api/v1/auth/login', { username, password });
        if (response.status === 200) {
            const user = response.data;
            //sessionStorage.setItem("username", user.username);
            //sessionStorage.setItem("id", user.id);
            //sessionStorage.setItem("role", user.role);
            return user;
        }
    } catch (error) {
        throw error.response?.data || "Login failed";
    }
};

export const getCurrentUser = async () => {
    try {
        const response = await apiClient.get('api/v1/aut/current-user');
        if (response.status === 200) {
            const user = response.data;
            return user;
        }
    } catch (error) {
        throw error.response?.data || "No user logged in";
    }
}

export const register = async (registerData) => {
    try {
        const response = await apiClient.post('api/v1/auth/register', {
            username: registerData.username,
            email: registerData.email,
            password: registerData.password,
            phoneNumber: registerData.phoneNumber,
            address: registerData.address
        });
        return response.data;
    } catch (error) {
        throw error.response?.data || "Registration failed";
    }
}

export const logout = async () => {
    const response = await apiClient.post('api/v1/auth/logout');
    return response;
}
