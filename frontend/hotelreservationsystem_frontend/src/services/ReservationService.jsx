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

export const getReservationsForCustomer = async (customerId) => {
    try {
        const respone = await apiClient.get('api/v1/reservation/get-reservations/' + customerId)
        return respone.data;
    } catch (error) {
        console.error("Reservation fetching error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Reservation fetching failed.");
    }
}

export const getActiveReservationsForCustomer = async (customerId) => {
    try {
        const respone = await apiClient.get('api/v1/reservation/get-active-reservations/' + customerId)
        return respone.data;
    } catch (error) {
        console.error("Reservation fetching error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Reservation fetching failed.");
    }
}

export const deleteReservation = async (reservationId) => {
    try {
        const respone = await apiClient.delete('api/v1/reservation/delete-reservation/' + reservationId);
        return respone.data;
    } catch (error) {
        console.error("Reservation delete error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Reservation delete failed.");
    }
}