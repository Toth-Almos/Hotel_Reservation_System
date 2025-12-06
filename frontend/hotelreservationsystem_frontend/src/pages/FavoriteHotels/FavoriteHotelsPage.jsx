import { useEffect, useState } from "react";
import { useAuth } from "../../hooks/AuthContext";
import { getFavoriteHotels, deleteFavoriteHotel } from "../../services/FavoriteHotelService";
import { useNavigate } from "react-router";
import classes from "./favoriteHotelsPage.module.css";

export default function FavoriteHotelsPage() {
    const { user } = useAuth();
    const [favorites, setFavorites] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const pageSize = 10;
    const navigate = useNavigate();

    useEffect(() => {
        if (user) {
            loadFavorites(0);
        }
    }, [user]);

    const loadFavorites = async (pageNumber) => {
        try {
            const data = await getFavoriteHotels(user.id, pageNumber, pageSize);
            setFavorites(data.content);
            setTotalPages(data.totalPages);
            setPage(data.number);
        } catch (error) {
            console.error("Error loading favorite hotels:", error);
        }
    };

    const handleRemove = async (hotelId) => {
        if (!window.confirm("Are you sure you want to remove this hotel from favorites?")) return;

        try {
            await deleteFavoriteHotel(user.id, hotelId);
            loadFavorites(page); // refresh the current page
        } catch (error) {
            console.error("Error removing favorite:", error);
        }
    };

    return (
        <div className={classes.container}>
            <h2 className={classes.title}>My Favorite Hotels</h2>

            {favorites.length === 0 ? (
                <p className={classes.emptyText}>You have no favorite hotels.</p>
            ) : (
                <div className={classes.list}>
                    {favorites.map(fav => (
                        <div key={fav.id} className={classes.card}>
                            <div className={classes.cardInfo}>
                                <h3
                                    className={classes.hotelLink}
                                    onClick={() => navigate(`/hotel/${fav.id}`)}
                                    style={{ cursor: "pointer", textDecoration: "underline" }}
                                >
                                    {fav.name}
                                </h3>
                                <p>{fav.location}</p>
                            </div>

                            <button
                                className={classes.deleteButton}
                                onClick={() => handleRemove(fav.id)}
                            >
                                Remove
                            </button>
                        </div>
                    ))}
                </div>
            )}

            {/* Pagination */}
            {totalPages > 1 && (
                <div className={classes.pagination}>
                    <button
                        disabled={page === 0}
                        onClick={() => loadFavorites(page - 1)}
                    >
                        Prev
                    </button>

                    <span>{page + 1} / {totalPages}</span>

                    <button
                        disabled={page === totalPages - 1}
                        onClick={() => loadFavorites(page + 1)}
                    >
                        Next
                    </button>
                </div>
            )}
        </div>
    );
}
