import apiClient from "./axiosConfig";

export const getAll = async () => {
    const response = await apiClient.get('api/v1/hotels');
    return response.data;
}

export const getById = async (hotelId) => {
    const response = await apiClient.get('api/v1/hotels/' + hotelId);
    return response.data;
}