import apiClient from "./axiosConfig";

export const getFavoriteHotels = async (customerId, page = 0, size = 10) => {
    const response = await apiClient.get(`/api/v1/favorites/${customerId}`, {
        params: { page, size }
    });
    return response.data;
};

export const deleteFavoriteHotel = async (customerId, hotelId) => {
    const response = await apiClient.delete(`/api/v1/favorites/remove`, {
        params: { customerId, hotelId }
    });
    return response.data;
};

export const isHotelFavorite = async (customerId, hotelId) => {
    const response = await apiClient.get(`/api/v1/favorites/check`, {
        params: { customerId, hotelId }
    });
    return response.data;
};

export const addFavoriteHotel = async (customerId, hotelId) => {
    const response = await apiClient.post(`/api/v1/favorites/add?customerId=${customerId}&hotelId=${hotelId}`);
    return response.data;
};