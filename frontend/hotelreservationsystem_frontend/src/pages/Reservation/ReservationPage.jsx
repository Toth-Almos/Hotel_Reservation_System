import { useState } from "react";
import { useLocation, useNavigate } from "react-router";
import classes from "./reservationPage.module.css";
import { createReservation } from "../../services/ReservationService";
import { useAuth } from "../../hooks/AuthContext";
import { validateCoupon } from "../../services/CouponService";

export default function ReservationPage() {
    const { user } = useAuth();
    const location = useLocation();
    const navigate = useNavigate();

    // Get hotel data from navigation state
    const hotel = location.state?.hotel;

    // Prevent hooks from running conditionally
    const initialRoomState = hotel
        ? hotel.rooms.reduce((acc, room) => {
            acc[room.id] = 0;
            return acc;
        }, {})
        : {};

    const [selectedRooms, setSelectedRooms] = useState(initialRoomState);
    const [checkInDate, setCheckInDate] = useState("");
    const [checkOutDate, setCheckOutDate] = useState("");
    const [totalCost, setTotalCost] = useState(0);
    const [paymentMethod, setPaymentMethod] = useState("ONSITE");
    const [couponCode, setCouponCode] = useState("");
    const [isCouponValid, setIsCouponValid] = useState(false);
    const [discountValue, setDiscountValue] = useState({ type: null, value: 0 });
    const [validationMessage, setValidationMessage] = useState("");
    const [couponChecked, setCouponChecked] = useState(false);

    if (!hotel) {
        return <p className={classes.error}>Error: No hotel selected.</p>;
    }

    const handleRoomChange = (roomId, value) => {
        const updatedRooms = { ...selectedRooms, [roomId]: value };
        setSelectedRooms(updatedRooms);
        calculateTotalCost(updatedRooms, checkInDate, checkOutDate);
    };

    const calculateTotalCost = (rooms, checkIn, checkOut) => {
        if (!checkIn || !checkOut) return;

        const nights = Math.max(0, (new Date(checkOut) - new Date(checkIn)) / (1000 * 60 * 60 * 24));
        let cost = 0;

        for (const room of hotel.rooms) {
            cost += rooms[room.id] * room.pricePerNight * nights;
        }

        let discountedCost = cost;
        if (isCouponValid && discountValue.type) {
            if (discountValue.type === "FIXED") {
                discountedCost = Math.max(0, cost - discountValue.value);
            }
            else if (discountValue.type === "PERCENTAGE") {
                discountedCost = cost * (1 - discountValue.value / 100);
            }
        }

        setTotalCost(discountedCost);
    };

    const handleDateChange = (checkIn, checkOut) => {
        setCheckInDate(checkIn);
        setCheckOutDate(checkOut);
        calculateTotalCost(selectedRooms, checkIn, checkOut);
    };

    const handleReserve = async (e) => {
        e.preventDefault();

        if (!user) {
            alert("You must be logged in to make a reservation.");
            return;
        }

        const reservationData = {
            hotelId: hotel.id,
            customerId: user.id,
            checkInDate,
            checkOutDate,
            paymentMethod: paymentMethod,
            couponCode: isCouponValid ? couponCode : null,
            reservationItemRequests: Object.entries(selectedRooms)
                .map(([roomId, count]) => count > 0 ? { roomId: parseInt(roomId), numberOfRooms: count } : null)
                .filter(item => item !== null),
        };

        try {
            await createReservation(reservationData);
            alert("Reservation successful!");
            navigate("/profile");
        } catch (error) {
            console.error("Reservation failed:", error);
            alert("Reservation failed. Please try again.");
        }
    };

    const handleValidateCoupon = async () => {
        if (!couponCode.trim()) {
            setValidationMessage("Please enter a coupon code.");
            return;
        }

        if (!user) {
            alert("You must be logged in to make a reservation.");
            return;
        }

        try {
            const result = await validateCoupon(couponCode, user.id);
            setCouponChecked(true);

            if (result.valid) {
                setIsCouponValid(true);
                setDiscountValue({ type: result.type, value: result.discountValue });
                setValidationMessage(result.message);
                setCouponCode(result.code);

                calculateTotalCost(selectedRooms, checkInDate, checkOutDate);
            }
            else {
                setIsCouponValid(false);
                setDiscountValue({ type: null, value: 0 });
                setValidationMessage(result.message);
            }
        } catch (error) {
            console.error(error);
            setIsCouponValid(false);
            setDiscountValue({ type: null, value: 0 });
            setValidationMessage("Error during coupon validation process, please try again.");
        }
    }

    const handleCouponChange = (e) => {
        setCouponCode(e.target.value);
        setIsCouponValid(false);
        setCouponChecked(false);
        setValidationMessage("");
    };

    return (
        <div className={classes.container}>
            <h1 className={classes.title}>Reserve Rooms at {hotel.name}</h1>

            <form onSubmit={handleReserve} className={classes.form}>
                <label>
                    Check-in Date:
                    <input type="date" value={checkInDate} onChange={(e) => handleDateChange(e.target.value, checkOutDate)} required />
                </label>

                <label>
                    Check-out Date:
                    <input type="date" value={checkOutDate} onChange={(e) => handleDateChange(checkInDate, e.target.value)} required />
                </label>

                <h1>Select Rooms</h1>
                {hotel.rooms.map((room) => (
                    <div key={room.id} className={classes.roomSelection}>
                        <span>{room.type} - {room.pricePerNight}€ / night</span>
                        <input
                            type="number"
                            min="0"
                            max="5"
                            value={selectedRooms[room.id]}
                            onChange={(e) => handleRoomChange(room.id, parseInt(e.target.value))}
                        />
                    </div>
                ))}

                <h3 className={classes.totalCost}>Total Cost: {totalCost.toFixed(2)}€</h3>

                <label>
                    Payment Method:
                    <select value={paymentMethod} className={classes.paymentSelect} onChange={(e) => setPaymentMethod(e.target.value)} required>
                        <option value="ONLINE">Online</option>
                        <option value="ONSITE">On site</option>
                    </select>
                </label>

                <label>
                    Coupon Code:
                    <div className={classes.couponWrapper}>
                        <input
                            type="text"
                            value={couponCode}
                            onChange={handleCouponChange}
                            className={classes.couponInput}
                        />
                        <button type="button" onClick={handleValidateCoupon} className={classes.validateButton}>
                            Apply
                        </button>
                    </div>
                </label>

                {validationMessage && (
                    <p className={isCouponValid ? classes.success : classes.error}>
                        {validationMessage}
                    </p>
                )}

                {isCouponValid && (
                    <p className={classes.discountText}>
                        Your Discount using this coupon: -{discountValue.type === "FIXED" ? discountValue.value : (discountValue.value / 100) * totalCost} €
                    </p>
                )}

                <button type="submit" className={classes.reserveButton} disabled={(couponCode.trim() && !couponChecked) || (couponChecked && !isCouponValid && couponCode.trim())}>Confirm Reservation</button>
            </form>
        </div>
    );
}