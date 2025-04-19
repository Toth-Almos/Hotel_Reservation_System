import { useAuth } from "../hooks/AuthContext";
import { Navigate } from "react-router";
import PropTypes from "prop-types";
import { useEffect, useState } from "react";

export default function AdminRoute({ children }) {
    const { user } = useAuth();
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const waitForUser = async () => {
            if (user !== null) {
                setLoading(false);
            }
        };

        waitForUser();
    }, [user]);

    if (loading) {
        return <div>Loading admin route...</div>;
    }

    if (!user) {
        return <Navigate to="/login" />;
    }

    if (!user.role.includes("ROLE_ADMIN")) {
        return <Navigate to="/unauthorized" />;
    }


    return children;
}

AdminRoute.propTypes = {
    children: PropTypes.node.isRequired,
};