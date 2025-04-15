import apiClient from "./axiosConfig";

export const getAll = async () => {
    const response = await apiClient.get('api/v1/hotels');
    return response.data;
};

export const getById = async (hotelId) => {
    const response = await apiClient.get('api/v1/hotels/' + hotelId);
    return response.data;
};

export const updateHotel = async (hotelId, updateData) => {
    try {
        const response = await apiClient.patch(`/api/v1/hotels/update-hotel/${hotelId}`, updateData);
        return response.data;
    } catch (error) {
        console.error("Hotel update error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Update failed");
    }
};

export const createHotel = async (hotelData) => {
    try {
        const response = await apiClient.post(`/api/v1/hotels/create-hotel`, hotelData);
        return response.data;
    } catch (error) {
        console.error("Hotel creation error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Create failed");
    }
};