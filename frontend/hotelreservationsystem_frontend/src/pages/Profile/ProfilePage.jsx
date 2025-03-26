import { useState, useEffect } from "react";
import { useAuth } from "../../hooks/AuthContext";
import classes from "./profilePage.module.css";
import { getProfileDetails } from "../../services/UserService";

export default function ProfilePage() {
    const { user } = useAuth();
    const [profileDetails, setProfileDetails] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (user) {
            getProfileDetails(user.id)
                .then(data => setProfileDetails(data))
                .catch(error => console.error("Error fetching profile:", error))
                .finally(() => setLoading(false));
        }
    }, [user]);

    if (loading) {
        return <div className={classes.loading}>Loading profile...</div>;
    }

    if (!profileDetails) {
        return <div className={classes.error}>Failed to load profile details.</div>;
    }

    return (
        <div className={classes.profileContainer}>
            <h2 className={classes.profileTitle}>Your Profile</h2>

            <div className={classes.profileDetails}>
                <p><strong>Username:</strong> {profileDetails.username}</p>
                <p><strong>Email:</strong> {profileDetails.email}</p>
                <p><strong>Phone:</strong> {profileDetails.phoneNumber}</p>
                <p><strong>Address:</strong> {profileDetails.address}</p>
            </div>

            <button className={classes.editButton}>Edit Profile</button>
        </div>
    )
}