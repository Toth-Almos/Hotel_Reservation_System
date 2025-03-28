import { Route, Routes } from "react-router"
import HomePage from './pages/Home/HomePage'
import BrowserPage from './pages/Browser/BrowserPage'
import HotelPage from "./pages/Hotel/HotelPage"
import LoginPage from "./pages/Login/LoginPage"
import RegisterPage from "./pages/Register/RegisterPage"
import ProfilePage from "./pages/Profile/ProfilePage"

export default function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/browser" element={<BrowserPage />} />
            <Route path="/hotel/:id" element={<HotelPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/profile" element={<ProfilePage />} />
        </Routes>
    )
}