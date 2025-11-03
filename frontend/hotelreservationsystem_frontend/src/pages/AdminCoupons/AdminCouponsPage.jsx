import { useState } from "react";
import { createCoupon, updateCoupon, deleteCoupon, getFilteredCoupons } from "../../services/CouponService";
import classes from "./adminCouponsPage.module.css";

export default function AdminCouponPage() {
    const [coupons, setCoupons] = useState([]);
    const [editingCoupon, setEditingCoupon] = useState(null);
    const [confirmDelete, setConfirmDelete] = useState(false);

    const [filters, setFilters] = useState({
        code: '',
        active: null
    });

    const [newCouponData, setNewCouponData] = useState({
        code: "",
        discountValue: 0,
        type: "FIXED",
        validFrom: "",
        validUntil: "",
        active: true
    });

    const [formData, setFormData] = useState({
        code: "",
        discountValue: 0,
        type: "FIXED",
        validFrom: "",
        validUntil: "",
        active: true
    });

    const handleFilterChange = (e) => {
        let { name, value } = e.target;
        if (name === "active") {
            value = value === "" ? null : value === "true";
        }
        setFilters(prev => ({ ...prev, [name]: value }));
    };

    const handleSearch = async () => {
        try {
            const data = await getFilteredCoupons(filters);
            setCoupons(data.content || []);
        } catch (error) {
            console.error("Error searching coupons:", error);
            setCoupons([]);
        }
    };

    const handleEditClick = (coupon) => {
        setEditingCoupon(coupon);
        setFormData(coupon);
    };

    const handleFormChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: name === "discountValue" ? parseFloat(value) : value
        }));
    };

    const handleNewCouponChange = (e) => {
        const { name, value } = e.target;
        setNewCouponData(prev => ({
            ...prev,
            [name]: name === "discountValue" ? parseFloat(value) : value
        }));
    };

    const handleSave = async () => {
        try {
            const payload = {
                id: editingCoupon.id, // include in request body but not as separate arg
                ...formData
            };

            const updated = await updateCoupon(payload);

            setCoupons(prev =>
                prev.map(c => c.id === updated.id ? updated : c)
            );

            setEditingCoupon(null);
            setConfirmDelete(false);

        } catch (error) {
            console.error("Error updating coupon:", error);
        }
    };

    const handleDelete = async () => {
        try {
            await deleteCoupon(editingCoupon.id);
            setCoupons(prev => prev.filter(c => c.id !== editingCoupon.id));
            setEditingCoupon(null);
            setConfirmDelete(false);
        } catch (error) {
            console.error("Error deleting coupon:", error);
        }
    };

    const handleCreate = async () => {
        try {
            const created = await createCoupon(newCouponData);
            setCoupons(prev => [...prev, created]);
            setNewCouponData({
                code: "",
                discountValue: 0,
                type: "FIXED",
                validFrom: "",
                validUntil: "",
                active: true
            });
        } catch (error) {
            console.error("Error creating coupon:", error);
        }
    };

    return (
        <div className={classes.container}>
            <h2>Coupon Management</h2>

            {/* Create Coupon Form */}
            <div className={classes.newCouponForm}>
                <h3>Create New Coupon</h3>

                <label>Code:</label>
                <input name="code" value={newCouponData.code} onChange={handleNewCouponChange} />

                <label>Discount Value:</label>
                <input name="discountValue" type="number" value={newCouponData.discountValue} onChange={handleNewCouponChange} />

                <label>Type:</label>
                <select name="type" value={newCouponData.type} onChange={handleNewCouponChange}>
                    <option value="FIXED">Fixed (€)</option>
                    <option value="PERCENTAGE">Percent (%)</option>
                </select>

                <label>Valid From:</label>
                <input name="validFrom" type="date" value={newCouponData.validFrom} onChange={handleNewCouponChange} />

                <label>Valid Until:</label>
                <input name="validUntil" type="date" value={newCouponData.validUntil} onChange={handleNewCouponChange} />

                <button onClick={handleCreate} className={classes.createButton}>Create Coupon</button>
            </div>

            {/* Filter Section */}
            <div className={classes.searchSection}>
                <input name="code" placeholder="Code" value={filters.code} onChange={handleFilterChange} />
                <select name="active" value={filters.active ?? ""} onChange={handleFilterChange}>
                    <option value="">All</option>
                    <option value="true">Active</option>
                    <option value="false">Inactive</option>
                </select>
                <button onClick={handleSearch}>Search</button>
            </div>

            {/* List */}
            <ul className={classes.list}>
                {coupons.map(coupon => (
                    <li key={coupon.id}>
                        <div className={classes.content}>
                            <div>
                                <p><strong>{coupon.code}</strong></p>
                                <p>{coupon.type === "PERCENTAGE" ? `${coupon.discountValue}%` : `${coupon.discountValue}€`}</p>
                                <p>Valid From: {coupon.validFrom}</p>
                                <p>Valid Until: {coupon.validUntil}</p>
                                <p>Status: {coupon.active ? "✅ Active" : "❌ Inactive"}</p>
                            </div>
                            <button onClick={() => handleEditClick(coupon)} className={classes.editButton}>Edit</button>
                        </div>
                    </li>
                ))}
            </ul>

            {/* Edit Modal */}
            {editingCoupon && (
                <div className={classes.modalOverlay}>
                    <div className={classes.editForm}>
                        <h3>Edit Coupon - {editingCoupon.code}</h3>

                        <label>Code:</label>
                        <input name="code" value={formData.code} onChange={handleFormChange} />

                        <label>Discount Value:</label>
                        <input name="discountValue" type="number" value={formData.discountValue} onChange={handleFormChange} />

                        <label>Type:</label>
                        <select name="type" value={formData.type} onChange={handleFormChange}>
                            <option value="FIXED">Fixed (€)</option>
                            <option value="PERCENTAGE">Percent (%)</option>
                        </select>

                        <label>Valid From:</label>
                        <input name="validFrom" type="date" value={formData.validFrom} onChange={handleFormChange} />

                        <label>Valid Until:</label>
                        <input name="validUntil" type="date" value={formData.validUntil} onChange={handleFormChange} />

                        <label>Status:</label>
                        <select name="active" value={formData.active} onChange={handleFormChange}>
                            <option value={true}>Active</option>
                            <option value={false}>Inactive</option>
                        </select>

                        <div className={classes.formActions}>
                            <button onClick={handleSave} className={classes.saveButton}>Save</button>
                            <button onClick={() => setEditingCoupon(null)} className={classes.cancelButton}>Cancel</button>
                            <button onClick={() => setConfirmDelete(true)} className={classes.deleteButton}>Delete</button>
                        </div>

                        {confirmDelete && (
                            <div className={classes.confirmDelete}>
                                <p>Are you sure you want to delete this coupon?</p>
                                <button onClick={handleDelete}>Yes</button>
                                <button onClick={() => setConfirmDelete(false)}>No</button>
                            </div>
                        )}
                    </div>
                </div>
            )}

        </div>
    );
}
