import apiClient from "./axiosConfig";

export const validateCoupon = async (code, customerId) => {
    const response = await apiClient.get(`api/v1/coupons/validate`, {
        params: { code, customerId }
    });
    return response.data;
};