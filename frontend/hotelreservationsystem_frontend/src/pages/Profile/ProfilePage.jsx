import { useState, useEffect } from "react";
import { useAuth } from "../../hooks/AuthContext";
import classes from "./profilePage.module.css";
import { getProfileDetails, updateProfileDetails } from "../../services/UserService";
import { useNavigate } from "react-router";

export default function ProfilePage() {
    const { user } = useAuth();
    const navigate = useNavigate();
    const [profileDetails, setProfileDetails] = useState(null);
    const [loading, setLoading] = useState(true);
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState({
        username: "",
        email: "",
        phoneNumber: "",
        address: "",
    });

    useEffect(() => {
        if (user) {
            getProfileDetails(user.id)
                .then(data => {
                    setProfileDetails(data);
                    setFormData(data);
                })
                .catch(error => console.error("Error fetching profile:", error))
                .finally(() => setLoading(false));
        }
    }, [user]);

    const handleInputChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    }

    const handleSave = async () => {
        try {
            const updatedData = await updateProfileDetails(user.id, formData);
            setProfileDetails(updatedData);
            setIsEditing(false);
        } catch (error) {
            console.error("Error updating profile:", error);
        }
    }

    if (loading) {
        return <div className={classes.loading}>Loading profile...</div>;
    }

    if (!profileDetails) {
        return <div className={classes.error}>Failed to load profile details.</div>;
    }

    return (
        <div className={classes.profileContainer}>
            <h2 className={classes.profileTitle}>Your Profile</h2>

            {!isEditing ? (
                <>
                    <div className={classes.profileDetails}>
                        <p><strong>Username:</strong> {profileDetails.username}</p>
                        <p><strong>Email:</strong> {profileDetails.email}</p>
                        <p><strong>Phone:</strong> {profileDetails.phoneNumber}</p>
                        <p><strong>Address:</strong> {profileDetails.address}</p>
                    </div>
                    <div className={classes.buttonContainer}>
                        <button className={classes.profileButton} onClick={() => navigate(`/reservation-history`)}>
                            My Reservation History
                        </button>
                        <button className={classes.profileButton} onClick={() => navigate(`/reservation-cancel`)}>
                            Cancel Active Reservations
                        </button>
                        <button className={classes.profileButton} onClick={() => setIsEditing(true)}>
                            Edit Profile
                        </button>
                    </div>
                </>
            ) : (
                <div className={classes.editForm}>
                    <label>Username:</label>
                    <input
                        type="text"
                        name="username"
                        value={formData.username}
                        onChange={handleInputChange}
                    />

                    <label>Email:</label>
                    <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleInputChange}
                    />

                    <label>Phone:</label>
                    <input
                        type="text"
                        name="phoneNumber"
                        value={formData.phoneNumber}
                        onChange={handleInputChange}
                    />

                    <label>Address:</label>
                    <input
                        type="text"
                        name="address"
                        value={formData.address}
                        onChange={handleInputChange}
                    />

                    <button className={classes.saveButton} onClick={handleSave}>Save</button>
                    <button className={classes.cancelButton} onClick={() => setIsEditing(false)}>Cancel</button>
                </div>
            )}
        </div>
    )
}