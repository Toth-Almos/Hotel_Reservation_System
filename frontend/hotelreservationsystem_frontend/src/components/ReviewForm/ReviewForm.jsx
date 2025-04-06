import { useState } from "react";
import { useAuth } from "../../hooks/AuthContext";
import { createReview } from "../../services/ReviewService";
import PropTypes from 'prop-types';
import classes from "./reviewForm.module.css";

export default function ReviewForm({ hotelId }) {
    const { user } = useAuth();
    const [rating, setRating] = useState(5);
    const [comment, setComment] = useState("");
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!user) {
            setError("You must be logged in to leave a review.");
            return;
        }
        if (rating < 1 || rating > 5) {
            setError("Rating must be between 1 and 5");
            return;
        }
        if (comment.trim() === "") {
            setError("Comment can not be empty.");
            return;
        }

        try {
            await createReview({
                customerId: user.id,
                hotelId: hotelId,
                rating,
                comment,
            });
            setSuccess("Review submitted!");
            setComment("");
            setRating(5);
            setError(null);
        } catch (error) {
            setError(error.messsage);
            setSuccess(null);
        }
    };

    return (
        <form onSubmit={handleSubmit} className={classes.reviewForm}>
            <h3>Leave a Review</h3>

            {error && <p className={classes.error}>{error}</p>}
            {success && <p className={classes.success}>{success}</p>}

            <label>
                Rating:
                <select value={rating} onChange={(e) => setRating(Number(e.target.value))}>
                    {[1, 2, 3, 4, 5].map((r) => (
                        <option key={r} value={r}>{r}</option>
                    ))}
                </select>
            </label>

            <label>
                Comment:
                <textarea
                    value={comment}
                    onChange={(e) => setComment(e.target.value)}
                    rows="4"
                    placeholder="Share your experience..."
                />
            </label>

            <button type="submit" className={classes.submitButton}>Submit Review</button>
        </form>
    );
}

ReviewForm.propTypes = {
    hotelId: PropTypes.oneOfType([
        PropTypes.string,
        PropTypes.number
    ]).isRequired,
};