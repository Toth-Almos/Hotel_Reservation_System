import { Route, Routes } from "react-router"
import HomePage from './pages/Home/HomePage'
import BrowserPage from './pages/Browser/BrowserPage'
import HotelPage from "./pages/Hotel/HotelPage"

export default function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/browser" element={<BrowserPage />} />
            <Route path="/hotel/:id" element={<HotelPage />} />
        </Routes>
    )
}