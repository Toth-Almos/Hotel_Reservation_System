import apiClient from "./axiosConfig";

export const createReview = async (reviewData) => {
    try {
        const respone = await apiClient.post('api/v1/review/create-review', reviewData);
        return respone.data;
    } catch (error) {
        console.error("Review creation error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Review creation failed.");
    }
}

export const getReviewsForCustomer = async (customerId) => {
    try {
        const respone = await apiClient.get('api/v1/review/get-customer-reviews/' + customerId)
        return respone.data;
    } catch (error) {
        console.error("Review fetching error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Review fetching failed.");
    }
}

export const getReviewsForHotel = async (hotelId) => {
    try {
        const respone = await apiClient.get('api/v1/review/get-hotel-reviews/' + hotelId)
        return respone.data;
    } catch (error) {
        console.error("Review fetching error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Review fetching failed.");
    }
}