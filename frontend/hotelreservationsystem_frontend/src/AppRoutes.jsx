import { Route, Routes } from "react-router"
import HomePage from './pages/Home/HomePage'
import BrowserPage from './pages/Browser/BrowserPage'

export default function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<HomePage/>} />
            <Route path="/browser" element={<BrowserPage/>} />
        </Routes>
    )
}