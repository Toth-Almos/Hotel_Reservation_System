import { Route, Routes } from "react-router"
import HomePage from './pages/Home/HomePage'
import BrowserPage from './pages/Browser/BrowserPage'
import HotelPage from "./pages/Hotel/HotelPage"
import LoginPage from "./pages/Login/LoginPage"
import RegisterPage from "./pages/Register/RegisterPage"
import ProfilePage from "./pages/Profile/ProfilePage"
import ReservationPage from "./pages/Reservation/ReservationPage"
import ReservationHistoryPage from "./pages/ReservationHistory/ReservationHistoryPage"
import ReservationCancelPage from "./pages/ReservationCancel/ReservationCancelPage"

export default function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/browser" element={<BrowserPage />} />
            <Route path="/hotel/:id" element={<HotelPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/profile" element={<ProfilePage />} />
            <Route path="/reserve/:id" element={<ReservationPage />} />
            <Route path="/reservation-history" element={<ReservationHistoryPage />} />
            <Route path="/reservation-cancel" element={<ReservationCancelPage />} />
        </Routes>
    )
}