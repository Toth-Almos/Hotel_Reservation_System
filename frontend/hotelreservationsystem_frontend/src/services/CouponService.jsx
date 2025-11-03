import apiClient from "./axiosConfig";

export const validateCoupon = async (code, customerId) => {
    const response = await apiClient.get(`api/v1/coupons/validate`, {
        params: { code, customerId }
    });
    return response.data;
};

export const createCoupon = async (couponData) => {
    try {
        const response = await apiClient.post(`api/v1/coupons/create-coupon`, couponData);
        return response.data;
    } catch (error) {
        console.error("Coupon creation error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Create failed");
    }
}

export const updateCoupon = async (couponData) => {
    try {
        const response = await apiClient.patch(`api/v1/coupons/update-coupon`, couponData);
        return response.data;
    } catch (error) {
        console.error("Coupon update error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Update failed");
    }
}

export const deleteCoupon = async (couponData) => {
    try {
        const response = await apiClient.delete(`api/v1/coupons/delete-coupon`, couponData);
        return response.data;
    } catch (error) {
        console.error("Coupon deletion error:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Delete failed");
    }
}

export const getFilteredCoupons = async (filters = {}, page = 0, size = 10) => {
    try {
        // Normalize code
        const code = filters.code?.trim() || undefined;

        // Normalize isActive:
        // - if already boolean, keep it
        // - if string "true"/"false", convert to boolean
        // - if empty string / null / undefined -> undefined (no filter)
        let isActive;
        if (typeof filters.isActive === "boolean") {
            isActive = filters.isActive;
        } else if (typeof filters.isActive === "string") {
            const s = filters.isActive.trim().toLowerCase();
            if (s === "true") isActive = true;
            else if (s === "false") isActive = false;
            else isActive = undefined;
        } else {
            isActive = undefined;
        }

        const params = {
            ...(code ? { code } : {}),
            ...(typeof isActive === "boolean" ? { isActive } : {}),
            page,
            size
        };

        const response = await apiClient.get('api/v1/coupons/filtered', {
            params,
            withCredentials: true,
        });

        return response.data;
    } catch (error) {
        console.error("Fetching filtered coupons failed:", error.response?.data || error.message);
        throw new Error(error.response?.data?.message || "Fetching filtered coupons failed.");
    }
};