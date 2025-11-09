import { useState, useEffect } from "react";
import { useAuth } from "../../hooks/AuthContext";
import { getReviewsForCustomer, deleteReview } from "../../services/ReviewService";
import classes from "./reviewListPage.module.css";

export default function ReviewListPage() {
    const { user } = useAuth();
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        if (!user) return;

        const fetchReviews = async () => {
            try {
                const data = await getReviewsForCustomer(user.id);
                setReviews(data);
            } catch (err) {
                console.error(err);
                setError("Failed to fetch reviews.");
            } finally {
                setLoading(false);
            }
        };

        fetchReviews();
    }, [user]);

    const handleDelete = async (reviewId) => {
        if (!window.confirm("Are you sure you want to delete this review?")) return;

        try {
            await deleteReview(reviewId);
            setReviews(prev => prev.filter(r => r.id !== reviewId));
        } catch (err) {
            console.error(err);
            alert("Failed to delete the review. Try again.");
        }
    };

    if (!user) {
        return <p className={classes.error}>You must be logged in to see your reviews.</p>;
    }

    if (loading) {
        return <p>Loading your reviews...</p>;
    }

    if (error) {
        return <p className={classes.error}>{error}</p>;
    }

    if (reviews.length === 0) {
        return <p>You haven’t left any reviews yet.</p>;
    }

    return (
        <div className={classes.container}>
            <h1>Your Reviews</h1>
            <ul className={classes.reviewList}>
                {reviews.map(review => (
                    <li key={review.id} className={classes.reviewItem}>
                        <div>
                            <h3>{review.hotelName}</h3>
                            <p><strong>Rating:</strong> {review.rating} / 5</p>
                            <p>{review.comment}</p>
                        </div>
                        <button
                            className={classes.deleteButton}
                            onClick={() => handleDelete(review.id)}
                        >
                            Delete
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}
