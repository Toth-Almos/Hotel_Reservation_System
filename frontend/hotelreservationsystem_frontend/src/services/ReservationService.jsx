import apiClient from "./axiosConfig";

export const createReservation = async (resvationData) => {
    try {
        const respone = await apiClient.post('api/v1/reservation/create-reservation', resvationData);
        return respone.data;
    } catch (error) {
        console.error("Reservation creation error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Reservation failed.");
    }
};